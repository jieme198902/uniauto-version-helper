####车微联版本更新：
使用方法：
```java
VersionHelper versionHelper = new VersionHelper();
        versionHelper.createParam(this)
                .setAppKey("XXXXXXXXXXXXXXXXXXX")
                .setUrl("https://XXXXXXXX/checkVersion")
                .setSetTopPic_450_204_DrawableId(R.mipmap.ic_launcher)
                .setCheckVersionResultListener(new VersionHelper.CheckVersionResultListener() {
                    @Override
                    public CUpdateAppBean createUpdateBean(String result) {
                        CUpdateAppBean bean = new CUpdateAppBean();
                        //自定义从String转成返回的对象
                        
                        return bean;
                    }
                })
                .start(this);
```