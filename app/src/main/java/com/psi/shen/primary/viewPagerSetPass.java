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

public class viewPagerSetPass extends Fragment {
    public interface setPasscode{
        void setedPasscode(String passcode);
    }
    setPasscode msetPasscode;
    private EditText passcodeSet1ET,passcodeSet2ET;
    private Button setPassBtn;
    TextView warningTextSetPass;



    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.viewpager_set_pass,null);
        passcodeSet1ET = view.findViewById(R.id.passcodeSet1);
        passcodeSet2ET = view.findViewById(R.id.passcodeSet2);
        setPassBtn = view.findViewById(R.id.setPass_btn);
        warningTextSetPass = view.findViewById(R.id.warningText_setpass);

        warningTextSetPass.setVisibility(View.INVISIBLE);
        setPassBtn.setClickable(false);
        setPassBtn.setBackgroundResource(R.color.btn_unclickable_grey);

        passcodeSet1ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passcodeSet2ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        setPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msetPasscode.setedPasscode(passcodeSet1ET.getText().toString());
            }
        });
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        msetPasscode= (setPasscode)context;
    }
    private void check(){
        if(!(passcodeSet1ET.getText().toString().isEmpty()|passcodeSet2ET.getText().toString().isEmpty())){
            setPassBtn.setClickable(false);
            setPassBtn.setBackgroundResource(R.color.btn_unclickable_grey);
        }else if(passcodeSet1ET.getText().toString().equals(passcodeSet2ET.getText().toString())){
            warningTextSetPass.setVisibility(View.VISIBLE);
        }else{
            setPassBtn.setClickable(true);
            setPassBtn.setBackgroundResource(R.color.btn_clickable_green);
            warningTextSetPass.setVisibility(View.INVISIBLE);
        }
    }
    public Fragment newInstance(){
        viewPagerSetPass fragment = new viewPagerSetPass();
        return fragment;
    }

}
