package com.psi.shen.primary;

import android.content.Context;
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

public class viewPagerResetpass extends Fragment {

    public interface ResetPass{
        void changePass(String oldPass,String newPass);
    }

    ResetPass resetPass;
    //Bundle arg;
    //String phoneNumber;
    private EditText oldPassET,newPassET,RE_newPass;
    private Button changePassBtn;
    private TextView warningText;


    @Override
    public void onCreate(@Nullable Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        //arg = getArguments();
        //phoneNumber = arg.getString("phoneNum");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.viewpager_resetpass,null);
        oldPassET = view.findViewById(R.id.oldPass);
        newPassET = view.findViewById(R.id.newPass);
        RE_newPass = view.findViewById(R.id.newPass2);
        changePassBtn = view.findViewById(R.id.changePass_btn);
        warningText = view.findViewById(R.id.warning);

        changeBtnState(false,changePassBtn);
        warningText.setVisibility(View.INVISIBLE);
        oldPassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isAllEntered(oldPassET,newPassET,RE_newPass)) changeBtnState(true,changePassBtn);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newPassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(isAllEntered(oldPassET,newPassET,RE_newPass)) changeBtnState(true,changePassBtn);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        RE_newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isAllEntered(oldPassET,newPassET,RE_newPass)) changeBtnState(true,changePassBtn);
                if(!newPassET.getText().toString().equals(RE_newPass.getText().toString())){
                    warningText.setVisibility(View.VISIBLE);
                }
                else{warningText.setVisibility(View.INVISIBLE);}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass.changePass(oldPassET.getText().toString(),newPassET.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        resetPass = (ResetPass)context;
    }

    private void changeBtnState(boolean isValid,Button botton){
        if(isValid){
            botton.setClickable(true);
            botton.setBackgroundColor(0xff9e9e9e);
        }
        else{
            botton.setClickable(false);
            botton.setBackgroundColor(0xff99cc00);
        }
    }
    private boolean isAllEntered(EditText editText1,EditText editText2,EditText editText3){
        return !(editText1.getText().toString().isEmpty()|editText2.getText().toString().isEmpty()|
                editText3.getText().toString().isEmpty());
    }

}
