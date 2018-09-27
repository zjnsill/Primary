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

    JSONArray alloys = null;

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

    /*
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://cd-cdb-1dpkpdzc.sql.tencentcdb.com:63711/alloy";
    private static final String USERNAME = "root";
    private static final String PASSWOED = "sfynb123";
    private Connection cn;
    private Statement st;
    private ResultSet rs;
    private String SQLSentence;
    */

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

            }
        }).start();

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

    private JSONObject executeHttpPost(final Request request) {
        String path="http://118.25.122.232/test/get_all_alloys.php";
        //List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("Naming_Standard", "Elektron"));
        //params.add(new BasicNameValuePair("Density_Max", "1.9"));
        List<NameValuePair> params = paramsList(request);
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
        boolean flag = false;
        String name = request.getName();
        String namingStandard = request.getNamingStandard();
        boolean[] component = request.getComponet();
        double[] doubledatas = request.getDoubleArray();
        boolean[] validation = request.getValidation();

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

    /*
    public void produceSQLSentence(final Request request) {
        boolean flag = false;
        String name = request.getName();
        String namingStandard = request.getNamingStandard();
        boolean[] component = request.getComponet();
        double[] doubledatas = request.getDoubleArray();
        boolean[] validation = request.getValidation();
        SQLSentence = "";
        SQLSentence += "SELECT * FROM attributes JOIN components USING(Name) WHERE ";
        if(validation[0]) {
            SQLSentence += "Name = \"" + name + "\"";
            flag = true;
        }
        if(validation[18]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Naming_Standard = \"" + namingStandard + "\"";
            flag = true;
        }
        if(validation[3]) {
            if(flag)
                SQLSentence += " AND ";
            boolean flag2 = false;
            SQLSentence += "FIND_IN_SET(";
            if(component[0]) {
                SQLSentence += "\"Al\", Elements)";
                flag2 = true;
            }
            if(component[1]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Zn\", Elements)";
                flag2 = true;
            }
            if(component[2]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Mn\", Elements)";
                flag2 = true;
            }
            if(component[3]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Zr\", Elements)";
                flag2 = true;
            }
            if(component[4]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Y\", Elements)";
                flag2 = true;
            }
            flag = true;
        }
        if(validation[1]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Brinell_Hardness_Mix > ";
            SQLSentence += doubledatas[0] + "";
            flag = true;
        }
        if(validation[2]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Brinell_Hardness_Max < ";
            SQLSentence += doubledatas[1] + "";
            flag = true;
        }
        if(validation[4]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Melting_Range_Min > ";
            SQLSentence += doubledatas[2] + "";
            flag = true;
        }
        if(validation[5]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Melting_Range_Max < ";
            SQLSentence += doubledatas[3] + "";
            flag = true;
        }
        if(validation[6]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Elastic_Modulus > ";
            SQLSentence += doubledatas[4] + "";
            flag = true;
        }
        if(validation[7]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Elastic_Modulus < ";
            SQLSentence += doubledatas[5] + "";
            flag = true;
        }
        if(validation[8]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Specific_Heat > ";
            SQLSentence += doubledatas[6] + "";
            flag = true;
        }
        if(validation[9]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Specific_Heat < ";
            SQLSentence += doubledatas[7] + "";
            flag = true;
        }
        if(validation[10]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Resistivity > ";
            SQLSentence += doubledatas[8] + "";
            flag = true;
        }
        if(validation[11]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Resistivity < ";
            SQLSentence += doubledatas[9] + "";
            flag = true;
        }
        if(validation[12]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Thermal_conductivity > ";
            SQLSentence += doubledatas[10] + "";
            flag = true;
        }
        if(validation[13]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Thermal_conductivity < ";
            SQLSentence += doubledatas[11] + "";
            flag = true;
        }
        if(validation[14]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Thermal_Expansion_Coefficient > ";
            SQLSentence += doubledatas[12] + "";
            flag = true;
        }
        if(validation[15]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Thermal_Expansion_Coefficient < ";
            SQLSentence += doubledatas[13] + "";
            flag = true;
        }
        if(validation[16]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Density > ";
            SQLSentence += doubledatas[14] + "";
            flag = true;
        }
        if(validation[17]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Density < ";
            SQLSentence += doubledatas[15] + "";
            flag = true;
        }
        if(validation[19]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Poisson\'s_Ratio > ";
            SQLSentence += doubledatas[16] + "";
            flag = true;
        }
        if(validation[20]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Poisson\'s_Ratio < ";
            SQLSentence += doubledatas[17] + "";
            flag = true;
        }
        if(validation[21]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Damping_Index > ";
            SQLSentence += doubledatas[18] + "";
            flag = true;
        }
        if(validation[22]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Damping_Index < ";
            SQLSentence += doubledatas[19] + "";
            flag = true;
        }
        if(validation[23]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Fracture_Toughness > ";
            SQLSentence += doubledatas[20] + "";
            flag = true;
        }
        if(validation[24]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Fracture_Toughness < ";
            SQLSentence += doubledatas[21] + "";
            flag = true;
        }
        SQLSentence += " ORDER BY Name";
        SQLSentence += ";";
        Log.i("Mainactivity", SQLSentence);
    }*/

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
