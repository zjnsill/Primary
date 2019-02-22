package com.psi.shen.primary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class bottomSheet extends AppCompatActivity {
    private View bottomSheetView,backView,bottomsheetLower;
    private BottomSheetBehavior mBehavior;
    private TextView nameTV,starredItemCountTV,welcome,editTV,Email,phoneNum,leftTV,rightTV,Bio,expandIndicator;
    private CardView searchCV,createCV,starredCV,aboutCV;
    private signedUser defaultUser = signedUser.DefaultUser;
    private signedUser currentUser;
    private int MAX_Bio_Line=3,EXPANDED_Bio_Line=6;
    private UserDatabaseManager userDatabaseManager;
    private String phone;


    private long exitTime;

    //declaration of error type
    private static final int VERIFY_OK = 7;
    private static final int VERIFY_WRONG = 8;
    @IntDef({VERIFY_OK,VERIFY_WRONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PHONE_VERIFY{}

    private user_server userUtility = new user_server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        Intent ifSigned = getIntent();
        Bundle bundle = ifSigned.getExtras();
        phone = bundle.getString("id123");

        bottomSheetView = findViewById(R.id.user_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheetView);
        backView = findViewById(R.id.backView);
        bottomsheetLower = bottomSheetView.findViewById(R.id.bottomsheet_lowerPart);

        nameTV = bottomSheetView.findViewById(R.id.signedUser);//signed in as
        starredItemCountTV = bottomSheetView.findViewById(R.id.starredItemCount);//item currently stored in cloud
        //signInTV = bottomSheetView.findViewById(R.id.signIn);//sign in TextView
        leftTV = bottomSheetView.findViewById(R.id.signOut);//sign out TextView
        rightTV = bottomSheetView.findViewById(R.id.deleteInfo);//delete user INFO TextView
        welcome = findViewById(R.id.userName);//welcome on the top
        phoneNum = findViewById(R.id.phoneNum);
        Bio = findViewById(R.id.Bio);
        expandIndicator = findViewById(R.id.expandIndicator);
        editTV = bottomSheetView.findViewById(R.id.editTV);
        Email = bottomSheetView.findViewById(R.id.Email);


        //functions to ask user to sign in;
        //currently return francis as the previously signed user;
        //if the user is not signed in, use default user;

        String s = "one important lesson learnt from this lesson is that we can have a clear" +
                " target of what we should learn in the future. Give a example, in our product, " +
                "we used knowledge from liner algebra, virtual machine, computer network, data structure. " +
                "But, as we are yet, freshmen student, we don’t have a very deep understanding of these, we learnt by ourself  ";
        currentUser = new signedUser.Builder("shen","123456").Bio(s).Email("AlloyProject@sjtu.edu.cn").build();
        userDatabaseManager = UserDatabaseManager.getInstance(this,currentUser.getName());
        //
        //
        welcome.setText("Welcome "+currentUser.getName());
        nameTV.setText("Signed in as "+currentUser.getName());
        phoneNum.setText("Phone Number: "+currentUser.getPhone());
        starredItemCountTV.setText("Item starred: "+currentUser.getStarredItemCount());
        Email.setText(currentUser.getEmail());
        Bio.setMaxLines(MAX_Bio_Line);
        Bio.setText("Bio: "+currentUser.getBio());
        expandIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandIndicator.getText().toString().equals("...more")){
                    if(Bio.getLineCount()>MAX_Bio_Line){
                        Bio.setMaxLines(EXPANDED_Bio_Line);
                        Bio.requestLayout();
                        expandIndicator.setText("collapse");
                        Bio.setVerticalScrollBarEnabled(true);
                        Bio.setMovementMethod(ScrollingMovementMethod.getInstance());
                    }else {
                        expandIndicator.setText("collapse");
                    }

                }else if(expandIndicator.getText().toString().equals("collapse")){
                    Bio.setMaxLines(MAX_Bio_Line);
                    Bio.requestLayout();
                    expandIndicator.setText("...more");
                    Bio.setVerticalScrollBarEnabled(false);
                    Bio.setMovementMethod(null);
                }
            }
        });


        //
        //after calling the following methods, create a new intent and redirect to this activity other than using finish();
        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editAccountInfo = new Intent(bottomSheet.this,edit_account_info.class);
                Bundle userInfo = new Bundle();
                userInfo.putParcelable("user",currentUser);
                editAccountInfo.putExtras(userInfo);
                editAccountInfo.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(editAccountInfo);
            }
        });

        if(currentUser.equals(defaultUser)){
            leftTV.setText("Sign In");
            leftTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //implement sign in action;

                }
            });
            rightTV.setText("Register");
            rightTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVerifyWindow("",1);
                }
            });
            editTV.setVisibility(View.INVISIBLE);
            editTV.setClickable(false);
        }else{
            leftTV.setText("Sign Out");
            leftTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //implement sign out actiton;
                }
            });
            rightTV.setText("Delete user info");
            rightTV.setTextColor(Color.rgb(255,69,58));
            rightTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //implement delete user Info action;
                }
            });
            editTV.setVisibility(View.VISIBLE);
            editTV.setClickable(true);
        }

        backView.setDrawingCacheEnabled(true);
        //
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        searchCV = findViewById(R.id.SearchCV);
        createCV = findViewById(R.id.CreateCV);
        starredCV = findViewById(R.id.StarredCV);
        aboutCV = findViewById(R.id.AboutCV);

        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(bottomSheet.this, Search.class);
                searchIntent.putExtra("userName", currentUser.getName());
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchIntent);
            }
        });
        createCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createintent = new Intent(bottomSheet.this, CreateAlloy.class);
                createintent.putExtra("userName", currentUser.getName());
                createintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(createintent);
            }
        });
        starredCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starredIntent = new Intent(bottomSheet.this,StarredList.class);
                starredIntent.putExtra("userName",currentUser.getName());
                starredIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(starredIntent);
            }
        });
        aboutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(bottomSheet.this, AboutActivity.class);
                aboutIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(aboutIntent);
            }
        });

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetAction();
            }
        });


        //end of onCreate Methods;
    }

    public void sheetAction(){
        if(mBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        if(mBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }





    void ShowSignInView(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View signInView = layoutInflater.inflate(R.layout.popup_user_signin,null);
        View parentView = layoutInflater.inflate(R.layout.activity_bottom_sheet,null);

        final EditText phoneNum = signInView.findViewById(R.id.phoneNum);
        final EditText passcode = signInView.findViewById(R.id.Passcode);
        TextView CancelTV = signInView.findViewById(R.id.CancelTV);
        TextView SignInTV = signInView.findViewById(R.id.SignInTV);
        TextView forgotPasscode = signInView.findViewById(R.id.forgotPasscode);

        final PopupWindow signInPopView = new PopupWindow(signInView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setWindowAlpha(0.2f);
        signInPopView.setFocusable(true);
        signInPopView.setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        signInPopView.setAnimationStyle(R.style.sign_in_popup);
        signInPopView.setBackgroundDrawable(dw);
        signInPopView.showAtLocation(parentView, Gravity.CENTER,0,0);
        signInPopView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1f);

            }
        });
        CancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInPopView.dismiss();
            }
        });
        SignInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNum.getText().toString().isEmpty()){
                    Toast.makeText(bottomSheet.this,"Please enter your phone number!",Toast.LENGTH_SHORT).show();
                }else if(passcode.getText().toString().isEmpty()){
                    Toast.makeText(bottomSheet.this,"Please enter your passcodes!",Toast.LENGTH_SHORT).show();
                }else{
                    signedUser returnedUser = userUtility.SignIn(phoneNum.getText().toString(),passcode.getText().toString());
                    if(returnedUser.getErrorCode()==signedUser.NO_SUCH_USER){
                        Toast.makeText(bottomSheet.this,"User doesn't exist!",Toast.LENGTH_SHORT).show();
                    }else if(returnedUser.getErrorCode()==signedUser.INCORRECT_PASSWORD){
                        Toast.makeText(bottomSheet.this,"Wrong passcode!",Toast.LENGTH_SHORT).show();
                    }else if(returnedUser.getErrorCode()==signedUser.SUCCESS){
                        Intent userGot = new Intent(bottomSheet.this,bottomSheet.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("user",returnedUser);
                        userGot.putExtras(bundle);
                        userGot.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        finish();//to end the current activity and reload with the successfully signed user;
                        startActivity(userGot);
                    }else {
                        Toast.makeText(bottomSheet.this,"Operation Failed, please try latter!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        forgotPasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInPopView.dismiss();
                showVerifyWindow(currentUser.getPhone(),3);
                //change passcode and require to sign in again or automatically sign in

            }
        });

    }
    void setWindowAlpha(float alpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    public void showVerifyWindow(String Num, final int flag){//flag 1: register,2:editting info,3: forgot passcode 4: changing phone number
        @SMS_Service.SMSERROR int returnERRORInt;
        final Handler returnValListener = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==1){
                }
            }
        };
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View VerifyView = layoutInflater.inflate(R.layout.verify_phone_num,null);
        View parentView = layoutInflater.inflate(R.layout.activity_bottom_sheet,null);
        final PopupWindow verifyWindow = new PopupWindow(VerifyView,WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setWindowAlpha(0.2f);
        verifyWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });

        final EditText phoneNum = VerifyView.findViewById(R.id.PhoneNum);
        final EditText verifyCode = VerifyView.findViewById(R.id.VerifyCode);
        final TextView sendCode = VerifyView.findViewById(R.id.SendCode);
        TextView cancel = VerifyView.findViewById(R.id.VerifyCancel);
        final TextView verify = VerifyView.findViewById(R.id.VerifyVerify);
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification code;
            }
        });
        if(flag==1){
            phoneNum.setText(null);
            phoneNum.setHint("Enter your phone number");
        }else{
            phoneNum.setText(Num);
        }
        verifyWindow.setFocusable(true);
        verifyWindow.setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        verifyWindow.setBackgroundDrawable(dw);
        verifyWindow.setAnimationStyle(R.style.sign_in_popup);
        verifyWindow.showAtLocation(parentView,Gravity.CENTER,0,0);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyWindow.dismiss();
                Message msg = new Message();
                msg.what = 1;
                returnValListener.sendMessage(msg);

            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNum.getText().toString().isEmpty()){
                    Toast.makeText(bottomSheet.this,"Please enter phone number!",Toast.LENGTH_SHORT).show();
                }else if(verifyCode.getText().toString().isEmpty()){
                    Toast.makeText(bottomSheet.this,"Please enter verify code!",Toast.LENGTH_SHORT).show();
                }else{
                    //verify code;
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                logoutApp();
            }
        }
        return true;
    }

    private void logoutApp() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "Press again to exit the Application", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        backView.setDrawingCacheEnabled(false);
    }
}
