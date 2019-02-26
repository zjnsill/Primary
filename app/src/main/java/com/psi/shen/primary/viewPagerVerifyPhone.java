package com.psi.shen.primary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class viewPagerVerifyPhone extends Fragment {
    //回调接口
    public interface VerifyPhoneUtil{
        void SendVerifyCode(String phone);
        void verifyCode(String phone,String Code);
    }
    VerifyPhoneUtil verifyUtil;
    //变量定义
    private EditText verifyphoenET,verificationCodeET;
    private Button getCodeBtn,verifyBtn;
    private TimeCount time;
    @Override
    public void onCreate(@Nullable Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.viewpager_verify_phone,null);
        verifyphoenET = view.findViewById(R.id.phoneNum_verify);
        verificationCodeET = view.findViewById(R.id.verifyCode);
        getCodeBtn = view.findViewById(R.id.getCode_btn);
        verifyBtn = view.findViewById(R.id.verify_btn);

        getCodeBtn.setEnabled(false);
        getCodeBtn.setBackgroundResource(R.color.btn_unclickable_grey);
        verifyBtn.setEnabled(false);
        verifyBtn.setBackgroundResource(R.color.btn_unclickable_grey);
        time = new TimeCount(60000, 1000);

        verifyphoenET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    getCodeBtn.setEnabled(true);
                    getCodeBtn.setBackgroundResource(R.color.btn_clickable_orange);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verificationCodeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!verifyphoenET.getText().toString().isEmpty()&!s.toString().isEmpty()){
                    verifyBtn.setEnabled(true);
                    verifyBtn.setBackgroundResource(R.color.btn_clickable_orange);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstancState){
        super.onActivityCreated(savedInstancState);
        getCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.start();
                verifyUtil.SendVerifyCode(verifyphoenET.getText().toString());
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUtil.verifyCode(verifyphoenET.getText().toString(),verificationCodeET.getText().toString());
            }
        });

    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        verifyUtil = (VerifyPhoneUtil)context;
    }

    public static Fragment newInstance(){
        viewPagerVerifyPhone fragment = new viewPagerVerifyPhone();
        return fragment;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeBtn.setClickable(false);
            getCodeBtn.setText("(" + millisUntilFinished / 1000 + ") ");
            getCodeBtn.setBackgroundResource(R.color.btn_unclickable_grey);
        }

        @Override
        public void onFinish() {
            getCodeBtn.setText("GET AGAIN");
            getCodeBtn.setClickable(true);
            getCodeBtn.setBackgroundResource(R.color.btn_clickable_orange);
        }
    }
}
