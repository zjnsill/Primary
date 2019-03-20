package com.psi.shen.primary;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class bottomSheet extends AppCompatActivity {
    private CardView searchCV, customizedCV,starredCV,aboutCV;
    private signedUser currentUser;
    private String phone;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        setStatusBarFullTransparent();
        setFitSystemWindow(true);
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        phone=sharedPreferences.getString("phone","");

        Intent Editing = getIntent();
        String s = "";
        if(Editing.getParcelableExtra("edituser")==null)
            currentUser = new signedUser.Builder("User", phone).Bio(s).Email("Null").build();
        else
            currentUser = Editing.getParcelableExtra("edituser");
        //
        //
        searchCV = findViewById(R.id.SearchCV);
        customizedCV = findViewById(R.id.CustomizedCV);
        starredCV = findViewById(R.id.StarredCV);
        aboutCV = findViewById(R.id.AboutCV);

        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(bottomSheet.this, Search.class);
                searchIntent.putExtra("user", currentUser);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchIntent, ActivityOptions.makeSceneTransitionAnimation(bottomSheet.this).toBundle());
            }
        });
        customizedCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customizedIntent = new Intent(bottomSheet.this, CustomizedAlloys.class);
                customizedIntent.putExtra("user", currentUser);
                customizedIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(customizedIntent, ActivityOptions.makeSceneTransitionAnimation(bottomSheet.this).toBundle());
            }
        });
        starredCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starredIntent = new Intent(bottomSheet.this,StarredList.class);
                starredIntent.putExtra("user", currentUser);
                starredIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(starredIntent, ActivityOptions.makeSceneTransitionAnimation(bottomSheet.this).toBundle());
            }
        });
        aboutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(bottomSheet.this, AboutActivity.class);
                aboutIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(aboutIntent, ActivityOptions.makeSceneTransitionAnimation(bottomSheet.this).toBundle());
            }
        });
        //end of onCreate Methods;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "Press again to exit the Application", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }
}
