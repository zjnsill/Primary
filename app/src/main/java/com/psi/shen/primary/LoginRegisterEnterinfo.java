package com.psi.shen.primary;

import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class LoginRegisterEnterinfo extends AppCompatActivity implements viewPagerLogin.login,viewPagerVerifyPhone.VerifyPhoneUtil {
    ViewPager viewPager;
    TextView statusTitle;
    ArrayList<String> titleStringSet=new ArrayList<>();
    ArrayList<Integer> contentSet = new ArrayList<>();
    Bundle ambition;

    public static final int LOGIN = 101;
    public static final int REGISTER = 102;
    public static final int FORGET_PASS = 103;
    public static final int SET_BIO = 104;
    public static final int CHANGE_PASS = 105;
    @IntDef({LOGIN,REGISTER,FORGET_PASS,SET_BIO,CHANGE_PASS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AMBITION{}
    //the parameter from the previous activity;
    public static final int NEXT_LOGIN = 112;
    public static final int NEXT_PHONEVERIFY =113;
    public static final int NEXT_SETPASS = 114;
    public static final int NEXT_SETBIO =115;
    public static final int NEXT_RESETPASS = 116;
    public static final int NEXT_CHANGEPHONE = 117;
    @IntDef({NEXT_CHANGEPHONE,NEXT_LOGIN,NEXT_PHONEVERIFY,NEXT_RESETPASS,NEXT_SETBIO,NEXT_SETPASS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NEXT_STEP{}
    //customized next step notation;

    //overide the function from fragment;
    @Override
    public void submitLogin(@NEXT_STEP int currentFragment,String phoneNum,String passcode){
        //TODO logging in with phone and passcode;
        //for test use
        Toast.makeText(this,"viewpager work fine!"+phoneNum+" "+passcode,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void SendVerifyCode(String Phone){
        //TODO send verification codel;
    }
    @Override
    public void verifyCode(String phone,String Code){
        //TODO verify verification code;
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_enterinfo);
        ambition = getIntent().getBundleExtra("ambition");
        viewPager = findViewById(R.id.viewPager);
        statusTitle = findViewById(R.id.titleTV);
        //initialize the working flow;
        switch(ambition.getInt("ambition")){
            case LOGIN:
                contentSet.add(NEXT_LOGIN);
                titleStringSet.add("Log In");
                break;
            case REGISTER:
                contentSet.add(NEXT_PHONEVERIFY);
                contentSet.add(NEXT_SETPASS);
                //contentSet.add(NEXT_SETBIO);

                titleStringSet.add("Verify your phone");
                titleStringSet.add("Set your password");
                //titleStringSet.add("Complete your info");
                break;
            case CHANGE_PASS:
                contentSet.add(NEXT_RESETPASS);
                titleStringSet.add("Change your password");
        }
        LogUtilViewPagerAdapter adapter = new LogUtilViewPagerAdapter(getSupportFragmentManager(),contentSet);
        viewPager.setAdapter(adapter);





    }






}
