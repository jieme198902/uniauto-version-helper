package me.uniauto.version.entity;

/**
 * 应用更新的实体类,默认的检测返回对象
 * Created by LOVE on 2016/9/1 001.
 */
public class DefaultCheckVersionEntity {


    /**
     * code : C1000
     * message : 有新版本更新
     * data : {"downloadUrl":"http://cxql.oss-cn-qingdao.aliyuncs.com/10app-debug_web.apk","versionCode":10,"updateDesc":"车微联版本更新测试","must":true}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * downloadUrl : http://cxql.oss-cn-qingdao.aliyuncs.com/10app-debug_web.apk
         * versionCode : 10
         * updateDesc : 车微联版本更新测试
         * must : true
         */

        private String downloadUrl;
        private int versionCode;
        private String updateDesc;
        private boolean must;

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getUpdateDesc() {
            return updateDesc;
        }

        public void setUpdateDesc(String updateDesc) {
            this.updateDesc = updateDesc;
        }

        public boolean isMust() {
            return must;
        }

        public void setMust(boolean must) {
            this.must = must;
        }
    }
}
