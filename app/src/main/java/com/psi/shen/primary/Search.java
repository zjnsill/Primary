package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private String currentUser;
    private UserDatabaseManager starredDatabaseManager;

    private ArrayList<StarredListItem> starredListData = new ArrayList<>();

    private TopBar searchTopBar;
    private RoundRectBtn SearchBtn;

    private EditText NameET,NamingStandardET;
    private String Name,NamingStandard;
    private EditText DensityET,ThermalExpanET,ThermalConET,SpecificHeatET,ResistivityET,ElasticModuET,PoissonsRatioET,
            DampingIndexET,FractureToughnessET,MeltingRange_MinET,MeltingRange_MaxET,Hardness_MinET, Hardness_MaxET,
            Al_MinET, Al_MaxET, Mn_MinET, Mn_MaxET, Zn_MinET, Zn_MaxET, Mg_MinET, Mg_MaxET, Nd_MinET, Nd_MaxET, Gd_MinET, Gd_MaxET, Zr_MinET,
            Zr_MaxET, Ag_MinET, Ag_MaxET, Cu_MinET, Cu_MaxET, Th_MinET, Th_MaxET, Y_MinET, Y_MaxET, RareElements_MinET, RareElements_MaxET;
    private CheckBox AlCB,ZnCB,MnCB,MgCB,NdCB,GdCB,AgCB,CuCB,ThCB,YCB,ZrCB,RareElementsCB;
    private double[] doubleArray;
    private boolean[] Validation = new boolean[39];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent fromMainInterface = getIntent();
        currentUser = fromMainInterface.getStringExtra("userName");
        setupInputComponent();
        SearchBtn.setBtnOnClickListener(new RoundRectBtn.BtnOnClickListenr(){
            @Override
            public void BtnOnClick(){
                gatherInput();
                Bundle input = packagingBundle();
                Intent jumpToResults = new Intent(Search.this,SearchResults.class);
                jumpToResults.putExtra("inquiry",input);
                jumpToResults.putExtra("user",currentUser);
                startActivity(jumpToResults);
            }
        });

        //GetSomeStarredSample();
        //starredDatabase.addStarredListItem(starredListData.get(0));
        //starredDatabase.addStarredListItem(starredListData.get(1));
        //starredDatabase.addStarredListItem(starredListData.get(2));
        //Toast.makeText(this,"Some sample data added!",Toast.LENGTH_SHORT).show();






    }
    private void clearAll(){
        NameET.setText("");
        NamingStandardET.setText("");
        DensityET.setText("");
        ThermalExpanET.setText("");
        ThermalConET.setText("");
        SpecificHeatET.setText("");
        ResistivityET.setText("");
        ElasticModuET.setText("");
        PoissonsRatioET.setText("");
        DampingIndexET.setText("");
        FractureToughnessET.setText("");
        MeltingRange_MinET.setText("");
        MeltingRange_MaxET.setText("");
        Hardness_MinET.setText("");
        Hardness_MaxET.setText("");
        Al_MinET.setText("");
        Al_MaxET.setText("");
        Mn_MinET.setText("");
        Mn_MaxET.setText("");
        Zn_MinET.setText("");
        Zn_MaxET.setText("");
        Mg_MinET.setText("");
        Mg_MaxET.setText("");
        Nd_MinET.setText("");
        Nd_MaxET.setText("");
        Gd_MinET.setText("");
        Gd_MaxET.setText("");
        Zr_MinET.setText("");
        Zr_MaxET.setText("");
        Ag_MinET.setText("");
        Ag_MaxET.setText("");
        Cu_MinET.setText("");
        Cu_MaxET.setText("");
        Th_MinET.setText("");
        Th_MaxET.setText("");
        Y_MinET.setText("");
        Y_MaxET.setText("");
        RareElements_MinET.setText("");
        RareElements_MaxET.setText("");
        AlCB.setChecked(false);
        ZnCB.setChecked(false);
        MnCB.setChecked(false);
        MgCB.setChecked(false);
        NdCB.setChecked(false);
        GdCB.setChecked(false);
        AgCB.setChecked(false);
        CuCB.setChecked(false);
        ThCB.setChecked(false);
        YCB.setChecked(false);
        ZrCB.setChecked(false);
        RareElementsCB.setChecked(false);
    }

    //need layout
    void setupInputComponent(){
        SearchBtn = findViewById(R.id.searchBtn);
        searchTopBar = findViewById(R.id.searchTopBar);
        NameET = findViewById(R.id.nameET);
        NamingStandardET = findViewById(R.id.);
        DensityET = findViewById(R.id.);
        ThermalExpanET = findViewById(R.id.);
        ThermalConET = findViewById(R.id.);
        SpecificHeatET = findViewById(R.id.);
        ResistivityET = findViewById(R.id.);
        ElasticModuET = findViewById(R.id.);
        PoissonsRatioET = findViewById(R.id.);
        DampingIndexET = findViewById(R.id.);
        FractureToughnessET = findViewById(R.id.);
        MeltingRange_MinET = findViewById(R.id.);
        MeltingRange_MaxET = findViewById(R.id.);
        Hardness_MinET = findViewById(R.id.hardness_min_ET);
        Hardness_MaxET = findViewById(R.id.hardness_max_ET);
        Al_MinET = findViewById(R.id.);
        Al_MaxET = findViewById(R.id.);
        Mn_MinET = findViewById(R.id.);
        Mn_MaxET = findViewById(R.id.);
        Zn_MinET = findViewById(R.id.);
        Zn_MaxET = findViewById(R.id.);
        Mg_MinET = findViewById(R.id.);
        Mg_MaxET = findViewById(R.id.);
        Nd_MinET = findViewById(R.id.);
        Nd_MaxET = findViewById(R.id.);
        Gd_MinET = findViewById(R.id.);
        Gd_MaxET = findViewById(R.id.);
        Zr_MinET = findViewById(R.id.);
        Zr_MaxET = findViewById(R.id.);
        Ag_MinET = findViewById(R.id.);
        Ag_MaxET = findViewById(R.id.);
        Cu_MinET = findViewById(R.id.);
        Cu_MaxET = findViewById(R.id.);
        Th_MinET = findViewById(R.id.);
        Th_MaxET = findViewById(R.id.);
        Y_MinET = findViewById(R.id.);
        Y_MaxET = findViewById(R.id.);
        RareElements_MinET = findViewById(R.id.);
        RareElements_MaxET = findViewById(R.id.);
        AlCB = findViewById(R.id.);
        ZnCB = findViewById(R.id.);
        MnCB = findViewById(R.id.);
        MgCB = findViewById(R.id.);
        NdCB = findViewById(R.id.);
        GdCB = findViewById(R.id.);
        AgCB = findViewById(R.id.);
        CuCB = findViewById(R.id.);
        ThCB = findViewById(R.id.);
        YCB = findViewById(R.id.);
        ZrCB = findViewById(R.id.);
        RareElementsCB = findViewById(R.id.);
        searchTopBar.setLeftAndRightListener(new TopBar.LeftAndRightListener(){
            @Override
            public void rightListener(){
                clearAll();
            }
            @Override
            public void leftListener(){
                //Intent jumpToCustomize = new Intent(Search.this,CreateAlloy.class);
                //startActivity(jumpToCustomize);
            }
        });
    }


    boolean hasInput(EditText editText){
        return !editText.getText().toString().isEmpty();
    }
    double EditTextTodouble(EditText editText){
        if(hasInput(editText)){
            return Double.parseDouble(editText.getText().toString());
        }
        else return 0.0;
    }
    String EditTextToString(EditText editText){
        if(hasInput(editText)){
            return editText.getText().toString();
        }
        else {return "";}
    }

    private void gatherInput(){
        Name = EditTextToString(NameET);
        NamingStandard = EditTextToString(NamingStandardET);
        Validation[0]=hasInput(NameET);
        Validation[1]=hasInput(NamingStandardET);
        Validation[2]=hasInput(DensityET);
        Validation[3]=hasInput(ThermalExpanET);
        Validation[4]=hasInput(ThermalConET);
        Validation[5]=hasInput(SpecificHeatET);
        Validation[6]=hasInput(ResistivityET);
        Validation[7]=hasInput(ElasticModuET);
        Validation[8]=hasInput(PoissonsRatioET);
        Validation[9]=hasInput(DampingIndexET);
        Validation[10]=hasInput(FractureToughnessET);
        Validation[11]=hasInput(MeltingRange_MinET);
        Validation[12]=hasInput(MeltingRange_MaxET);
        Validation[13]=hasInput(Hardness_MinET);
        Validation[14]=hasInput(Hardness_MaxET);
        Validation[15]=hasInput(Al_MinET);
        Validation[16]=hasInput(Al_MaxET);
        Validation[17]=hasInput(Mn_MinET);
        Validation[18]=hasInput(Mn_MaxET);
        Validation[19]=hasInput(Zn_MinET);
        Validation[20]=hasInput(Zn_MaxET);
        Validation[21]=hasInput(Mg_MinET);
        Validation[22]=hasInput(Mg_MaxET);
        Validation[23]=hasInput(Nd_MinET);
        Validation[24]=hasInput(Nd_MaxET);
        Validation[25]=hasInput(Gd_MinET);
        Validation[26]=hasInput(Gd_MaxET);
        Validation[27]=hasInput(Zr_MinET);
        Validation[28]=hasInput(Zr_MaxET);
        Validation[29]=hasInput(Ag_MinET);
        Validation[30]=hasInput(Ag_MaxET);
        Validation[31]=hasInput(Cu_MinET);
        Validation[32]=hasInput(Cu_MaxET);
        Validation[33]=hasInput(Th_MinET);
        Validation[34]=hasInput(Th_MaxET);
        Validation[35]=hasInput(Y_MinET);
        Validation[36]=hasInput(Y_MaxET);
        Validation[37]=hasInput(RareElements_MinET);
        Validation[38]=hasInput(RareElements_MaxET);
        Validation[39]=AlCB.isChecked();
        Validation[40]=ZnCB.isChecked();
        Validation[41]=MnCB.isChecked();
        Validation[42]=MgCB.isChecked();
        Validation[43]=NdCB.isChecked();
        Validation[44]=GdCB.isChecked();
        Validation[45]=AgCB.isChecked();
        Validation[46]=CuCB.isChecked();
        Validation[47]=ThCB.isChecked();
        Validation[48]=YCB.isChecked();
        Validation[49]=ZrCB.isChecked();
        Validation[50]=RareElementsCB.isChecked();

        doubleArray=new double[]{EditTextTodouble(DensityET),EditTextTodouble(ThermalExpanET),EditTextTodouble(ThermalConET),
                EditTextTodouble(SpecificHeatET),EditTextTodouble(ResistivityET),EditTextTodouble(ElasticModuET),EditTextTodouble(PoissonsRatioET),
                EditTextTodouble(DampingIndexET),EditTextTodouble(FractureToughnessET),EditTextTodouble(MeltingRange_MinET),
                EditTextTodouble(MeltingRange_MaxET),EditTextTodouble(Hardness_MinET),EditTextTodouble(Hardness_MaxET),
                EditTextTodouble(Al_MinET),EditTextTodouble(Al_MaxET),EditTextTodouble(Mn_MinET),EditTextTodouble(Mn_MaxET),
                EditTextTodouble(Zn_MinET),EditTextTodouble(Zn_MaxET),EditTextTodouble(Mg_MinET),EditTextTodouble(Mg_MaxET),
                EditTextTodouble(Nd_MinET),EditTextTodouble(Nd_MaxET),EditTextTodouble(Gd_MinET),EditTextTodouble(Gd_MaxET),
                EditTextTodouble(Zr_MinET),EditTextTodouble(Zr_MaxET),EditTextTodouble(Ag_MinET),EditTextTodouble(Ag_MaxET),
                EditTextTodouble(Cu_MinET),EditTextTodouble(Cu_MaxET),EditTextTodouble(Th_MinET),EditTextTodouble(Th_MaxET),
                EditTextTodouble(Y_MinET),EditTextTodouble(Y_MaxET),EditTextTodouble(RareElements_MinET),EditTextTodouble(RareElements_MaxET)};
    }
    Bundle packagingBundle(){
        Bundle inquiryBundle = new Bundle();
        inquiryBundle.putString("name",Name);
        inquiryBundle.putString("namingstandard",NamingStandard);
        inquiryBundle.putDoubleArray("doubleArray",doubleArray);
        inquiryBundle.putBooleanArray("validation",Validation);
        return inquiryBundle;
    }

    /*
    void GetSomeStarredSample(){
        ArrayList<SingleAlloyItem> resultsArray = new ArrayList<>();
        boolean[] validation = new boolean[10];
        for(int i=0;i<10;i++){validation[i]=true;}
        resultsArray.add(new SingleAlloyItem("WZ311","Zn Mn Yr",12.0,32.0,54.6,56.8,78.4,98.8,34.0,23.0,0.3,validation));
        resultsArray.add(new SingleAlloyItem("ZN431","Mn Al",43.8,65,90,78,18,45,34.8,56.0,9.0,validation));
        resultsArray.add(new SingleAlloyItem("RE311","Zn Yr",98,2,4,6,70,9,45.0,56.0,9.0,validation));
        starredListData.add(new StarredListItem(resultsArray.get(0),true,false,false,false));
        starredListData.add(new StarredListItem(resultsArray.get(1),false,true,false,false));
        starredListData.add(new StarredListItem(resultsArray.get(2),false,true,true,true));
    }*/
    //from old version no longer applicable will be deleted afterwards;










}
