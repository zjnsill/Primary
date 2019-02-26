package com.psi.shen.primary;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchResults extends AppCompatActivity {
    private TopBar topbar;
    private signedUser currentUser;

    private long countDownTime = 10000;
    private long countDownInterval = 1;

    private Bundle inquiry = new Bundle();
    public ArrayList<Bundle> resultsArray = new ArrayList<>();
    private ArrayList<String> starredAlloys = new ArrayList<>();

    // JSON node names
    private static final String TAG = "MainActivity";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ALLOYS = "alloys";

    private RecyclerView alloyResultsList;
    private SearchResultsAdapter searchResultsAdapter;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                Bundle bundle = msg.getData();
                resultsArray.add(bundle);
            } else if(msg.what == 0) {
                String starredAlloy = (String) msg.obj;
                starredAlloys.add(starredAlloy);
            } else if(msg.what == 2) {
                String starredAlloystr = (String)msg.obj;
                starredAlloys.clear();
                String[] temp = starredAlloystr.split(",");
                for(int i = 0; i < temp.length; i++) {
                    starredAlloys.add(temp[i]);
                }
            }
        }
    };

    private LoadingDialog dialog;

    private final Handler refreshHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
                dialog.close();
            searchResultsAdapter.notifyDataSetChanged();
            if(msg.what == 1)
                Toast.makeText(SearchResults.this,"Alloy starred!",Toast.LENGTH_SHORT).show();
            else if(msg.what == 2)
                Toast.makeText(SearchResults.this,"Alloy unstarred!",Toast.LENGTH_SHORT).show();
        }
    };

    private final CountDownTimer countDown = new CountDownTimer(countDownTime, countDownInterval) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            dialog.close();
            Toast.makeText(SearchResults.this,"Request time out, please try again!",Toast.LENGTH_SHORT).show();
            refreshHandler.removeCallbacksAndMessages(null);
            SearchResults.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        inquiry = intent.getBundleExtra("inquiry");
        currentUser = intent.getParcelableExtra("user");

        alloyResultsList = findViewById(R.id.alloyResultsList);
        alloyResultsList.setLayoutManager(new LinearLayoutManager(this));

        searchResultsAdapter = new SearchResultsAdapter(this, resultsArray, starredAlloys, currentUser);
        searchResultsAdapter.setmOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                ItemAction(position);
            }
            @Override
            public void onLongClick(final int position, boolean isStarred) {
                if(isStarred) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            removeStarredItem(resultsArray.get(position).getString("Name"));
                            refreshHandler.sendEmptyMessage(2);
                        }
                    }).start();
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            addStarredItem(resultsArray.get(position).getString("Name"));
                            refreshHandler.sendEmptyMessage(1);
                        }
                    }).start();
                }
            }
        });
        alloyResultsList.setAdapter(searchResultsAdapter);

        topbar = findViewById(R.id.searchResultsTopbar);
        topbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                SearchResults.this.finish();
            }

            @Override
            public void rightListener() {
                Intent sortIntent = new Intent(SearchResults.this, SortAlloys.class);
                sortIntent.putExtra("resultsArray", resultsArray);
                sortIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(sortIntent);
            }
        });

        dialog = new LoadingDialog(this,"Loading data");
        dialog.show();

        countDown.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getSingleAlloyItems(inquiry);
                refreshHandler.sendEmptyMessage(0);
                countDown.cancel();
            }
        }).start();
    }

    private void ItemAction(int position) {
        Bundle alloyItem = resultsArray.get(position);
        Intent jumpToDetail = new Intent(SearchResults.this, detailed_alloy.class);
        jumpToDetail.putExtra("alloy", alloyItem);
        jumpToDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(jumpToDetail);
    }

    private void getSingleAlloyItems(final Bundle inquiry) {
        JSONObject alloys = getAlloy(inquiry);
        transferToAlloyItems(alloys);
    }

    private JSONObject getAlloy(final Bundle inquiry) {
        String path = "http://118.25.122.232/android_connect/new_query.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = makeFormBody(inquiry);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "" + response.code());
                Log.i(TAG, "" + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                return  jsonObject;
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FormBody.Builder makeFormBody(final Bundle inquiry) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("Phone", currentUser.getPhone());
        if(inquiry.size() != 0) {
            for(String key : inquiry.keySet()) {
                Bundle bundle = inquiry.getBundle(key);
                if(key.equals("Name")) {
                    formBody.add("Name", bundle.getString("Name"));
                } else if(key.equals("Alloy Composition")) {
                    if(bundle.containsKey("Min")) {
                        formBody.add(key + "_Min", bundle.getString("Min"));
                    }
                    if(bundle.containsKey("Max")) {
                        formBody.add(key + "_Max", bundle.getString("Max"));
                    }
                } else {
                    if(bundle.containsKey("Min")) {
                        formBody.add(key + "-Min", bundle.getString("Min"));
                    }
                    if(bundle.containsKey("Max")) {
                        formBody.add(key + "-Max", bundle.getString("Max"));
                    }
                }
            }
        }
        return formBody;
    }

    private void transferToAlloyItems(JSONObject alloys) {
        try {
            if(alloys.getInt(TAG_SUCCESS) == 1) {
                JSONArray jsonArray = alloys.getJSONArray(TAG_ALLOYS);
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
                    message.what = 1;
                    mHandler.sendMessage(message);
                }

                jsonArray = alloys.getJSONArray("starredItems");
                for(int i = 0; i < jsonArray.length(); i++) {
                    String starredAlloy = jsonArray.get(i).toString();
                    Message message = Message.obtain();
                    message.obj = starredAlloy;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addStarredItem(String name) {
        String path = "http://118.25.122.232/android_connect/addStarredItem.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", currentUser.getPhone());
            formBody.add("NewStarredItem", name);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "" + response.code());
                Log.i(TAG, "" + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                if(jsonObject.getInt("errorCode") == 0) {
                    Message message = Message.obtain();
                    message.obj = jsonObject.getString("starredItems");
                    message.what = 2;
                    mHandler.sendMessage(message);
                }
            } else {
                Log.e(TAG, "error");
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
                    message.obj = jsonObject.getString("starredItems");
                    message.what = 2;
                    mHandler.sendMessage(message);
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
