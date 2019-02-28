package com.psi.shen.primary;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

    private int startX, startY;

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

    private String[] sortItems = {"Name",
            "Elastic (Young\'s, Tensile) Modulus", "Elongation at Break", "Fatigue Strength", "Poisson\'s Ratio", "Shear Modulus", "Shear Strength", "Tensile Strength: Ultimate (UTS)", "Tensile Strength: Yield (Proof)", "Brinell Hardness", "Compressive (Crushing) Strength", "Rockwell F Hardness", "Impact Strength: V-Notched Charpy", "Fracture Toughness",
            "Latent Heat of Fusion", "Maximum Temperature: Mechanical", "Melting Completion (Liquidus)", "Melting Onset (Solidus)", "Solidification (Pattern Maker\'s) Shrinkage", "Specific Heat Capacity", "Thermal Conductivity", "Thermal Expansion", "Brazing Temperature",
            "Electrical Conductivity: Equal Volume", "Electrical Conductivity: Equal Weight (Specific)",
            "Base Metal Price", "Density", "Embodied Carbon", "Embodied Energy", "Embodied Water",
            "Resilience: Ultimate (Unit Rupture Work)", "Resilience: Unit (Modulus of Resilience)", "Stiffness to Weight: Axial", "Stiffness to Weight: Bending", "Strength to Weight: Axial", "Strength to Weight: Bending", "Thermal Diffusivity", "Thermal Shock Resistance"};
            //"Mg", "Al", "Mn", "Si", "Zn", "Cu", "Ni", "Y", "Zr", "Li", "Fe", "Be", "Ca", "Ag", "Rare Elements", "Residuals"};
    private int[] sortIds;

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

        sortIds = new int[sortItems.length];

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
                showSortDialog();
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

    private void showSortDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        View view = View.inflate(this, R.layout.sort_select_dialog, null);
        final RadioGroup sortRG;
        sortRG = view.findViewById(R.id.SortRG);

        int tag = 1000;
        for(int i = 0; i < sortItems.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setTextSize(15);
            radioButton.setText(sortItems[i]);
            radioButton.setId(tag);
            sortIds[i] = tag++;
            sortRG.addView(radioButton);
        }

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int)(ScreenSizeUtils.getInstance(this).getScreenWidth());
        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = (int)(ScreenSizeUtils.getInstance(this).getScreenHeight() * 2 / 3);
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        dialog.show();

        sortRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                Log.i(TAG, String.valueOf(id));
                dialog.dismiss();
                dialog.cancel();

                sortAlloys(sortItems[id - 1000]);
            }
        });
    }

    private void sortAlloys(String sortBy) {
        for(int i = 0; i < resultsArray.size(); i++) {
            for(int j = i + 1; j < resultsArray.size(); j++) {
                if(sortBy.equals("Name")) {
                    if (resultsArray.get(i).getString(sortBy).compareTo(resultsArray.get(j).getString(sortBy)) > 0) {
                        Bundle bundle = resultsArray.get(i);
                        resultsArray.set(i, resultsArray.get(j));
                        resultsArray.set(j, bundle);
                    }
                } else {
                    if(!resultsArray.get(i).containsKey(sortBy) && !resultsArray.get(j).containsKey(sortBy)) {
                        if (resultsArray.get(i).getString("Name").compareTo(resultsArray.get(j).getString("Name")) < 0) {
                            Bundle bundle = resultsArray.get(i);
                            resultsArray.set(i, resultsArray.get(j));
                            resultsArray.set(j, bundle);
                        }
                    } else if(!resultsArray.get(i).containsKey(sortBy)) {
                        Bundle bundle = resultsArray.get(i);
                        resultsArray.set(i, resultsArray.get(j));
                        resultsArray.set(j, bundle);
                    } else if(resultsArray.get(i).containsKey(sortBy) && resultsArray.get(j).containsKey(sortBy)) {
                        if(Double.parseDouble(resultsArray.get(i).getString(sortBy)) < Double.parseDouble(resultsArray.get(j).getString(sortBy))) {
                            Bundle bundle = resultsArray.get(i);
                            resultsArray.set(i, resultsArray.get(j));
                            resultsArray.set(j, bundle);
                        }
                    }
                }
            }
        }
        searchResultsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
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
