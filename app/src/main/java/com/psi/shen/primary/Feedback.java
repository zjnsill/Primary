package com.psi.shen.primary;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Feedback extends AppCompatActivity {

    private int startX, startY;

    private static final String TAG = "MainActivity";
    private static final int MAX_COUNT = 200;
    private TopBar topBar;
    private EditText feedbackET;
    private TextView textCount;
    private EditText fbEmailET;
    private CardView submitCV;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                Toast.makeText(Feedback.this, "Submit successfully", Toast.LENGTH_SHORT).show();
            } else if(msg.what == 0) {
                Toast.makeText(Feedback.this, "Submit failed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackET = findViewById(R.id.feedbackET);
        textCount = findViewById(R.id.textCount);
        fbEmailET = findViewById(R.id.fbEmailET);
        submitCV = findViewById(R.id.submitCV);
        topBar = findViewById(R.id.feedbackTopbar);
        topBar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                Feedback.this.finish();
            }

            @Override
            public void rightListener() {

            }
        });

        feedbackET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textCount.setText((MAX_COUNT - s.length()) + "/" + MAX_COUNT);
            }
        });
        submitCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(hasInput(feedbackET) && hasInput(fbEmailET)) {
                            if(sendFeedback(feedbackET.getText().toString(), fbEmailET.getText().toString())) {
                                Message msg = Message.obtain();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            } else {
                                Message msg = Message.obtain();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private boolean sendFeedback(String feedback, String email) {
        String path = "http://118.25.122.232/android_connect/feedback.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Feedback", feedback);
            formBody.add("Email", email);
            formBody.add("Time", new Date(System.currentTimeMillis()).toString());
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "" + response.code());
                Log.i(TAG, "" + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt("errorCode");
                if(errorCode == 0)
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean hasInput(EditText editText) {
        return !editText.getText().toString().isEmpty();
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
