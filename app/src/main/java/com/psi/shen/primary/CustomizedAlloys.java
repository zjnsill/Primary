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

public class CustomizedAlloys extends AppCompatActivity {

    private int startX, startY;

    private static final String TAG = "MainActivity";

    private signedUser currentUser;

    private TopBar topBar;
    private RoundRectBtn addBtn;

    private ArrayList<Bundle> customizedAlloys = new ArrayList<>();

    private long countDownTime = 10000;
    private long countDownInterval = 1;

    private RecyclerView recyclerView;
    private CustomizedAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if(message.what == 0) {
                Bundle bundle = message.getData();
                customizedAlloys.add(bundle);
            } else if(message.what == 1) {
                String alloy = (String) message.obj;
                for(int i = 0; i < customizedAlloys.size(); i++) {
                    if(alloy.equals(customizedAlloys.get(i).getString("Name"))) {
                        customizedAlloys.remove(i);
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
            adapter.notifyDataSetChanged();
            if(msg.what == 1)
                Toast.makeText(CustomizedAlloys.this,"Alloy deleted!",Toast.LENGTH_SHORT).show();
        }
    };

    private final CountDownTimer countDown = new CountDownTimer(countDownTime, countDownInterval) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            dialog.close();
            Toast.makeText(CustomizedAlloys.this,"Request time out, please try again!",Toast.LENGTH_SHORT).show();
            refreshHandler.removeCallbacksAndMessages(null);
            CustomizedAlloys.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized_alloys);

        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
        Intent intent = getIntent();
        currentUser = intent.getParcelableExtra("user");

        addBtn = findViewById(R.id.addBtn);
        addBtn.setBtnOnClickListener(new RoundRectBtn.BtnOnClickListenr() {
            @Override
            public void BtnOnClick() {
                Intent addIntent = new Intent(CustomizedAlloys.this, CreateAlloy.class);
                addIntent.putExtra("user", currentUser);
                addIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(addIntent, 1);
            }
        });

        topBar = findViewById(R.id.CustomizedTopbar);
        topBar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                CustomizedAlloys.this.finish();
            }

            @Override
            public void rightListener() {
                refreshList();
            }
        });

        recyclerView = findViewById(R.id.customized);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomizedAdapter(this, customizedAlloys);
        adapter.setOnItemClickListener(new CustomizedAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle alloy = customizedAlloys.get(position);
                Intent detail = new Intent(CustomizedAlloys.this, detailed_alloy.class);
                detail.putExtra("alloy", alloy);
                detail.putExtra("modify", true);
                detail.putExtra("user", currentUser);
                detail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(detail, 2);
            }

            @Override
            public void onLongClick(final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        removeCustomizedAlloy(customizedAlloys.get(position).getString("Name"));
                        refreshHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        recyclerView.setAdapter(adapter);

        dialog = new LoadingDialog(this,"Loading data");
        dialog.show();
        countDown.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getCustomizedAlloys();
                refreshHandler.sendEmptyMessage(0);
                countDown.cancel();
            }
        }).start();
    }

    private void getCustomizedAlloys() {
        String path = "http://118.25.122.232/android_connect/getCustomized.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", currentUser.getPhone());
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt("errorCode");
                if(errorCode == 0) {
                    JSONArray jsonArray = jsonObject.getJSONArray("alloys");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject alloy = jsonArray.getJSONObject(i);
                        Bundle bundle = new Bundle();
                        for(Iterator<String> keys = alloy.keys(); keys.hasNext(); ) {
                            String key = keys.next();
                            String value = alloy.getString(key);
                            if(!value.equals("null") && !key.equals("Phone")) {
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
            Log.e(TAG, e.toString());
        }
    }

    private void removeCustomizedAlloy(String name) {
        String path = "http://118.25.122.232/android_connect/removeCustomized.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", currentUser.getPhone());
            formBody.add("Name", name);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                if(jsonObject.getInt("errorCode") == 0) {
                    Message message = Message.obtain();
                    message.obj = name;
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void refreshList() {
        customizedAlloys.clear();
        dialog = new LoadingDialog(this,"Loading data");
        dialog.show();
        countDown.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCustomizedAlloys();
                refreshHandler.sendEmptyMessage(0);
                countDown.cancel();
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) {
            refreshList();
        }
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
                    if(endX > startX) {
                        this.finish();
                    }
                }
        }
        return super.dispatchTouchEvent(event);
    }
}
