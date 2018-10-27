package com.psi.shen.primary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Developers extends AppCompatActivity {
    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        topBar = findViewById(R.id.developersTopbar);
        topBar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                Developers.this.finish();
            }

            @Override
            public void rightListener() {

            }
        });
    }
}
