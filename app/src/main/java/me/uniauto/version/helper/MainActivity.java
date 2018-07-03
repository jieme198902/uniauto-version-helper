package me.uniauto.version.helper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VersionHelper versionHelper = new VersionHelper();
        versionHelper.createParam(this)
                .setAppKey("5fb9c5849535c13917c2cf9baaece6ef9693ef27")
                .setUrl("https://app.cxql.net/versionManager/admin/checkVersion")
                .start(this);
    }
}
