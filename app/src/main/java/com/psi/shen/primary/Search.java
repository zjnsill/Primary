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

    private StarredListDatabaseManager starredDatabase= StarredListDatabaseManager.getInstance(this);

    private ArrayList<StarredListItem> starredListData = new ArrayList<>();

    private TopBar searchTopBar;
    private RoundRectBtn SearchBtn;

    private EditText nameET;
    private String Name;

    private EditText hardness_minET,hardness_maxET;
    //private double hardness_min,hardness_max;
    private CheckBox AlCB,ZnCB,MnCB,ZrCB,YrCB,OtherCB;
    private boolean[] Component=new boolean[6];
    private EditText MeltingPoint_minET,MeltingPoint_maxET;
    //private double MeltingPoint_min,MeltingPoint_max;
    private EditText ElasticModu_minET,ElasticModu_maxET;
    //private double ElasticModu_min,ElasticModu_max;
    private EditText SpecificHeat_minET,SpecificHeat_maxET;
    //private double SpecificHeat_min,SpecificHeat_max;
    private EditText Resistivity_minET,Resistivity_maxET;
    //private double Resistivity_min,Resistivity_max;
    private EditText ThermalCon_minET,ThermalCon_maxET;
    //private double ThermalCon_min,ThermalCon_max;
    private EditText ThermalExpan_minET,ThermalExpan_maxET;
    //private double ThermalExpan_min,ThermalExpan_max;
    private EditText Density_minET,Density_maxET;
    //private double Density_min,Density_max;
    private double[] doubleArray ;
    private boolean[] Validation = new boolean[18];//[2+16]
    //Name -> boolean[0], hardness_min&max -> boolean[1]&[2], Component -> boolean[3],
    // MeltingPoint_min&max -> boolean[4]&[5], ElasticModu_min&max -> boolean[6]&[7],
    // SpecificHeat_min&max -> boolean[8]&[9], Resistivity_min&max -> boolean[10]&[11],
    // ThermalCon_min&max -> boolean[12]&[13], ThermalExpan_min&max -> boolean[14]&[15],
    // Density_min&max -> boolean[16]&[17]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupInputComponent();
        SearchBtn.setBtnOnClickListener(new RoundRectBtn.BtnOnClickListenr(){
            @Override
            public void BtnOnClick(){
                gatherInput();
                Bundle input = packagingBundle();
                Intent jumpToResults = new Intent(Search.this,SearchResults.class);
                jumpToResults.putExtra("inquiry",input);
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
        nameET.setText("");
        hardness_minET.setText("");
        hardness_maxET.setText("");
        AlCB.setChecked(false);
        ZnCB.setChecked(false);
        MnCB.setChecked(false);
        ZrCB.setChecked(false);
        YrCB.setChecked(false);
        OtherCB.setChecked(false);
        MeltingPoint_minET.setText("");
        MeltingPoint_maxET.setText("");
        ElasticModu_minET.setText("");
        ElasticModu_maxET.setText("");
        SpecificHeat_minET.setText("");
        SpecificHeat_maxET.setText("");
        Resistivity_minET.setText("");
        Resistivity_maxET.setText("");
        ThermalExpan_minET.setText("");
        ThermalExpan_maxET.setText("");
        ThermalCon_minET.setText("");
        ThermalCon_maxET.setText("");
        Density_minET.setText("");
        Density_maxET.setText("");
    }

    void setupInputComponent(){
        SearchBtn = findViewById(R.id.searchBtn);
        searchTopBar = findViewById(R.id.searchTopBar);
        nameET = findViewById(R.id.nameET);
        hardness_minET=findViewById(R.id.hardness_min_ET);
        hardness_maxET=findViewById(R.id.hardness_max_ET);
        AlCB = findViewById(R.id.Al);
        ZnCB = findViewById(R.id.Zn);
        MnCB = findViewById(R.id.Mn);
        ZrCB = findViewById(R.id.Zr);
        YrCB = findViewById(R.id.Yr);
        OtherCB = findViewById(R.id.Other);
        MeltingPoint_minET = findViewById(R.id.meltingPoint_min_ET);
        MeltingPoint_maxET = findViewById(R.id.meltingPoint_max_ET);
        ElasticModu_minET = findViewById(R.id.elasticModu_min_ET);
        ElasticModu_maxET = findViewById(R.id.elasticModu_max_ET);
        SpecificHeat_minET = findViewById(R.id.specificHeat_min_ET);
        SpecificHeat_maxET = findViewById(R.id.specificHeat_max_ET);
        Resistivity_minET = findViewById(R.id.resistivity_min_ET);
        Resistivity_maxET = findViewById(R.id.resistivity_max_ET);
        ThermalCon_minET = findViewById(R.id.thermalCon_min_ET);
        ThermalCon_maxET  = findViewById(R.id.thermnalCon_max_ET);
        ThermalExpan_minET = findViewById(R.id.thermalExpan_min_ET);
        ThermalExpan_maxET = findViewById(R.id.thermnalExpan_max_ET);
        Density_maxET = findViewById(R.id.density_max_ET);
        Density_minET = findViewById(R.id.density_min_ET);
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

        SearchBtn.setBtnOnClickListener(new RoundRectBtn.BtnOnClickListenr() {
            @Override
            public void BtnOnClick() {
                gatherInput();
                Bundle inquiry = packagingBundle();
                Intent jumpToResults = new Intent(Search.this,SearchResults.class);
                jumpToResults.putExtra("inquiry",inquiry);
                startActivity(jumpToResults);
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
        Name = EditTextToString(nameET);
        Validation[0]=hasInput(nameET);
        Component[0]=AlCB.isChecked();
        Component[1]=ZnCB.isChecked();
        Component[2]=MnCB.isChecked();
        Component[3]=ZrCB.isChecked();
        Component[4]=YrCB.isChecked();
        Component[5]=OtherCB.isChecked();
        if (!(Component[0] || Component[1] || Component[2] || Component[3]||Component[4]||Component[5])){Validation[3]=false;}
        else {Validation[3]=true;}
        doubleArray=new double[]{EditTextTodouble(hardness_minET),EditTextTodouble(hardness_maxET),EditTextTodouble(MeltingPoint_minET),
                EditTextTodouble(MeltingPoint_maxET),EditTextTodouble(ElasticModu_minET),EditTextTodouble(ElasticModu_maxET),
                EditTextTodouble(SpecificHeat_minET),EditTextTodouble(SpecificHeat_maxET),EditTextTodouble(Resistivity_minET),
                EditTextTodouble(Resistivity_maxET),EditTextTodouble(ThermalCon_maxET),EditTextTodouble(ThermalCon_maxET),
                EditTextTodouble(ThermalExpan_minET),EditTextTodouble(ThermalExpan_maxET),EditTextTodouble(Density_minET),EditTextTodouble(Density_maxET)};
        Validation[1]=hasInput(hardness_minET);
        Validation[2]=hasInput(hardness_maxET);
        Validation[4]=hasInput(MeltingPoint_minET);
        Validation[5]=hasInput(MeltingPoint_maxET);
        Validation[6]=hasInput(ElasticModu_minET);
        Validation[7]=hasInput(ElasticModu_maxET);
        Validation[8]=hasInput(SpecificHeat_minET);
        Validation[9]=hasInput(SpecificHeat_maxET);
        Validation[10]=hasInput(Resistivity_minET);
        Validation[11] = hasInput(Resistivity_maxET);
        Validation[12]=hasInput(ThermalCon_minET);
        Validation[13]=hasInput(ThermalCon_maxET);
        Validation[14] = hasInput(ThermalExpan_minET);
        Validation[15] = hasInput(ThermalExpan_maxET);
        Validation[16]=hasInput(Density_minET);
        Validation[17] = hasInput(Density_maxET);
    }
    Bundle packagingBundle(){
        Bundle inquiryBundle = new Bundle();
        inquiryBundle.putString("name",Name);
        inquiryBundle.putBooleanArray("component",Component);
        inquiryBundle.putDoubleArray("doubleArray",doubleArray);
        inquiryBundle.putBooleanArray("validation",Validation);
        return inquiryBundle;
    }

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
    }










}
