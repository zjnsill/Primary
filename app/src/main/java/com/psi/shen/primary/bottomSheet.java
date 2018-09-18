package com.psi.shen.primary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class bottomSheet extends AppCompatActivity {
    private View bottomSheetView;
    private BottomSheetBehavior mBehavior;
    private TextView nameTV,starredItemCountTV,createdcountTV;
    private ImageView accountImg;
    private CardView createCV,searchCV,starredCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        bottomSheetView = findViewById(R.id.user_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheetView);
        nameTV = bottomSheetView.findViewById(R.id.signedUser);
        starredItemCountTV = bottomSheetView.findViewById(R.id.starredItemCount);
        createdcountTV = bottomSheetView.findViewById(R.id.createditemCount);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        accountImg = findViewById(R.id.accountImg);
        accountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    setBottomSheetExpand(bottomSheetView);
                }
                if(mBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                    setBottomSheetCollapsed(bottomSheetView);
                }
            }
        });
        createCV = findViewById(R.id.createCV);
        starredCV = findViewById(R.id.starredCV);
        searchCV = findViewById(R.id.searchCV);
        createCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToCreate = new Intent(bottomSheet.this,CreateAlloy.class);
                startActivity(jumpToCreate);
            }
        });
        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToSearch = new Intent(bottomSheet.this,Search.class);
                startActivity(jumpToSearch);
            }
        });
        starredCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToStarred = new Intent(bottomSheet.this, StarredList.class);
                startActivity(jumpToStarred);
            }
        });

    }


    public void setBottomSheetExpand(View view){
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    public void setBottomSheetCollapsed(View view){
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


}
