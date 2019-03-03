package com.psi.shen.primary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class edit_account_info extends AppCompatActivity {
    private TopBar editingTopar;
    private EditText PhoneEdit;
    private CardView SignOut;
    private PrefManager prefManager;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_info);
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
        PhoneEdit = findViewById(R.id.phoneNumEdit);
        SignOut = findViewById(R.id.signout);
        editingTopar = findViewById(R.id.topBar);
        prefManager = new PrefManager(this);
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        phone = sharedPreferences.getString("phone","");
        PhoneEdit.setText(phone);
        PhoneEdit.setEnabled(false);

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(edit_account_info.this,LoginRegisterEnterinfo.class);
                Bundle arg = new Bundle();
                arg.putInt("ambition",102);
                toLogin.putExtra("ambition",arg);
                toLogin.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(toLogin);
                edit_account_info.this.finish();
            }
        });
        editingTopar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                edit_account_info.this.finish();
            }
            @Override
            public void rightListener() {

            }
        });

    }
}
