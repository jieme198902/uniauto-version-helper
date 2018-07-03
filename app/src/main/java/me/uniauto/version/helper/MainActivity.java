package me.uniauto.version.helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
                .setAppKey("5fb9c5849535c13917c2cf9baaece6ef9693ef27")
                .setUrl("https://app.cxql.net/versionManager/admin/checkVersion")
                .setSetTopPic_450_204_DrawableId(R.mipmap.ic_launcher)
                .start(this);
    }
}
