package com.psi.shen.primary;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StarredList extends AppCompatActivity {
    private TopBar StarredListTopbar;
    private String currentUser;
    private ArrayList<StarredListItem> starredListData = new ArrayList<>();
    private ArrayList<StarredListItem> waitingToCloud=new ArrayList<>();
    private ArrayList<StarredListItem> waitingToDelete=new ArrayList<>();
    private UserDatabaseManager userDatabaseManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_list);

        Intent nameIntent = getIntent();
        this.currentUser = nameIntent.getStringExtra("userName");//need modification in relative java file;
        userDatabaseManager = UserDatabaseManager.getInstance(this,currentUser);
        StarredListTopbar = findViewById(R.id.StarredListTopbar);
        StarredListTopbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                waitingToCloud.clear();
                waitingToDelete.clear();
                StarredList.this.finish();

            }

            @Override
            public void rightListener() {
                Toast ts = Toast.makeText(StarredList.this,"Loading",Toast.LENGTH_SHORT);
                ts.show();
                DeleteQueue();
                upLoadingQueueItem();
                refreshList();
            }
        });
        starredListData = userDatabaseManager.GetAllItems();

        RecyclerView recyclerView = findViewById(R.id.starredRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final StarredListAdapter starredListAdapter = new StarredListAdapter(this,starredListData);
        starredListAdapter.setOnItemClickListener(new StarredListAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent jumpToDetail = new Intent(StarredList.this,detaied_alloy.class);
                Bundle itemBundle = new Bundle();
                itemBundle.putParcelable("clickedItem",starredListData.get(position).getAlloyItem());
                jumpToDetail.putExtras(itemBundle);
                jumpToDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(jumpToDetail);
            }

            @Override
            public void onLongClick(int position) {
                Toast ts = Toast.makeText(StarredList.this,"Clicked",Toast.LENGTH_SHORT);
                ts.show();
                showBottomDialog(position,starredListAdapter);
            }
        });
        recyclerView.setAdapter(starredListAdapter);
    }

    private void showBottomDialog(final int position, final StarredListAdapter starredListAdapter){
        final Dialog dialog = new Dialog(this,R.style.Theme_AppCompat_Dialog);
        View view = View.inflate(this,R.layout.bottom_sheet_dialog,null);
        final RelativeLayout toCloudRL,toDeleteRL;
        final TextView cancelTV;
        toCloudRL = view.findViewById(R.id.toCloud);
        toDeleteRL = view.findViewById(R.id.toDelete);
        cancelTV=view.findViewById(R.id.cancelTV);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        view.setMinimumHeight((int)(ScreenSizeUtils.getInstance(this).getScreenHeight()*0.23f));///
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);//神他妈狗屎透明度设置
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width=(int)(ScreenSizeUtils.getInstance(this).getScreenWidth());//原来是*0.9f
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        dialog.show();

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        toCloudRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(starredListData.get(position).isCould){
                    Toast ts = Toast.makeText(StarredList.this,"Item already in cloud!",Toast.LENGTH_SHORT);
                    ts.show();
                }
                else{
                     Toast ts = Toast.makeText(StarredList.this,"This Item will be uploaded!",Toast.LENGTH_SHORT);
                     ts.show();
                     waitingToCloud.add(starredListData.get(position));
                }
                starredListData.get(position).isToBeClouded = true;
                starredListData.get(position).isToBeDeleted= false;
                starredListAdapter.notifyItemChanged(position);
                userDatabaseManager.updateStarredItem(starredListData.get(position));
                dialog.dismiss();
                dialog.cancel();
            }
        });
        toDeleteRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(starredListData.get(position).isToBeClouded||waitingToCloud.contains(starredListData.get(position))){
                    Toast ts = Toast.makeText(StarredList.this,"This item will " +
                            "be removed from uploading queue and deleted from database",Toast.LENGTH_LONG);
                    ts.show();
                    waitingToCloud.remove(starredListData.get(position));
                }
                else{
                    Toast ts = Toast.makeText(StarredList.this,"This item will be deleted!",Toast.LENGTH_LONG);
                    ts.show();
                }
                waitingToDelete.add(starredListData.get(position));
                starredListData.get(position).isToBeDeleted = true;
                starredListData.get(position).isToBeClouded=false;
                starredListData.get(position).isCould = false;
                starredListAdapter.notifyItemChanged(position);
                dialog.dismiss();
                dialog.cancel();
            }

        });
    }


    void upLoadingQueueItem(){}

    void refreshList(){
        Intent refresh = new Intent(StarredList.this,StarredList.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(refresh);
        finish();
        overridePendingTransition(0,0);
    }

    void DeleteQueue(){
        int queueSize = waitingToDelete.size();
        for(int i=0;i<queueSize;i++){
            userDatabaseManager.deleteStarredItem(waitingToDelete.get(i).getAlloyItem().getAlloyName());
        }
        waitingToDelete.clear();
    }







}
