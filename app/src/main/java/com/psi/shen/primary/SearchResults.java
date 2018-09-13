package com.psi.shen.primary;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {
    private TopBar topbar;
    public ArrayList<SingleAlloyItem> resultsArray = new ArrayList<>();
    private RecyclerView alloyResultsList;
    private StarredListDatabaseManager starredListDatabaseManager = StarredListDatabaseManager.getInstance(this);

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://cd-cdb-1dpkpdzc.sql.tencentcdb.com:63711/alloy";
    private static final String USERNAME = "root";
    private static final String PASSWOED = "sfynb123";
    private Connection cn;
    private Statement st;
    private ResultSet rs;
    private String SQLSentence;

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
        final SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(this,resultsArray,starredListDatabaseManager);
        searchResultsAdapter.setmOnItemClickListener(new SearchResultsAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position){ItemAction(position);}
            @Override
            public void onLongClick(int position,boolean isStarred){
                if(isStarred){
                    starredListDatabaseManager.deleteStarredListItem(resultsArray.get(position).getAlloyName());
                    Toast.makeText(SearchResults.this,"Item unstarred!",Toast.LENGTH_SHORT).show();
                }
                else {
                    StarredListItem starredListItem = new StarredListItem(resultsArray.get(position), true, false, false, false);
                    starredListDatabaseManager.addStarredListItem(starredListItem);
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
                            String returnDatas[] = new String[11];
                            returnDatas[0] = rs.getString("Alloy_Name");
                            returnDatas[1] = rs.getString("Component");
                            returnDatas[2] = rs.getString("Specific_Gravity");
                            returnDatas[3] = rs.getString("Thermal_Expansion_Coefficient");
                            returnDatas[4] = rs.getString("Thermal_conductivity");
                            returnDatas[5] = rs.getString("Specific_Heat");
                            returnDatas[6] = rs.getString("Resistivity");
                            returnDatas[7] = rs.getString("Elastic_Modulus");
                            returnDatas[8] = rs.getString("Melting_Range_Min");
                            returnDatas[9] = rs.getString("Melting_Range_Max");
                            returnDatas[10] = rs.getString("Brinell_Hardness_Max");

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
        }).start();
        //prepare data and inform recyclerView when data is prepared

    }

    private void ItemAction(int position) {
        SingleAlloyItem alloyItem = resultsArray.get(position);
        Intent jumpToDetail = new Intent(SearchResults.this, detaied_alloy.class);
        jumpToDetail.putExtra("alloyItem",new assistingTools().pacakgingbundle(alloyItem));
        startActivity(jumpToDetail);
    }
    public void produceSQLSentence(final Request request) {
        boolean flag = false;
        String name = request.getName();
        boolean[] component = request.getComponet();
        double[] doubledatas = request.getDoubleArray();
        boolean[] validation = request.getValidation();
        SQLSentence = "";
        SQLSentence += "SELECT Alloy_Name, Specific_Gravity, Thermal_Expansion_Coefficient, " +
                "Thermal_conductivity, Specific_Heat, Resistivity, Elastic_Modulus, " +
                "Melting_Range_Min, Melting_Range_Max, Brinell_Hardness_Max, Component " +
                "FROM attributes WHERE ";
        if(validation[0]) {
            SQLSentence += "Alloy_Name = \"" + name + "\"";
            flag = true;}
        if(validation[3]) {
            if(flag)
                SQLSentence += " AND ";
            boolean flag2 = false;
            SQLSentence += "FIND_IN_SET(";
            if(component[0]) {
                SQLSentence += "\"Al\", Component)";
                flag2 = true;
            }
            if(component[1]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Zn\", Component)";
                flag2 = true;
            }
            if(component[2]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Mn\", Component)";
                flag2 = true;
            }
            if(component[3]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Zr\", Component)";
                flag2 = true;
            }
            if(component[4]) {
                if(flag2)
                    SQLSentence += " AND FIND_IN_SET(";
                SQLSentence += "\"Y\", Component)";
                flag2 = true;
            }
            flag = true;
        }
        if(validation[1]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Brinell_Hardness_Max > ";
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
            SQLSentence += "Specific_Gravity > ";
            SQLSentence += doubledatas[14] + "";
            flag = true;
        }
        if(validation[17]) {
            if(flag)
                SQLSentence += " AND ";
            SQLSentence += "Specific_Gravity < ";
            SQLSentence += doubledatas[15] + "";
            flag = true;
        }
        SQLSentence += ";";
        Log.i("Mainactivity", SQLSentence);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
