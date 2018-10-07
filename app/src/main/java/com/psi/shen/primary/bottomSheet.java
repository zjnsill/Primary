package com.psi.shen.primary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class bottomSheet extends AppCompatActivity {
    private View bottomSheetView;
    private BottomSheetBehavior mBehavior;
    private TextView nameTV,starredItemCountTV,welcome,editTV,Email,phoneNum,leftTV,signInTV,rightTV,Bio,expandIndicator;
    private CardView searchCV,createCV,starredCV,aboutCV;
    private signedUser defaultUser = signedUser.DefaultUser;
    private signedUser currentUser;
    private int MAX_Bio_Line=3,EXPANDED_Bio_Line=6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        Intent ifSigned = getIntent();
        if(ifSigned.getParcelableExtra("user")!=null){//1 represent as from other activities;
            currentUser = ifSigned.getParcelableExtra("user");
            //receive intent from other views;
        }
        bottomSheetView = findViewById(R.id.user_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheetView);

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
        //currentUser = new signedUser.Builder(defaultUser.getName(),defaultUser.getPhone()).build();
        String s = "one important lesson learnt from this lesson is that we can have a clear" +
                " target of what we should learn in the future. Give a example, in our product, " +
                "we used knowledge from liner algebra, virtual machine, computer network, data structure. " +
                "But, as we are yet, freshmen student, we don’t have a very deep understanding of these, we learnt by ourself  ";
        currentUser = new signedUser.Builder("francis","123456789").Bio(s).Email("AlloyProject@sjtu.edu.cn").build();
        currentUser = defaultUser;//only for test use
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

        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editAccountInfo = new Intent(bottomSheet.this,edit_account_info.class);
                Bundle userInfo = new Bundle();
                userInfo.putParcelable("user",currentUser);
                editAccountInfo.putExtras(userInfo);
                // a new pop up window to modify user info
                //after changing the profile, automatically jump back to this page and autoamtically sign in;
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
                    //register
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
                Intent searchIntent = new Intent(bottomSheet.this,Search.class);
                searchIntent.putExtra("userName",currentUser.getName());
                startActivity(searchIntent);
            }
        });
        createCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createintent = new Intent(bottomSheet.this,CreateAlloy.class);
                createintent.putExtra("userName",currentUser.getName());
                startActivity(createintent);
            }
        });
        starredCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starredIntent = new Intent(bottomSheet.this,StarredList.class);
                starredIntent.putExtra("userName",currentUser.getName());
                startActivity(starredIntent);
            }
        });
        aboutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //wait to be complement;
            }
        });

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetAction();
            }
        });



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

        EditText phoneNum = signInView.findViewById(R.id.phoneNum);
        EditText passcode = signInView.findViewById(R.id.Passcode);
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
                //request for logging in
                //return relative information;

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

    void showVerifyWindow(String Num, final int flag){//flag 1: register,2:editting info,3: forgot passcode 4: changing phone number
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

        EditText PhoneNum = VerifyView.findViewById(R.id.phoneNum);
        final EditText verifyCode = VerifyView.findViewById(R.id.VerifyCode);
        TextView sendCode = VerifyView.findViewById(R.id.SendCode);
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
                Toast.makeText(bottomSheet.this,"User canceled this process!",Toast.LENGTH_SHORT).show();
                //implement cancel action;
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

                    Intent toEdittingInfo = new Intent(bottomSheet.this,edit_account_info.class);
                    Bundle bundle = new Bundle();
                    signedUser user = new signedUser.Builder(currentUser.getName(),phoneNum.getText().toString()).Email(currentUser.getEmail()).Bio(currentUser.getBio()).build();
                    bundle.putParcelable("user",user);
                    bundle.putInt("flag",flag);
                    toEdittingInfo.putExtras(bundle);
                    startActivity(toEdittingInfo);
                }
            }
        });

    }


}
