package com.psi.shen.primary;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchResults extends AppCompatActivity {
    private TopBar topbar;
    public ArrayList<SingleAlloyItem> resultsArray = new ArrayList<>();
    private RecyclerView alloyResultsList;
    private String currentUser;
    private UserDatabaseManager starredListDatabaseManager;
    private long countDownTime = 10000;
    private long countDownInterval = 1;

    // JSON node names
    private static final String TAG = "MainActivity";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ALLOYS = "alloys";
    private static final String TAG_NAME = "Name";
    private static final String TAG_NAMING_STANDARD = "Naming_Standard";
    private static final String TAG_DENSITY = "Density";
    private static final String TAG_THERMAL_EXPANSION_COEFFICIENT = "Thermal_Expansion_Coefficient";
    private static final String TAG_THERMAL_CONDUCTIVITY = "Thermal_conductivity";
    private static final String TAG_SPECIFIC_HEAT = "Specific_Heat";
    private static final String TAG_RESISTIVITY = "Resistivity";
    private static final String TAG_ELASTIC_MODULUS = "Elastic_Modulus";
    private static final String TAG_POISSONS_RATIO = "Poisson\'s_Ratio";
    private static final String TAG_MELTING_RANGE_MIN = "Melting_Range_Min";
    private static final String TAG_MELTING_RANGE_MAX = "Melting_Range_Max";
    private static final String TAG_DAMPING_INDEX = "Damping_Index";
    private static final String TAG_BRINELL_HARDNESS_MIN = "Brinell_Hardness_Min";
    private static final String TAG_BRINELL_HARDNESS_MAX = "Brinell_Hardness_Max";
    private static final String TAG_FRACTURE_TOUGHNESS = "Fracture_Toughness";
    private static final String TAG_AL_MIN = "Al_Min";
    private static final String TAG_AL_MAX = "Al_Max";
    private static final String TAG_MN_MIN = "Mn_Min";
    private static final String TAG_MN_MAX = "Mn_Max";
    private static final String TAG_ZN_MIN = "Zn_Min";
    private static final String TAG_ZN_MAX = "Zn_Max";
    private static final String TAG_MG_MIN = "Mg_Min";
    private static final String TAG_MG_MAX = "Mg_Max";
    private static final String TAG_ND_MIN = "Nd_Min";
    private static final String TAG_ND_MAX = "Nd_Max";
    private static final String TAG_GD_MIN = "Gd_Min";
    private static final String TAG_GD_MAX = "Gd_Max";
    private static final String TAG_ZR_MIN = "Zr_Min";
    private static final String TAG_ZR_MAX = "Zr_Max";
    private static final String TAG_AG_MIN = "Ag_Min";
    private static final String TAG_AG_MAX = "Ag_Max";
    private static final String TAG_CU_MIN = "Cu_Min";
    private static final String TAG_CU_MAX = "Cu_Max";
    private static final String TAG_TH_MIN = "Th_Min";
    private static final String TAG_TH_MAX = "Th_Max";
    private static final String TAG_Y_MIN = "Y_Min";
    private static final String TAG_Y_MAX = "Y_Max";
    private static final String TAG_RARE_ELEMENTS_MIN = "Rare_Elements_Min";
    private static final String TAG_RARE_ELEMENTS_MAX = "Rare_Elements_Max";
    private static final String TAG_ELEMENTS = "Elements";
    private static final String TAG_FORGING = "Forging";
    private static final String TAG_WELDABILITY = "Weldability";
    private static final String TAG_MACHINING = "Machining";
    private static final String TAG_SURFACE_TREATMENT = "Surface_Treatment";
    private static final String TAG_CORROSION_RESISTANCE = "Corrosion_Resistance";

    Handler mHandler =new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            SingleAlloyItem singleAlloyItem = msg.getData().getParcelable("returnedItem");
            resultsArray.add(singleAlloyItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        alloyResultsList = findViewById(R.id.alloyResultsList);
        alloyResultsList.setLayoutManager(new LinearLayoutManager(this));
        topbar = findViewById(R.id.searchResultsTopbar);

        Intent inquery = getIntent();
        final SearchRequest request = new SearchRequest(inquery.getBundleExtra("inquiry"));
        this.currentUser=inquery.getStringExtra("user");
        starredListDatabaseManager = UserDatabaseManager.getInstance(this,currentUser);
        final SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(this,resultsArray,currentUser);
        searchResultsAdapter.setmOnItemClickListener(new SearchResultsAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position){ItemAction(position);}
            @Override
            public void onLongClick(int position,boolean isStarred){
                if(isStarred){
                    starredListDatabaseManager.deleteStarredItem(resultsArray.get(position).getAlloyName());
                    Toast.makeText(SearchResults.this,"Item unstarred!",Toast.LENGTH_SHORT).show();
                }
                else {
                    StarredListItem starredListItem = new StarredListItem(resultsArray.get(position), true, false, false, false);
                    starredListDatabaseManager.addStarredItem(starredListItem);
                    Toast.makeText(SearchResults.this,"Item starred!",Toast.LENGTH_SHORT).show();
                }
                searchResultsAdapter.notifyItemChanged(position);
            }
        });
        alloyResultsList.setAdapter(searchResultsAdapter);
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
        final LoadingDialog dialog = new LoadingDialog(this,"Loading data");
        dialog.show();

        final Handler refreshHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                dialog.close();
                searchResultsAdapter.notifyDataSetChanged();
            }
        };
        //count down time and close this interface;
        final CountDownTimer countDown = new CountDownTimer(countDownTime,countDownInterval) {
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
        countDown.start();





        new Thread(new Runnable() {
            @Override
            public void run() {
                getSingleAlloyItems(request);
                refreshHandler.sendEmptyMessage(0);
                countDown.cancel();
            }
        }).start();
    }

    private void ItemAction(int position) {
        SingleAlloyItem alloyItem = resultsArray.get(position);
        Intent jumpToDetail = new Intent(SearchResults.this, detaied_alloy.class);
        Bundle item = new Bundle();
        item.putParcelable("clickedItem",alloyItem);
        jumpToDetail.putExtras(item);
        jumpToDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(jumpToDetail);
    }

    private void getSingleAlloyItems(final SearchRequest searchRequest) {
        JSONObject alloys = getAlloy(searchRequest);
        transferToAlloyItems(alloys);
    }

    private JSONObject getAlloy(final SearchRequest searchRequest) {
        String path = "http://118.25.122.232/android_connect/query.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = makeFormBody(searchRequest);
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

    private FormBody.Builder makeFormBody(final SearchRequest request) {
        FormBody.Builder formBody = new FormBody.Builder();
        String name = request.getName();
        String namingStandard = request.getNamingStandard();
        double[] doubleDatas = request.getDoubleArray();
        boolean[] validation = request.getValidation();

        if(validation[0])
            formBody.add("Name", name);
        if(validation[1])
            formBody.add("Naming_Standard", namingStandard);
        if(validation[2])
            formBody.add("Density_Min", doubleDatas[0] + "");
        if(validation[3])
            formBody.add("Density_Max", doubleDatas[1] + "");
        if(validation[4])
            formBody.add("Thermal_Expansion_Coefficient_Min", doubleDatas[2] + "");
        if(validation[5])
            formBody.add("Thermal_Expansion_Coefficient_Max", doubleDatas[3] + "");
        if(validation[6])
            formBody.add("Thermal_conductivity_Min", doubleDatas[4] + "");
        if(validation[7])
            formBody.add("Thermal_conductivity_Max", doubleDatas[5] + "");
        if(validation[8])
            formBody.add("Specific_Heat_Min", doubleDatas[6] + "");
        if(validation[9])
            formBody.add("Specific_Heat_Max", doubleDatas[7] + "");
        if(validation[10])
            formBody.add("Resistivity_Min", doubleDatas[8] + "");
        if(validation[11])
            formBody.add("Resistivity_Max", doubleDatas[9] + "");
        if(validation[12])
            formBody.add("Elastic_Modulus_Min", doubleDatas[10] + "");
        if(validation[13])
            formBody.add("Elastic_Modulus_Max", doubleDatas[11] + "");
        if(validation[14])
            formBody.add("Poissons_Ratio_Min", doubleDatas[12] + "");
        if(validation[15])
            formBody.add("Poissons_Ratio_Max", doubleDatas[13] + "");
        if(validation[16])
            formBody.add("Damping_Index_Min", doubleDatas[14] + "");
        if(validation[17])
            formBody.add("Damping_Index_Max", doubleDatas[15] + "");
        if(validation[18])
            formBody.add("Toughness_Min", doubleDatas[16] + "");
        if(validation[19])
            formBody.add("Toughness_Max", doubleDatas[17] + "");
        if(validation[20])
            formBody.add("Melting_Range_Min", doubleDatas[18] + "");
        if(validation[21])
            formBody.add("Melting_Range_Max", doubleDatas[19] + "");
        if(validation[22])
            formBody.add("Hardness_Min", doubleDatas[20] + "");
        if(validation[23])
            formBody.add("Hardness_Max", doubleDatas[21] + "");
        if(validation[24])
            formBody.add("Al_Min", doubleDatas[22] + "");
        if(validation[25])
            formBody.add("Al_Max", doubleDatas[23] + "");
        if(validation[26])
            formBody.add("Mn_Min", doubleDatas[24] + "");
        if(validation[27])
            formBody.add("Mn_Max", doubleDatas[25] + "");
        if(validation[28])
            formBody.add("Zn_Min", doubleDatas[26] + "");
        if(validation[29])
            formBody.add("Zn_Max", doubleDatas[27] + "");
        if(validation[30])
            formBody.add("Mg_Min", doubleDatas[28] + "");
        if(validation[31])
            formBody.add("Mg_Max", doubleDatas[29] + "");
        if(validation[32])
            formBody.add("Nd_Min", doubleDatas[30] + "");
        if(validation[33])
            formBody.add("Nd_Max", doubleDatas[31] + "");
        if(validation[34])
            formBody.add("Gd_Min", doubleDatas[32] + "");
        if(validation[35])
            formBody.add("Gd_Max", doubleDatas[33] + "");
        if(validation[36])
            formBody.add("Zr_Min", doubleDatas[34] + "");
        if(validation[37])
            formBody.add("Zr_Max", doubleDatas[35] + "");
        if(validation[38])
            formBody.add("Ag_Min", doubleDatas[36] + "");
        if(validation[39])
            formBody.add("Ag_Max", doubleDatas[37] + "");
        if(validation[40])
            formBody.add("Cu_Min", doubleDatas[38] + "");
        if(validation[41])
            formBody.add("Cu_Max", doubleDatas[39] + "");
        if(validation[42])
            formBody.add("Th_Min", doubleDatas[40] + "");
        if(validation[43])
            formBody.add("Th_Max", doubleDatas[41] + "");
        if(validation[44])
            formBody.add("Y_Min", doubleDatas[42] + "");
        if(validation[45])
            formBody.add("Y_Max", doubleDatas[43] + "");
        if(validation[46])
            formBody.add("Rare_Elements_Min", doubleDatas[44] + "");
        if(validation[47])
            formBody.add("Rare_Elements_Max", doubleDatas[45] + "");

        String elements = "";
        boolean flag = false;
        if(validation[48]) {
            elements += "Al";
            flag = true;
        }
        if(validation[49]) {
            if(flag)
                elements += ",";
            elements += "Mn";
            flag = true;
        }
        if(validation[50]) {
            if(flag)
                elements += ",";
            elements += "Zn";
            flag = true;
        }
        if(validation[51]) {
            if(flag)
                elements += ",";
            elements += "Mg";
            flag = true;
        }
        if(validation[52]) {
            if(flag)
                elements += ",";
            elements += "Nd";
            flag = true;
        }
        if(validation[53]) {
            if(flag)
                elements += ",";
            elements += "Gd";
            flag = true;
        }
        if(validation[54]) {
            if(flag)
                elements += ",";
            elements += "Zr";
            flag = true;
        }
        if(validation[55]) {
            if(flag)
                elements += ",";
            elements += "Ag";
            flag = true;
        }
        if(validation[56]) {
            if(flag)
                elements += ",";
            elements += "Cu";
            flag = true;
        }
        if(validation[57]) {
            if(flag)
                elements += ",";
            elements += "Th";
            flag = true;
        }
        if(validation[58]) {
            if(flag)
                elements += ",";
            elements += "Y";
            flag = true;
        }
        if(validation[59]) {
            if(flag)
                elements += ",";
            elements += "Rare_Elements";
            flag = true;
        }
        if(flag)
            formBody.add("Elements", elements);
        return formBody;
    }

    private void transferToAlloyItems(JSONObject alloys) {
        try {
            if(alloys.getInt(TAG_SUCCESS) == 1) {
                JSONArray jsonArray = alloys.getJSONArray(TAG_ALLOYS);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject singleAlloy = jsonArray.getJSONObject(i);
                    String[] returnDatas = new String[45];
                    returnDatas[0] = singleAlloy.getString(TAG_NAME);
                    returnDatas[1] = singleAlloy.getString(TAG_NAMING_STANDARD);
                    returnDatas[2] = singleAlloy.getString(TAG_ELEMENTS);
                    returnDatas[3] = singleAlloy.getString(TAG_DENSITY);
                    returnDatas[4] = singleAlloy.getString(TAG_THERMAL_CONDUCTIVITY);
                    returnDatas[5] = singleAlloy.getString(TAG_THERMAL_EXPANSION_COEFFICIENT);
                    returnDatas[6] = singleAlloy.getString(TAG_SPECIFIC_HEAT);
                    returnDatas[7] = singleAlloy.getString(TAG_RESISTIVITY);
                    returnDatas[8] = singleAlloy.getString(TAG_ELASTIC_MODULUS);
                    returnDatas[9] = singleAlloy.getString(TAG_POISSONS_RATIO);
                    returnDatas[10] = singleAlloy.getString(TAG_MELTING_RANGE_MIN);
                    returnDatas[11] = singleAlloy.getString(TAG_MELTING_RANGE_MAX);
                    returnDatas[12] = singleAlloy.getString(TAG_DAMPING_INDEX);
                    returnDatas[13] = singleAlloy.getString(TAG_BRINELL_HARDNESS_MIN);
                    returnDatas[14] = singleAlloy.getString(TAG_BRINELL_HARDNESS_MAX);
                    returnDatas[15] = singleAlloy.getString(TAG_FORGING); // forging
                    returnDatas[16] = singleAlloy.getString(TAG_WELDABILITY); // weldability
                    returnDatas[17] = singleAlloy.getString(TAG_MACHINING); // machining
                    returnDatas[18] = singleAlloy.getString(TAG_SURFACE_TREATMENT); //surface_treatment
                    returnDatas[19] = singleAlloy.getString(TAG_CORROSION_RESISTANCE); // corrision_resistance
                    returnDatas[20] = singleAlloy.getString(TAG_FRACTURE_TOUGHNESS);
                    returnDatas[21] = singleAlloy.getString(TAG_AL_MIN);
                    returnDatas[22] = singleAlloy.getString(TAG_AL_MAX);
                    returnDatas[23] = singleAlloy.getString(TAG_MN_MIN);
                    returnDatas[24] = singleAlloy.getString(TAG_MN_MAX);
                    returnDatas[25] = singleAlloy.getString(TAG_ZN_MIN);
                    returnDatas[26] = singleAlloy.getString(TAG_ZN_MAX);
                    returnDatas[27] = singleAlloy.getString(TAG_MG_MIN);
                    returnDatas[28] = singleAlloy.getString(TAG_MG_MAX);
                    returnDatas[29] = singleAlloy.getString(TAG_ND_MIN);
                    returnDatas[30] = singleAlloy.getString(TAG_ND_MAX);
                    returnDatas[31] = singleAlloy.getString(TAG_GD_MIN);
                    returnDatas[32] = singleAlloy.getString(TAG_GD_MAX);
                    returnDatas[33] = singleAlloy.getString(TAG_ZR_MIN);
                    returnDatas[34] = singleAlloy.getString(TAG_ZR_MAX);
                    returnDatas[35] = singleAlloy.getString(TAG_AG_MIN);
                    returnDatas[36] = singleAlloy.getString(TAG_AG_MAX);
                    returnDatas[37] = singleAlloy.getString(TAG_CU_MIN);
                    returnDatas[38] = singleAlloy.getString(TAG_CU_MAX);
                    returnDatas[39] = singleAlloy.getString(TAG_TH_MIN);
                    returnDatas[40] = singleAlloy.getString(TAG_TH_MAX);
                    returnDatas[41] = singleAlloy.getString(TAG_Y_MIN);
                    returnDatas[42] = singleAlloy.getString(TAG_Y_MAX);
                    returnDatas[43] = singleAlloy.getString(TAG_RARE_ELEMENTS_MIN);
                    returnDatas[44] = singleAlloy.getString(TAG_RARE_ELEMENTS_MAX);

                    SingleAlloyItem singleAlloyItem = new SingleAlloyItem(returnDatas);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("returnedItem", singleAlloyItem);
                    Message msg = Message.obtain();
                    msg.setData(bundle);
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);

    }
}
