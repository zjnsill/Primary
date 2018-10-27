package com.psi.shen.primary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppIntro extends AppCompatActivity {
    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

        topBar = findViewById(R.id.appIntroTopbar);
        topBar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                AppIntro.this.finish();
            }

            @Override
            public void rightListener() {

            }
        });
    }
}
