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
    private EditText Density_MinET, Density_MaxET, ThermalExpan_MinET, ThermalExpan_MaxET, ThermalCon_MinET,ThermalCon_MaxET,
            SpecificHeat_MinET,SpecificHeat_MaxET,Resistivity_MinET,Resistivity_MaxET,ElasticModu_MinET,ElasticModu_MaxET,
            PoissonsRatio_MinET,PoissonsRatio_MaxET, DampingIndex_MinET,DampingIndex_MaxET,FractureToughness_MinET,
            FractureToughness_MaxET, MeltingRange_MinET,MeltingRange_MaxET,Hardness_MinET, Hardness_MaxET,
            Al_MinET, Al_MaxET, Mn_MinET, Mn_MaxET, Zn_MinET, Zn_MaxET, Mg_MinET, Mg_MaxET, Nd_MinET, Nd_MaxET, Gd_MinET, Gd_MaxET, Zr_MinET,
            Zr_MaxET, Ag_MinET, Ag_MaxET, Cu_MinET, Cu_MaxET, Th_MinET, Th_MaxET, Y_MinET, Y_MaxET, RareElements_MinET, RareElements_MaxET;
    private CheckBox AlCB,ZnCB,MnCB,MgCB,NdCB,GdCB,AgCB,CuCB,ThCB,YCB,ZrCB,RareElementsCB;
    private double[] doubleArray;
    private boolean[] Validation = new boolean[60];
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
                jumpToResults.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        Density_MinET.setText("");
        Density_MaxET.setText("");
        ThermalExpan_MinET.setText("");
        ThermalExpan_MaxET.setText("");
        ThermalCon_MinET.setText("");
        ThermalCon_MaxET.setText("");
        SpecificHeat_MinET.setText("");
        SpecificHeat_MaxET.setText("");
        Resistivity_MinET.setText("");
        Resistivity_MaxET.setText("");
        ElasticModu_MinET.setText("");
        ElasticModu_MaxET.setText("");
        PoissonsRatio_MinET.setText("");
        PoissonsRatio_MaxET.setText("");
        DampingIndex_MinET.setText("");
        DampingIndex_MaxET.setText("");
        FractureToughness_MinET.setText("");
        FractureToughness_MaxET.setText("");
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
        NameET = findViewById(R.id.NameET);
        NamingStandardET = findViewById(R.id.NamingStandardET);
        Density_MinET = findViewById(R.id.Density_min_ET);
        Density_MaxET = findViewById(R.id.Density_max_ET);
        ThermalExpan_MinET = findViewById(R.id.ThermalExpan_min_ET);
        ThermalExpan_MaxET = findViewById(R.id.ThermalExpan_max_ET);
        ThermalCon_MinET = findViewById(R.id.ThermalCon_min_ET);
        ThermalCon_MaxET = findViewById(R.id.ThermalCon_max_ET);
        SpecificHeat_MinET = findViewById(R.id.SpecificHeat_min_ET);
        SpecificHeat_MaxET = findViewById(R.id.SpecificHeat_max_ET);
        Resistivity_MinET = findViewById(R.id.Resistivity_min_ET);
        Resistivity_MaxET = findViewById(R.id.Resistivity_max_ET);
        ElasticModu_MinET = findViewById(R.id.ElasticModu_min_ET);
        ElasticModu_MaxET = findViewById(R.id.ElasticModu_max_ET);
        PoissonsRatio_MinET = findViewById(R.id.PoissonsRatio_min_ET);
        PoissonsRatio_MaxET = findViewById(R.id.PoissonsRatio_max_ET);
        DampingIndex_MinET = findViewById(R.id.DampingIndex_min_ET);
        DampingIndex_MaxET = findViewById(R.id.DampingIndex_max_ET);
        FractureToughness_MinET = findViewById(R.id.FractureToughness_min_ET);
        FractureToughness_MaxET = findViewById(R.id.FractureToughness_max_ET);
        MeltingRange_MinET = findViewById(R.id.MeltingRange_min_ET);
        MeltingRange_MaxET = findViewById(R.id.MeltingRange_max_ET);
        Hardness_MinET = findViewById(R.id.Hardness_min_ET);
        Hardness_MaxET = findViewById(R.id.Hardness_max_ET);
        Al_MinET = findViewById(R.id.Al_min_ET);
        Al_MaxET = findViewById(R.id.Al_max_ET);
        Mn_MinET = findViewById(R.id.Mn_min_ET);
        Mn_MaxET = findViewById(R.id.Mn_max_ET);
        Zn_MinET = findViewById(R.id.Zn_min_ET);
        Zn_MaxET = findViewById(R.id.Zn_max_ET);
        Mg_MinET = findViewById(R.id.Mg_min_ET);
        Mg_MaxET = findViewById(R.id.Mg_max_ET);
        Nd_MinET = findViewById(R.id.Nd_min_ET);
        Nd_MaxET = findViewById(R.id.Nd_max_ET);
        Gd_MinET = findViewById(R.id.Gd_min_ET);
        Gd_MaxET = findViewById(R.id.Gd_max_ET);
        Zr_MinET = findViewById(R.id.Zr_min_ET);
        Zr_MaxET = findViewById(R.id.Zr_max_ET);
        Ag_MinET = findViewById(R.id.Ag_min_ET);
        Ag_MaxET = findViewById(R.id.Ag_max_ET);
        Cu_MinET = findViewById(R.id.Cu_min_ET);
        Cu_MaxET = findViewById(R.id.Cu_max_ET);
        Th_MinET = findViewById(R.id.Th_min_ET);
        Th_MaxET = findViewById(R.id.Th_max_ET);
        Y_MinET = findViewById(R.id.Y_min_ET);
        Y_MaxET = findViewById(R.id.Y_max_ET);
        RareElements_MinET = findViewById(R.id.RareElements_min_ET);
        RareElements_MaxET = findViewById(R.id.RareElementsCB_max_ET);
        AlCB = findViewById(R.id.AlCB);
        ZnCB = findViewById(R.id.ZnCB);
        MnCB = findViewById(R.id.MnCB);
        MgCB = findViewById(R.id.MgCB);
        NdCB = findViewById(R.id.NdCB);
        GdCB = findViewById(R.id.GdCB);
        AgCB = findViewById(R.id.AgCB);
        CuCB = findViewById(R.id.CuCB);
        ThCB = findViewById(R.id.ThCB);
        YCB = findViewById(R.id.YCB);
        ZrCB = findViewById(R.id.ZrCB);
        RareElementsCB = findViewById(R.id.RareElementsCB);
        searchTopBar.setLeftAndRightListener(new TopBar.LeftAndRightListener(){
            @Override
            public void rightListener(){
                clearAll();
            }
            @Override
            public void leftListener(){
                Search.this.finish();
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
        Validation[0] = hasInput(NameET);Validation[1] = hasInput(NamingStandardET);
        Validation[2] = hasInput(Density_MinET);Validation[3] = hasInput(Density_MaxET);
        Validation[4] = hasInput(ThermalExpan_MinET);Validation[5] = hasInput(ThermalExpan_MaxET);
        Validation[6] = hasInput(ThermalCon_MinET);Validation[7] = hasInput(ThermalCon_MaxET);
        Validation[8] = hasInput(SpecificHeat_MinET);Validation[9] = hasInput(SpecificHeat_MaxET);
        Validation[10] = hasInput(Resistivity_MinET);Validation[11] = hasInput(Resistivity_MaxET);
        Validation[12] = hasInput(ElasticModu_MinET);Validation[13] = hasInput(ElasticModu_MaxET);
        Validation[14] = hasInput(PoissonsRatio_MinET);Validation[15] = hasInput(PoissonsRatio_MaxET);
        Validation[16] = hasInput(DampingIndex_MinET);Validation[17] = hasInput(DampingIndex_MaxET);
        Validation[18] = hasInput(FractureToughness_MinET);Validation[19] = hasInput(FractureToughness_MaxET);
        Validation[20] = hasInput(MeltingRange_MinET);Validation[21] = hasInput(MeltingRange_MaxET);
        Validation[22] = hasInput(Hardness_MinET);Validation[23] = hasInput(Hardness_MaxET);
        Validation[24] = hasInput(Al_MinET); Validation[25] = hasInput(Al_MaxET);
        Validation[26] = hasInput(Mn_MinET); Validation[27] = hasInput(Mn_MaxET);
        Validation[28] = hasInput(Zn_MinET); Validation[29] = hasInput(Zn_MaxET);
        Validation[30] = hasInput(Mg_MinET); Validation[31] = hasInput(Mg_MaxET);
        Validation[32] = hasInput(Nd_MinET); Validation[33] = hasInput(Nd_MaxET);
        Validation[34] = hasInput(Gd_MinET); Validation[35] = hasInput(Gd_MaxET);
        Validation[36] = hasInput(Zr_MinET); Validation[37] = hasInput(Zr_MaxET);
        Validation[38] = hasInput(Ag_MinET); Validation[39] = hasInput(Ag_MaxET);
        Validation[40] = hasInput(Cu_MinET); Validation[41] = hasInput(Cu_MaxET);
        Validation[42] = hasInput(Th_MinET); Validation[43] = hasInput(Th_MaxET);
        Validation[44] = hasInput(Y_MinET); Validation[45] = hasInput(Y_MaxET);
        Validation[46] = hasInput(RareElements_MinET); Validation[47] = hasInput(RareElements_MaxET);
        Validation[48] = AlCB.isChecked();
        Validation[49] = MnCB.isChecked();
        Validation[50] = ZnCB.isChecked();
        Validation[51] = MgCB.isChecked();
        Validation[52] = NdCB.isChecked();
        Validation[53] = GdCB.isChecked();
        Validation[54] = ZrCB.isChecked();
        Validation[55] = AgCB.isChecked();
        Validation[56] = CuCB.isChecked();
        Validation[57] = ThCB.isChecked();
        Validation[58] = YCB.isChecked();
        Validation[59] = RareElementsCB.isChecked();

        doubleArray=new double[]{EditTextTodouble(Density_MinET),EditTextTodouble(Density_MaxET),EditTextTodouble(ThermalExpan_MinET),
                EditTextTodouble(ThermalExpan_MaxET),EditTextTodouble(ThermalCon_MinET),EditTextTodouble(ThermalCon_MaxET),
                EditTextTodouble(SpecificHeat_MinET),EditTextTodouble(SpecificHeat_MaxET),EditTextTodouble(Resistivity_MinET),
                EditTextTodouble(Resistivity_MaxET),EditTextTodouble(ElasticModu_MinET),EditTextTodouble(ElasticModu_MaxET),
                EditTextTodouble(PoissonsRatio_MinET),EditTextTodouble(PoissonsRatio_MaxET), EditTextTodouble(DampingIndex_MinET),
                EditTextTodouble(DampingIndex_MaxET),EditTextTodouble(FractureToughness_MinET),EditTextTodouble(FractureToughness_MaxET),EditTextTodouble(MeltingRange_MinET),
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
        inquiryBundle.putString("namingStandard",NamingStandard);
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
