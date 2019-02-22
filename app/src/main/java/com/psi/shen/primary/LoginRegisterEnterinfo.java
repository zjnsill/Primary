package com.psi.shen.primary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginRegisterEnterinfo extends AppCompatActivity implements viewPagerLogin.login,viewPagerVerifyPhone.VerifyPhoneUtil,
viewPagerResetpass.ResetPass,viewPagerSetPass.setPasscode,viewPagerSetBio.setedBioEmail{
    ViewPager viewPager;
    TextView statusTitle;
    ArrayList<String> titleStringSet=new ArrayList<>();
    ArrayList<Integer> contentSet = new ArrayList<>();
    Bundle ambition;
    signedUser currentUser;
    private String phonenumber;
    //TODO 完善这个变量的传输；
    //这个用户数据在之后改其他东西的时候可能会用到；
    private EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                        } else {
                            ((Throwable) data).printStackTrace();
                            Toast.makeText(LoginRegisterEnterinfo.this, "send failed", Toast.LENGTH_SHORT).show();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证成功的结果
                            SharedPreferences sp=getSharedPreferences("config",0);
                            SharedPreferences.Editor editor=sp.edit();
                            //把数据进行保存
                            editor.putString("phone",phonenumber);
                            //提交数据
                            editor.commit();
                            Intent tobottom = new Intent(LoginRegisterEnterinfo.this,bottomSheet.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("id123", phonenumber);
                            tobottom.putExtras(mBundle);
                            startActivity(tobottom);
                            LoginRegisterEnterinfo.this.finish();
                        } else {
                            Toast.makeText(LoginRegisterEnterinfo.this, "Wrong code", Toast.LENGTH_SHORT).show();
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    return false;
                }
            }).sendMessage(msg);
        }
    };
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
        SMSSDK.registerEventHandler(eventHandler);
        phonenumber = Phone;
        SMSSDK.getVerificationCode("86", Phone);
    }
    @Override
    public void verifyCode(String phone,String Code){
        //TODO verify verification code;
        SMSSDK.submitVerificationCode("86", phone, Code);
    }
    @Override
    public void changePass(String oldPass,String newPass){
        //TODO change the passcode, the following user phone number can be used;
        String user_phone = currentUser.getPhone();
    }
    @Override
    public void setedPasscode(String passcode){
        //TODO set passcode for the user;
        String user_phone = currentUser.getPhone();
    }
    @Override
    public void set_Bio(String Bio){
        //TODO update Bio;
    }
    @Override
    public void set_Email(String Email){
        //TODO update Email;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_enterinfo);
        ambition = getIntent().getBundleExtra("ambition");
        viewPager = findViewById(R.id.viewPager);
        statusTitle = findViewById(R.id.titleTV);
        //initialize the working flow;
        switch (ambition.getInt("ambition")) {
            case LOGIN:
                contentSet.add(NEXT_LOGIN);
                titleStringSet.add("Log In");
                break;
            case REGISTER:
                MobSDK.init(this);
                contentSet.add(NEXT_PHONEVERIFY);
                contentSet.add(NEXT_SETPASS);
                //contentSet.add(NEXT_SETBIO);
                titleStringSet.add("Verify your phone");
                titleStringSet.add("Set your password");
                //titleStringSet.add("Complete your info");
                break;
            case CHANGE_PASS:
                MobSDK.init(this);
                contentSet.add(NEXT_RESETPASS);
                titleStringSet.add("Change your password");
        }
        LogUtilViewPagerAdapter adapter = new LogUtilViewPagerAdapter(getSupportFragmentManager(), contentSet);
        viewPager.setAdapter(adapter);
    }






}
