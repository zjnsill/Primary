package com.psi.shen.primary;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StarredList extends AppCompatActivity {

    private int startX, startY;

    private static final String TAG = "MainActivity";

    private TopBar StarredListTopbar;
    private signedUser currentUser;
    private ArrayList<Bundle> starredAlloys = new ArrayList<>();

    private long countDownTime = 10000;
    private long countDownInterval = 1;

    private RecyclerView recyclerView;
    private StarredListAdapter starredListAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if(message.what == 0) {
                Bundle bundle = message.getData();
                starredAlloys.add(bundle);
            } else if(message.what == 1) {
                String alloy = (String) message.obj;
                for(int i = 0; i < starredAlloys.size(); i++) {
                    if(alloy.equals(starredAlloys.get(i).getString("Name"))) {
                        starredAlloys.remove(i);
                        break;
                    }
                }
            }
        }
    };

    private LoadingDialog dialog;

    private final Handler refreshHandler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
                dialog.close();
            starredListAdapter.notifyDataSetChanged();
            if(msg.what == 1)
                Toast.makeText(StarredList.this,"Alloy unstarred!",Toast.LENGTH_SHORT).show();
        }
    };

    private final CountDownTimer countDown = new CountDownTimer(countDownTime, countDownInterval) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            dialog.close();
            Toast.makeText(StarredList.this,"Request time out, please try again!",Toast.LENGTH_SHORT).show();
            refreshHandler.removeCallbacksAndMessages(null);
            StarredList.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_list);

        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
        Intent nameIntent = getIntent();
        this.currentUser = nameIntent.getParcelableExtra("user");

        StarredListTopbar = findViewById(R.id.StarredListTopbar);
        StarredListTopbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                StarredList.this.finish();
            }

            @Override
            public void rightListener() {
                refreshList();
            }
        });

        recyclerView = findViewById(R.id.starredRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        starredListAdapter = new StarredListAdapter(this, starredAlloys);
        starredListAdapter.setOnItemClickListener(new StarredListAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle alloy = starredAlloys.get(position);
                Intent jumpToDetail = new Intent(StarredList.this, detailed_alloy.class);
                jumpToDetail.putExtra("alloy", alloy);
                jumpToDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(jumpToDetail);
            }

            @Override
            public void onLongClick(final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        removeStarredItem(starredAlloys.get(position).getString("Name"));
                        refreshHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        recyclerView.setAdapter(starredListAdapter);

        dialog = new LoadingDialog(this,"Loading data");
        dialog.show();
        countDown.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getStarredAlloys();
                refreshHandler.sendEmptyMessage(0);
                countDown.cancel();
            }
        }).start();
    }

    private void getStarredAlloys() {
        String path = "http://118.25.122.232/android_connect/getStarredItems.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", currentUser.getPhone());
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt("errorCode");
                if(errorCode == 0) {
                    JSONArray jsonArray = jsonObject.getJSONArray("starredItems");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject alloy = jsonArray.getJSONObject(i);
                        Bundle bundle = new Bundle();
                        for(Iterator<String> keys = alloy.keys(); keys.hasNext(); ) {
                            String key = keys.next();
                            String value = alloy.getString(key);
                            //Log.i(TAG, key1 + "---" + alloy.getString(key1));
                            if(!value.equals("null")) {
                                bundle.putString(key, value);
                            }
                        }
                        Message message = Message.obtain();
                        message.setData(bundle);
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeStarredItem(String name) {
        String path = "http://118.25.122.232/android_connect/removeStarredItem.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", currentUser.getPhone());
            formBody.add("RemoveStarredItem", name);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "" + response.code());
                Log.i(TAG, "" + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                if(jsonObject.getInt("errorCode") == 0) {
                    Message message = Message.obtain();
                    message.obj = name;
                    message.what = 1;
                    handler.sendMessage(message);
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshList() {
        starredAlloys.clear();
        dialog = new LoadingDialog(this,"Loading data");
        dialog.show();
        countDown.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getStarredAlloys();
                refreshHandler.sendEmptyMessage(0);
                countDown.cancel();
            }
        }).start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int)event.getRawX();
                startY = (int)event.getRawY();
            case MotionEvent.ACTION_MOVE:
                int endX = (int)event.getRawX();
                int endY = (int)event.getRawY();
                if(Math.abs(endX - startX) > Math.abs(endY - startY)) {
                    if(endX > startX && (endX - startX) > ScreenSizeUtils.getInstance(this).getScreenWidth() / 5) {
                        this.finish();
                    }
                }
        }
        return super.dispatchTouchEvent(event);
    }
}
