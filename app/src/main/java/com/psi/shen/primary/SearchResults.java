package com.psi.shen.primary;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {
    private TopBar topbar;
    public ArrayList<SingleAlloyItem> resultsArray = new ArrayList<>();
    private RecyclerView alloyResultsList;
    private String currentUser;
    private UserDatabaseManager starredListDatabaseManager;

    // JSON node names
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
        final Request request = new Request(inquery.getBundleExtra("inquiry"));
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
            public void rightListener() {}
        });
        final LoadingDialog dialog = new LoadingDialog(this,"Loading data");
        dialog.show();
        final Handler refreshHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                dialog.close();
                searchResultsAdapter.notifyDataSetChanged();
            }
        };//methods in onDestroy can avoid memeory leak issue

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SingleAlloyItem> singleAlloyItems = getSingleAlloyItems(request);
            }
        }).start();
        /* 因为之前的代码里用到了bundle，但我不知道怎么用，也不知道怎么传递message，
         * 所以下面这段以前的代码就只是注释掉没有删掉
         */
        /*
        new Thread(new Runnable() {
            volatile boolean flag = false;
            @Override
            public void run() {
                //Looper.prepare();
                while (!flag) {
                    try {
                        Class.forName(DRIVER);
                        cn = DriverManager.getConnection(URL, USERNAME, PASSWOED);
                        if (cn != null)
                            Log.i("Mainactivity", "ok");
                        //request 为传入类 Density改specific gravity
                        produceSQLSentence(request);
                        //sql 语句
                        st = cn.createStatement();
                        rs = st.executeQuery(SQLSentence);
                        resultsArray.clear();
                        while (rs.next()) {
                            String returnDatas[] = new String[21];
                            returnDatas[0] = rs.getString("Name");
                            returnDatas[1] = rs.getString("Naming_Standard");
                            returnDatas[2] = rs.getString("Elements");
                            returnDatas[3] = rs.getString("Density");
                            returnDatas[4] = rs.getString("Thermal_Expansion_Coefficient");
                            returnDatas[5] = rs.getString("Thermal_conductivity");
                            returnDatas[6] = rs.getString("Specific_Heat");
                            returnDatas[7] = rs.getString("Resistivity");
                            returnDatas[8] = rs.getString("Elastic_Modulus");
                            returnDatas[9] = rs.getString("Poisson's_Ratio");
                            returnDatas[10] = rs.getString("Melting_Range_Min");
                            returnDatas[11] = rs.getString("Melting_Range_Max");
                            returnDatas[12] = rs.getString("Damping_Index");
                            returnDatas[13] = rs.getString("Brinell_Hardness_Min");
                            returnDatas[14] = rs.getString("Brinell_Hardness_Max");
                            returnDatas[15] = rs.getString("Forging");
                            returnDatas[16] = rs.getString("Weldability");
                            returnDatas[17] = rs.getString("Machining");
                            returnDatas[18] = rs.getString("Surface_Treatment");
                            returnDatas[19] = rs.getString("Corrosion_Resistance");
                            returnDatas[20] = rs.getString("Fracture_Toughness");

                            SingleAlloyItem singleAlloyItem2 = new SingleAlloyItem(returnDatas);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("returnedItem",singleAlloyItem2);
                            Message msg = Message.obtain();
                            msg.setData(bundle);
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                            //输出搜索到到合金数据
                            //arrayList.add(singleAlloyItem);
                        }
                        flag = true;
                        refreshHandler.sendEmptyMessage(0);
                        //singleAlloyItem 为传出类 specific gravity改Density
                        if (cn != null)
                            cn.close();
                        if (st != null)
                            st.close();
                        if (rs != null)
                            rs.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
        //prepare data and inform recyclerView when data is prepared

    }

    private void ItemAction(int position) {
        SingleAlloyItem alloyItem = resultsArray.get(position);
        Intent jumpToDetail = new Intent(SearchResults.this, detaied_alloy.class);
        Bundle item = new Bundle();
        item.putParcelable("clickedItem",alloyItem);
        jumpToDetail.putExtras(item);
        startActivity(jumpToDetail);
    }

    private List<SingleAlloyItem> getSingleAlloyItems(final Request request) {
        List<SingleAlloyItem> singleAlloyItems = null;
        List<String> names = getNames(request);

        if(names != null) {
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                JSONObject attributes = getAttributes(name);
                JSONObject components = getComponents(name);
                SingleAlloyItem singleAlloyItem = transferToAlloyItem(attributes, components);
                singleAlloyItems.add(singleAlloyItem);
            }
            return singleAlloyItems;
        }
        return null;
    }

    private List<String> getNames(final Request request) {
        String path="http://118.25.122.232/android_connect/find.php";
        List<NameValuePair> params = paramsList(request);
        InputStream is = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            JSONObject jsonObject = parseInfo(is);
            if(jsonObject.getInt(TAG_SUCCESS) == 1) {
                JSONArray alloys = jsonObject.getJSONArray(TAG_ALLOYS);
                List<String> nameList = null;
                for(int i = 0; i < alloys.length(); i++) {
                    JSONObject c = alloys.getJSONObject(i);
                    String name = c.getString(TAG_NAME);
                    nameList.add(name);
                }
                return nameList;
            }
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    private JSONObject getAttributes(String name) {
        String path="http://118.25.122.232/android_connect/attributes.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Name", name));
        InputStream is = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            return parseInfo(is);
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    private JSONObject getComponents(String name) {
        String path="http://118.25.122.232/android_connect/components.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Name", name));
        InputStream is = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            return parseInfo(is);
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    private List<NameValuePair> paramsList(final Request request) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String name = request.getName();
        String namingStandard = request.getNamingStandard();
        double[] doubleDatas = request.getDoubleArray();
        boolean[] validation = request.getValidation();

        if(validation[0])
            params.add(new BasicNameValuePair("Name", name));
        if(validation[1])
            params.add(new BasicNameValuePair("Naming_Standard", namingStandard));
        if(validation[2])
            params.add(new BasicNameValuePair("Density_Min", doubleDatas[0] + ""));
        if(validation[3])
            params.add(new BasicNameValuePair("Density_Max", doubleDatas[1] + ""));
        if(validation[4])
            params.add(new BasicNameValuePair("Thermal_Expansion_Coefficient_Min", doubleDatas[2] + ""));
        if(validation[5])
            params.add(new BasicNameValuePair("Thermal_Expansion_Coefficient_Max", doubleDatas[3] + ""));
        if(validation[6])
            params.add(new BasicNameValuePair("Thermal_conductivity_Min", doubleDatas[4] + ""));
        if(validation[7])
            params.add(new BasicNameValuePair("Thermal_conductivity_Max", doubleDatas[5] + ""));
        if(validation[8])
            params.add(new BasicNameValuePair("Specific_Heat_Min", doubleDatas[6] + ""));
        if(validation[9])
            params.add(new BasicNameValuePair("Specific_Heat_Max", doubleDatas[7] + ""));
        if(validation[10])
            params.add(new BasicNameValuePair("Resistivity_Min", doubleDatas[8] + ""));
        if(validation[11])
            params.add(new BasicNameValuePair("Resistivity_Max", doubleDatas[9] + ""));
        if(validation[12])
            params.add(new BasicNameValuePair("Elastic_Modulus_Min", doubleDatas[10] + ""));
        if(validation[13])
            params.add(new BasicNameValuePair("Elastic_Modulus_Max", doubleDatas[11] + ""));
        if(validation[14])
            params.add(new BasicNameValuePair("Poissons_Ratio_Min", doubleDatas[12] + ""));
        if(validation[15])
            params.add(new BasicNameValuePair("Poissons_Ratio_Max", doubleDatas[13] + ""));
        if(validation[16])
            params.add(new BasicNameValuePair("Damping_Index_Min", doubleDatas[14] + ""));
        if(validation[17])
            params.add(new BasicNameValuePair("Damping_Index_Max", doubleDatas[15] + ""));
        if(validation[18])
            params.add(new BasicNameValuePair("Toughness_Min", doubleDatas[16] + ""));
        if(validation[19])
            params.add(new BasicNameValuePair("Toughness_Max", doubleDatas[17] + ""));
        if(validation[20])
            params.add(new BasicNameValuePair("Melting_Range_Min", doubleDatas[18] + ""));
        if(validation[21])
            params.add(new BasicNameValuePair("Melting_Range_Max", doubleDatas[19] + ""));
        if(validation[22])
            params.add(new BasicNameValuePair("Hardness_Min", doubleDatas[20] + ""));
        if(validation[23])
            params.add(new BasicNameValuePair("Hardness_Max", doubleDatas[21] + ""));
        if(validation[24])
            params.add(new BasicNameValuePair("Al_Min", doubleDatas[22] + ""));
        if(validation[25])
            params.add(new BasicNameValuePair("Al_Max", doubleDatas[23] + ""));
        if(validation[26])
            params.add(new BasicNameValuePair("Mn_Min", doubleDatas[24] + ""));
        if(validation[27])
            params.add(new BasicNameValuePair("Mn_Max", doubleDatas[25] + ""));
        if(validation[28])
            params.add(new BasicNameValuePair("Zn_Min", doubleDatas[26] + ""));
        if(validation[29])
            params.add(new BasicNameValuePair("Zn_Max", doubleDatas[27] + ""));
        if(validation[30])
            params.add(new BasicNameValuePair("Mg_Min", doubleDatas[28] + ""));
        if(validation[31])
            params.add(new BasicNameValuePair("Mg_Max", doubleDatas[29] + ""));
        if(validation[32])
            params.add(new BasicNameValuePair("Nd_Min", doubleDatas[30] + ""));
        if(validation[33])
            params.add(new BasicNameValuePair("Nd_Max", doubleDatas[31] + ""));
        if(validation[34])
            params.add(new BasicNameValuePair("Gd_Min", doubleDatas[32] + ""));
        if(validation[35])
            params.add(new BasicNameValuePair("Gd_Max", doubleDatas[33] + ""));
        if(validation[36])
            params.add(new BasicNameValuePair("Zr_Min", doubleDatas[34] + ""));
        if(validation[37])
            params.add(new BasicNameValuePair("Zr_Max", doubleDatas[35] + ""));
        if(validation[38])
            params.add(new BasicNameValuePair("Ag_Min", doubleDatas[36] + ""));
        if(validation[39])
            params.add(new BasicNameValuePair("Ag_Max", doubleDatas[37] + ""));
        if(validation[40])
            params.add(new BasicNameValuePair("Cu_Min", doubleDatas[38] + ""));
        if(validation[41])
            params.add(new BasicNameValuePair("Cu_Max", doubleDatas[39] + ""));
        if(validation[42])
            params.add(new BasicNameValuePair("Th_Min", doubleDatas[40] + ""));
        if(validation[43])
            params.add(new BasicNameValuePair("Th_Max", doubleDatas[41] + ""));
        if(validation[44])
            params.add(new BasicNameValuePair("Y_Min", doubleDatas[42] + ""));
        if(validation[45])
            params.add(new BasicNameValuePair("Y_Max", doubleDatas[43] + ""));
        if(validation[46])
            params.add(new BasicNameValuePair("Rare_Elements_Min", doubleDatas[44] + ""));
        if(validation[47])
            params.add(new BasicNameValuePair("Rare_Elements_Max", doubleDatas[45] + ""));

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
            params.add(new BasicNameValuePair("Elements", elements));
        return params;
    }

    private JSONObject parseInfo(InputStream in) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb=new StringBuilder();
        String line=null;
        String json = null;
        JSONObject jsonObject = null;
        while ((line=br.readLine())!=null){
            sb.append(line+"\n");
        }
        json = sb.toString();
        //Log.i(TAG, "parseInfo: sb:"+json);
        try {
            jsonObject = new JSONObject(json);
        } catch(JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jsonObject;
    }

    private SingleAlloyItem transferToAlloyItem(JSONObject attributes, JSONObject components) {
        try {
            if(attributes.getInt(TAG_SUCCESS) == 1 && components.getInt(TAG_SUCCESS) == 1 && attributes.getString(TAG_NAME) == components.getString(TAG_NAME)) {
                JSONObject attribute = attributes.getJSONArray(TAG_ALLOYS).getJSONObject(0);
                JSONObject component = components.getJSONArray(TAG_ALLOYS).getJSONObject(0);
                String[] returnDatas = new String[45];
                returnDatas[0] = attribute.getString(TAG_NAME);
                returnDatas[1] = attribute.getString(TAG_NAMING_STANDARD);
                returnDatas[2] = component.getString(TAG_ELEMENTS);
                returnDatas[3] = attribute.getString(TAG_DENSITY);
                returnDatas[4] = attribute.getString(TAG_THERMAL_CONDUCTIVITY);
                returnDatas[5] = attribute.getString(TAG_THERMAL_EXPANSION_COEFFICIENT);
                returnDatas[6] = attribute.getString(TAG_SPECIFIC_HEAT);
                returnDatas[7] = attribute.getString(TAG_RESISTIVITY);
                returnDatas[8] = attribute.getString(TAG_ELASTIC_MODULUS);
                returnDatas[9] = attribute.getString(TAG_POISSONS_RATIO);
                returnDatas[10] = attribute.getString(TAG_MELTING_RANGE_MIN);
                returnDatas[11] = attribute.getString(TAG_MELTING_RANGE_MAX);
                returnDatas[12] = attribute.getString(TAG_DAMPING_INDEX);
                returnDatas[13] = attribute.getString(TAG_BRINELL_HARDNESS_MIN);
                returnDatas[14] = attribute.getString(TAG_BRINELL_HARDNESS_MAX);
                returnDatas[15] = ""; // forging
                returnDatas[16] = ""; // weldability
                returnDatas[17] = ""; // machining
                returnDatas[18] = ""; //surface_treatment
                returnDatas[19] = ""; // corrision_resistance
                returnDatas[20] = attribute.getString(TAG_FRACTURE_TOUGHNESS);
                returnDatas[21] = component.getString(TAG_AL_MIN);
                returnDatas[22] = component.getString(TAG_AL_MAX);
                returnDatas[23] = component.getString(TAG_MN_MIN);
                returnDatas[24] = component.getString(TAG_MN_MAX);
                returnDatas[25] = component.getString(TAG_ZN_MIN);
                returnDatas[26] = component.getString(TAG_ZN_MAX);
                returnDatas[27] = component.getString(TAG_MG_MIN);
                returnDatas[28] = component.getString(TAG_MG_MAX);
                returnDatas[29] = component.getString(TAG_ND_MIN);
                returnDatas[30] = component.getString(TAG_ND_MAX);
                returnDatas[31] = component.getString(TAG_GD_MIN);
                returnDatas[32] = component.getString(TAG_GD_MAX);
                returnDatas[33] = component.getString(TAG_ZR_MIN);
                returnDatas[34] = component.getString(TAG_ZR_MAX);
                returnDatas[35] = component.getString(TAG_AG_MIN);
                returnDatas[36] = component.getString(TAG_AG_MAX);
                returnDatas[37] = component.getString(TAG_CU_MIN);
                returnDatas[38] = component.getString(TAG_CU_MAX);
                returnDatas[39] = component.getString(TAG_TH_MIN);
                returnDatas[40] = component.getString(TAG_TH_MAX);
                returnDatas[41] = component.getString(TAG_Y_MIN);
                returnDatas[42] = component.getString(TAG_Y_MAX);
                returnDatas[43] = component.getString(TAG_RARE_ELEMENTS_MIN);
                returnDatas[44] = component.getString(TAG_RARE_ELEMENTS_MAX);

                SingleAlloyItem singleAlloyItem = new SingleAlloyItem(returnDatas);
                return singleAlloyItem;
            }
        } catch (JSONException e) {
            e.printStackTrace();;
        }
        return null;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
