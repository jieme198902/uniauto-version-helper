package me.uniauto.version.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.uniauto.version.entity.CUpdateAppBean;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkVersion();
                    }
                });

    }

    private void startZongzhi() {
        Intent intent = new Intent("com.wisdom.manage.wisdommanage.ui.activity.WelcomeActivity");
        intent.putExtra("userName", "liujie");
        intent.putExtra("password", "111111aa");
        startActivity(intent);

    }

    private void checkVersion() {
        VersionHelper versionHelper = new VersionHelper();
        versionHelper.createParam(this)
                .setAppKey("XXXXXXXXXXXXXXXXXXX")
                .setUrl("https://XXXXXXXX/checkVersion")
                .setSetTopPic_450_204_DrawableId(R.mipmap.ic_launcher)
                //可不加
                .setCheckVersionResultListener(new VersionHelper.CheckVersionResultListener() {
                    @Override
                    public CUpdateAppBean createUpdateBean(String result) {
                        CUpdateAppBean bean = new CUpdateAppBean();
                        //自定义从String转成返回的对象

                        return bean;
                    }
                })
                .start(this);
    }
}
