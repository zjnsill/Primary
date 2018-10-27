package com.psi.shen.primary;

import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class viewPagerLogin extends Fragment {
    //private Bundle arg;
    private EditText phoneNumET,passcodeET;
    private TextView forgetPassTV;
    private Button loginBtn;
    private login loginhelper;

    public interface login{
        //TODO implete the forget pass function;
        void submitLogin(@LoginRegisterEnterinfo.NEXT_STEP int currentFragment, String phoneNum, String passCode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        //arg = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.viewpager_login,null);
        phoneNumET =  view.findViewById(R.id.phoneNum);
        passcodeET = view.findViewById(R.id.passcode);
        forgetPassTV = view.findViewById(R.id.forgetPass);
        loginBtn = view.findViewById(R.id.logIn_btn);
        loginBtn.setClickable(false);
        loginBtn.setBackgroundColor(getResources().getColor(R.color.smssdk_lv_tv_color));
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstancState){
        super.onActivityCreated(savedInstancState);
        final Activity activity = getActivity();
        passcodeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()&!phoneNumET.getText().toString().isEmpty()){
                    loginBtn.setClickable(true);
                    loginBtn.setBackgroundColor(0xff99cc00);
                }
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNumET.getText().toString().isEmpty()){ Toast.makeText(activity,"Please enter phone number!",Toast.LENGTH_SHORT).show(); }
                else if(passcodeET.getText().toString().isEmpty()){Toast.makeText(activity,"Please enter passcode",Toast.LENGTH_SHORT).show();}
                else{ loginhelper.submitLogin(LoginRegisterEnterinfo.NEXT_LOGIN,phoneNumET.getText().toString(),passcodeET.getText().toString());}
            }
        });
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        loginhelper=(login)context;
    }

    public static Fragment newInstance(){
        //如果需要什么参数可以在这个里面使用setArgument传入数据，来源为newInstance的参数；
        viewPagerLogin fragment = new viewPagerLogin();
        return fragment;
    }

}
