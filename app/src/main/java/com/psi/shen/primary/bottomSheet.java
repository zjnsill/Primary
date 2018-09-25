package com.psi.shen.primary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
        //register widgets
        bottomSheetView = findViewById(R.id.user_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheetView);
        nameTV = bottomSheetView.findViewById(R.id.signedUser);
        starredItemCountTV = bottomSheetView.findViewById(R.id.starredItemCount);
        signInTV = bottomSheetView.findViewById(R.id.signIn);
        signOutTV = bottomSheetView.findViewById(R.id.signOut);
        deleteInfoTV = bottomSheetView.findViewById(R.id.deleteInfo);
        welcome = findViewById(R.id.userName);
        editTV = findViewById(R.id.editTV);
        cancelTV = findViewById(R.id.cancelTV);
        enterEmail = findViewById(R.id.enterEmail);
        //functions to ask user to sign in;
        //currently return francis as the previously signed user;
        currentUser = "francis";
        String email = "francis@126.com";
        //
        //
        if(currentUser==defaultUser){
            signOutTV.setVisibility(View.INVISIBLE);
            signOutTV.setClickable(false);
            deleteInfoTV.setVisibility(View.INVISIBLE);
            deleteInfoTV.setClickable(false);
            signInTV.setVisibility(View.VISIBLE);
        }else{
            signOutTV.setClickable(true);
            signOutTV.setVisibility(View.VISIBLE);
            deleteInfoTV.setVisibility(View.VISIBLE);
            deleteInfoTV.setClickable(true);
            signInTV.setClickable(false);
            signInTV.setVisibility(View.INVISIBLE);
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
            public void onClick(View v) { }
        });//wait to be complement;
        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==defaultUser){
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                else{sheetAction();}
            }
        });
        //

        //
        enterEmail.setText(email);
        enterEmail.setFocusable(false);
        enterEmail.setFocusableInTouchMode(false);
        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTV.getText()=="Edit"){
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    editTV.setText("Done");
                    cancelTV.setVisibility(View.VISIBLE);
                    enterEmail.setFocusable(true);
                    enterEmail.setFocusableInTouchMode(true);
                    enterEmail.requestFocus();
                }
                if(editTV.getText()=="Done"){
                    enterEmail.setFocusableInTouchMode(false);
                    enterEmail.setFocusable(false);
                }
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


}
