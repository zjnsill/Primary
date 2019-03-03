package com.psi.shen.primary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.ImageView;


public class SplashActivity extends Activity {
    private int countDownTime = 1000;
    private int countDownInterval = 1;

    private String phone;
    private static final int ANIMATION_TIME = 1000;
    private static final float SCALE_END = 1.2F;

    private ImageView mSplashImage;
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        phone=sharedPreferences.getString("phone","");
        phone="123";
        if(!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }


        setContentView(R.layout.activity_splash);
        mSplashImage = findViewById(R.id.iv_entry);
        mSplashImage.setImageResource(R.drawable.aio_icon);

        CountDownTimer countDown = new CountDownTimer(countDownTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startAnim();
            }
        };
        countDown.start();
    }

    private void startAnim() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(phone.equals("")) {
                    Intent toLogin = new Intent(SplashActivity.this,LoginRegisterEnterinfo.class);
                    Bundle arg = new Bundle();
                    arg.putInt("ambition",102);
                    toLogin.putExtra("ambition",arg);
                    toLogin.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(toLogin);
                } else {
                    Intent tobottom = new Intent(SplashActivity.this,bottomSheet.class);
                    startActivity(tobottom);
                }
                SplashActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // do nothing
        return true;
    }
}
