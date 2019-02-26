package com.psi.shen.primary;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class edit_account_info extends AppCompatActivity {//in this page, editing and then uploading the user's profile
    TopBar editingTopar;
    EditText NameEdit,EmailEdit,BioEdit,PhoneEdit;
    //this view only accept parcel,that is, the complete signedUser

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_info);
        final signedUser currentUser;
        final int flag;
        //flag 1: register,2:editting info,3: forgot passcode,4: changing phone Num
        //register, pass visible; editting pass invisible; 3: forgot pass pass visible; 4: phone Num enable
        Intent beforeEditing = getIntent();
        currentUser = beforeEditing.getParcelableExtra("user");
        flag = beforeEditing.getIntExtra("flag",1);
        editingTopar = findViewById(R.id.topBar);
        NameEdit = findViewById(R.id.nameEdit);
        EmailEdit = findViewById(R.id.emailEdit);
        BioEdit = findViewById(R.id.BioEdit);
        PhoneEdit = findViewById(R.id.phoneNumEdit);

        NameEdit.setText(currentUser.getName());
        EmailEdit.setText(currentUser.getEmail());
        BioEdit.setText(currentUser.getBio());
        BioEdit.setVerticalScrollBarEnabled(true);
        BioEdit.setMovementMethod(ScrollingMovementMethod.getInstance());
        if(flag!=4){
            PhoneEdit.setEnabled(false);
            PhoneEdit.setText(currentUser.getPhone());
            BioEdit.setEnabled(false);
        }else{
            PhoneEdit.setEnabled(true);
            PhoneEdit.setText(currentUser.getPhone());
        }
        editingTopar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                edit_account_info.this.finish();
            }

            @Override
            public void rightListener() {
                String Name = NameEdit.getText().toString();
                String PhoneNum = PhoneEdit.getText().toString();
                String Bio = BioEdit.getText().toString();
                String Email = EmailEdit.getText().toString();
                switch (flag){
                    case 1:
                    case 2:
                        if(Name.isEmpty()){
                            Toast.makeText(edit_account_info.this,"Please enter user name",Toast.LENGTH_SHORT).show();
                        }else{
                            signedUser back = new signedUser.Builder(Name,PhoneNum).Bio(Bio).Email(Email).build();
                            //adding user to cloud
                            Intent backToBottomSheet = new Intent(edit_account_info.this,bottomSheet.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("edituser",back);
                            backToBottomSheet.putExtras(bundle);
                            backToBottomSheet.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(backToBottomSheet);
                        }
                        break;
                    /*case 3:{
                        if(Name.isEmpty()){
                            Toast.makeText(edit_account_info.this,"Please enter user name",Toast.LENGTH_SHORT).show();
                        }else{
                            signedUser back = new signedUser.Builder(Name,PhoneNum).Bio(Bio).Email(Email).build();
                            Intent backToBottomSheet = new Intent(edit_account_info.this,bottomSheet.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("user",back);
                            backToBottomSheet.putExtras(bundle);
                            backToBottomSheet.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(backToBottomSheet);
                        }
                    }
                    break;
                    case 4:{
                            showVerifyWindow(currentUser);
                        }
                    default:{}
                    break;*/
                }
            }
        });
    }

    /*void showVerifyWindow(signedUser user){//only for situation where phone changed;
        final signedUser userChangedPhone = user;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View VerifyView = layoutInflater.inflate(R.layout.verify_phone_num,null);
        View parentView = layoutInflater.inflate(R.layout.activity_edit_account_info,null);
        final PopupWindow verifyWindow = new PopupWindow(VerifyView,WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setWindowAlpha(0.2f);
        verifyWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });
        final EditText newPhoneNum = VerifyView.findViewById(R.id.PhoneNum);
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
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NameEdit.getText().toString().isEmpty()){
                    Toast.makeText(edit_account_info.this,"Enter Name!",Toast.LENGTH_SHORT).show();
                }
                else if(PhoneEdit.getText().toString().isEmpty()){
                    Toast.makeText(edit_account_info.this,"Enter phone number!",Toast.LENGTH_SHORT).show();
                }
                else{
                    String getVerifyCode = verifyCode.getText().toString();
                    //verify the received code;
                    //uploading the phone num;
                    Intent intent = new Intent(edit_account_info.this,bottomSheet.class);
                    Bundle bundle = new Bundle();
                    signedUser user = new signedUser.Builder(userChangedPhone.getName(),newPhoneNum.getText().toString()).Bio(userChangedPhone.getBio()).Email(userChangedPhone.getEmail()).build();
                    bundle.putParcelable("user",user);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    void setWindowAlpha(float alpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }
    */
}
