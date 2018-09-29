package com.psi.shen.primary;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class bottomSheet extends AppCompatActivity {
    private View bottomSheetView;
    private BottomSheetBehavior mBehavior;
    private TextView nameTV,starredItemCountTV,welcome,editTV,cancelTV,signOutTV,signInTV,deleteInfoTV;
    private CardView searchCV,createCV,starredCV,aboutCV;
    private EditText enterEmail;
    private String currentUser;
    private String defaultUser = "tempUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        currentUser = defaultUser;

        bottomSheetView = findViewById(R.id.user_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheetView);

        nameTV = bottomSheetView.findViewById(R.id.signedUser);//signed in as
        starredItemCountTV = bottomSheetView.findViewById(R.id.starredItemCount);//item currently stored in cloud
        signInTV = bottomSheetView.findViewById(R.id.signIn);//sign in TextView
        signOutTV = bottomSheetView.findViewById(R.id.signOut);//sign out TextView
        deleteInfoTV = bottomSheetView.findViewById(R.id.deleteInfo);//delete user INFO TextView
        welcome = findViewById(R.id.userName);//welcome on the top
        editTV = bottomSheetView.findViewById(R.id.editTV);
        enterEmail = bottomSheetView.findViewById(R.id.enterEmail);


        //functions to ask user to sign in;
        //currently return francis as the previously signed user;
        //if the user is not signed in, use default user;
        currentUser = defaultUser;
        String email = "francis@126.com";
        //
        //

        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // a new pop up window to modify user info
            }
        });
        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSignInView();
            }
        });

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
                searchIntent.putExtra("userName",currentUser);
                startActivity(searchIntent);
            }
        });
        createCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createintent = new Intent(bottomSheet.this,CreateAlloy.class);
                createintent.putExtra("userName",currentUser);
                startActivity(createintent);
            }
        });
        starredCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starredIntent = new Intent(bottomSheet.this,StarredList.class);
                starredIntent.putExtra("userName",currentUser);
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

        if(currentUser.equals(defaultUser)){
            signOutTV.setVisibility(View.INVISIBLE);
            signOutTV.setClickable(false);
            deleteInfoTV.setVisibility(View.INVISIBLE);
            deleteInfoTV.setClickable(false);
            signInTV.setVisibility(View.VISIBLE);
            signInTV.setClickable(true);
            editTV.setVisibility(View.INVISIBLE);
            editTV.setClickable(false);
            enterEmail.setText(email);
        }else{
            signOutTV.setClickable(true);
            signOutTV.setVisibility(View.VISIBLE);
            deleteInfoTV.setVisibility(View.VISIBLE);
            deleteInfoTV.setClickable(true);
            signInTV.setClickable(false);
            signInTV.setVisibility(View.INVISIBLE);
            editTV.setVisibility(View.VISIBLE);
            editTV.setClickable(true);
        }

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

        TextView sendCodeAction = signInView.findViewById(R.id.SendCode);
        EditText phoneNum = signInView.findViewById(R.id.phoneNum);
        EditText validationCode = signInView.findViewById(R.id.validationCode);
        TextView CancelTV = signInView.findViewById(R.id.CancelTV);
        TextView SignInTV = signInView.findViewById(R.id.SignInTV);

        final PopupWindow signInPopView = new PopupWindow(signInView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setWindowAlpha(0.2f);
        signInPopView.setFocusable(false);
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
                //return relative infomations

            }
        });

    }
    void setWindowAlpha(float alpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }




}
