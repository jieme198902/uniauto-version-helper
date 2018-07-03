package me.uniauto.version.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import java.util.HashMap;
import java.util.Map;

import helper.version.uniauto.me.version_helper.R;
import me.uniauto.version.entity.CUpdateAppBean;
import me.uniauto.version.entity.DefaultCheckVersionEntity;
import me.uniauto.version.http.util.OkGoUpdateHttpUtil;

public class VersionHelper {


    private Gson gson = new Gson();

    /**
     * 获取本地软件版本号
     */
    private Integer getVersionCode(Context ctx) {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    ////////////----------------------//////////////
    private Map<String, String> paramMap = new HashMap<>();
    private String appKey = null;
    //检查更新的弹窗
    private Context context = null;
    private String url;
    //检测版本的回调
    private CheckVersionResultListener checkVersionResultListener;
    //是否只是检测版本
    private boolean justCheckNotDownload = false;
    ////////////----------------------//////////////

    public VersionHelper setCheckVersionResultListener(CheckVersionResultListener checkVersionResultListener) {
        this.checkVersionResultListener = checkVersionResultListener;
        return this;
    }

    public VersionHelper setJustCheckNotDownload(boolean justCheckNotDownload) {
        this.justCheckNotDownload = justCheckNotDownload;
        return this;
    }

    /**
     * 添加其他的参数
     *
     * @param appendMap
     * @return
     */
    public VersionHelper appendParam(Map<String, String> appendMap) {
        paramMap.putAll(appendMap);
        return this;
    }

    /**
     * 构建参数
     *
     * @param context
     * @return
     */
    public VersionHelper createParam(Context context) {
        this.context = context;
        //这里即请求版本信息,也更新
        Map<String, String> param = new HashMap<>();
        param.put("oldVersion", getVersionCode(context) + "");
        String channelName = "web";
        try {
            Bundle bundle = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if (null != bundle && !TextUtils.isEmpty(bundle.getString("UMENG_CHANNEL"))) {
                channelName = bundle.getString("UMENG_CHANNEL");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        param.put("channelName", channelName);
        param.put("packname", context.getPackageName());
        paramMap.putAll(param);
        return this;
    }

    /**
     * 设置appKey
     *
     * @param appKey
     * @return
     */
    public VersionHelper setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }


    /**
     * 添加检测版本返回的监听器，自己组装参数
     */
    public interface CheckVersionResultListener {
        CUpdateAppBean createUpdateBean(String result);
    }


    /**
     * @param url
     * @return
     */
    public VersionHelper setUrl(String url) {
        this.url = url;
        return this;
    }


    /**
     * @param activity
     */
    public void start(final Activity activity) {
        //请求地址
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("please setUrl before request !!!");
        }
        //检测是否含有appKey
        if (TextUtils.isEmpty(appKey)) {
            throw new RuntimeException("please setAppKey before request !!!");
        }
        paramMap.put("appKey", appKey);

        new UpdateAppManager
                .Builder()
                //当前 Activity
                .setActivity(activity)
                //设置参数
                .setParams(paramMap)
                //设置请求为POST
                .setPost(true)
                //更新地址
                .setUpdateUrl(url)
//                .setTopPic(R.mipmap.lib_update_app_top_bg)
                //实现 httpManager 接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                .build()
                .checkNewApp(new UpdateCallback() {
                                 @Override
                                 protected UpdateAppBean parseJson(String json) {
                                     /**
                                      * 这里返回null的时候，就没有版本信息，返回的话就是有版本信息。
                                      */
                                     if (justCheckNotDownload && null != checkVersionResultListener) {
                                         checkVersionResultListener.createUpdateBean(json);
                                         return null;
                                     }
                                     UpdateAppBean updateAppBean = new UpdateAppBean();
                                     if (null == checkVersionResultListener) {
                                         DefaultCheckVersionEntity defaultCheckVersionEntity = gson.fromJson(json, DefaultCheckVersionEntity.class);
                                         if (null != defaultCheckVersionEntity) {
                                             DefaultCheckVersionEntity.DataBean dataBean = defaultCheckVersionEntity.getData();
                                             if (null != dataBean) {
                                                 updateAppBean.setUpdate(dataBean.isMust() ? "Yes" : "No");
                                                 updateAppBean.setNewVersion(dataBean.getVersionCode() + "");
                                                 updateAppBean.setApkFileUrl(dataBean.getDownloadUrl());
                                                 updateAppBean.setUpdateLog(dataBean.getUpdateDesc());
                                                 return updateAppBean;
                                             }
                                         }
                                     } else {
                                         //自己处理，从json转成UpdateAppBean
                                         CUpdateAppBean appBean = checkVersionResultListener.createUpdateBean(json);
                                         return gson.fromJson(gson.toJson(appBean), UpdateAppBean.class);
                                     }
                                     return null;
                                 }
                             }
                );
    }
}
