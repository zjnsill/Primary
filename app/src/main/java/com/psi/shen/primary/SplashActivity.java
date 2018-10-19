package com.psi.shen.primary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

public class SplashActivity extends Activity {
    private int countDownTime = 1000;
    private int countDownInterval = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CountDownTimer countDown = new CountDownTimer(countDownTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //Toast.makeText(SplashActivity.this, "Time's up", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this, bottomSheet.class));
                SplashActivity.this.finish();
            }
        };
        countDown.start();
    }
}
