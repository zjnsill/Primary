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

public class edit_account_info extends AppCompatActivity {//in this page, editing and then uploading the user's profile
    TopBar editingTopar;
    EditText NameEdit,EmailEdit,BioEdit,PhoneEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_info);

        Intent beforeEditing = getIntent();
        final signedUser currentUser = beforeEditing.getParcelableExtra("user");

        editingTopar = findViewById(R.id.topBar);
        NameEdit = findViewById(R.id.nameEdit);
        EmailEdit = findViewById(R.id.emailEdit);
        BioEdit = findViewById(R.id.BioEdit);
        PhoneEdit = findViewById(R.id.phoneNumEdit);
        NameEdit.setHint(currentUser.getName());
        EmailEdit.setHint(currentUser.getEmail());
        BioEdit.setHint(currentUser.getBio());
        BioEdit.setVerticalScrollBarEnabled(true);
        BioEdit.setMovementMethod(ScrollingMovementMethod.getInstance());
        PhoneEdit.setHint(currentUser.getPhone());
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
                if((!Name.isEmpty()&&(!PhoneNum.isEmpty()))){
                    if(!PhoneNum.equals(currentUser.getPhone())){
                        showVerifyWindow(PhoneNum);
                    }
                    //editing the user info and upload to cloud;
                }else{
                    Toast.makeText(edit_account_info.this,"Please fill Email and Name!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    void showVerifyWindow(String phoneNum){//if in register step,oldNum should be the default user's num;
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
        EditText newPhoneNum = VerifyView.findViewById(R.id.newPhone);
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
        newPhoneNum.setText(phoneNum);
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
                String getVerifyCode = verifyCode.getText().toString();
                //verify the received code;
            }
        });
    }

    void setWindowAlpha(float alpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

}
