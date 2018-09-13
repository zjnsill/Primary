package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAlloy extends AppCompatActivity {
    private StarredListDatabaseManager starredDatabase= new StarredListDatabaseManager(this);

    private EditText nameET,hardnessET,meltingPoint_minET,meltingPoint_maxET,
            elasticModuET,specificHeatET,resistivityET,thermalConET,thermalExpanET,densityET;
    private CheckBox AlCB,ZnCB,MnCB,ZrCB,YrCB,OtherCB;
    //String name,component;
    //double hardness,meltingPoint_min,meltingPoint_max,
    //        elasticModu,specificHeat,resistivity,thermalCon,thermalExpan,density;
    private boolean[] validation=new boolean[10];
    private TopBar customizeTopbar;
    private assistingTools toolSet = new assistingTools();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alloy);
        setupComponent();

    }

    private void setupComponent(){
        customizeTopbar=findViewById(R.id.createTopBar);
        customizeTopbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                CreateAlloy.this.finish();
            }

            @Override
            public void rightListener() {
                if(!toolSet.hasInput(nameET)){Toast.makeText(CreateAlloy.this,"Please enter alloy name!",Toast.LENGTH_SHORT).show();}
                else {
                    StarredListItem starredListItem = gatherData();
                    starredDatabase.addStarredListItem(starredListItem);
                    Toast ts = Toast.makeText(CreateAlloy.this, "Item successfully saved to starred list!", Toast.LENGTH_SHORT);
                    ts.show();
                    Intent backToSearch = new Intent(CreateAlloy.this, Search.class);
                    startActivity(backToSearch);
                }
            }
        });
        nameET=findViewById(R.id.name);
        hardnessET=findViewById(R.id.hardness);
        meltingPoint_minET=findViewById(R.id.meltingPoint_min);
        meltingPoint_maxET = findViewById(R.id.meltingPoint_max);
        elasticModuET=findViewById(R.id.elasticModu);
        specificHeatET=findViewById(R.id.specificHeat);
        resistivityET=findViewById(R.id.resistivity);
        thermalConET=findViewById(R.id.thermalCon);
        thermalExpanET=findViewById(R.id.thermalExpan);
        densityET=findViewById(R.id.density);
        AlCB=findViewById(R.id.AlCB);
        ZnCB=findViewById(R.id.ZnCB);
        MnCB=findViewById(R.id.MnCB);
        ZrCB=findViewById(R.id.ZrCB);
        YrCB=findViewById(R.id.YrCB);
        OtherCB=findViewById(R.id.OtherCB);
    }

    private StarredListItem gatherData(){
        boolean[] validation = new boolean[10];
        boolean[] component = new boolean[6];
        validation[0]=toolSet.hasInput(nameET);
        String name = toolSet.EditTextToString(nameET);
        component[0]=AlCB.isSelected();
        component[1]=ZnCB.isSelected();
        component[2]=MnCB.isSelected();
        component[3]=ZrCB.isSelected();
        component[4]=YrCB.isSelected();
        component[5]=OtherCB.isSelected();
        if(toolSet.isAllFalse(component)){validation[1]=false;}
        String componentString = toolSet.getStringComponent(component);
        validation[2]=toolSet.hasInput(densityET);
        double density = toolSet.EditTextTodouble(densityET);
        validation[3]=toolSet.hasInput(thermalExpanET);
        double thermalExpan = toolSet.EditTextTodouble(thermalExpanET);
        validation[4]=toolSet.hasInput(thermalConET);
        double thermalCon = toolSet.EditTextTodouble(thermalConET);
        validation[5]=toolSet.hasInput(specificHeatET);
        double specificHeat = toolSet.EditTextTodouble(specificHeatET);
        validation[6]=toolSet.hasInput(resistivityET);
        double resistivity = toolSet.EditTextTodouble(resistivityET);
        validation[7]=toolSet.hasInput(elasticModuET);
        double elasticModu = toolSet.EditTextTodouble(elasticModuET);
        validation[8]=(toolSet.hasInput(meltingPoint_minET))&(toolSet.hasInput(meltingPoint_maxET));
        double meltingPoint_min = toolSet.EditTextTodouble(meltingPoint_minET);
        double meltingPoint_max = toolSet.EditTextTodouble(meltingPoint_maxET);
        validation[9]=toolSet.hasInput(hardnessET);
        double hardness = toolSet.EditTextTodouble(hardnessET);
        SingleAlloyItem singleAlloyItem = new SingleAlloyItem(name,componentString,density,thermalExpan,thermalCon,specificHeat,
                resistivity,elasticModu,meltingPoint_min,meltingPoint_max,hardness,validation);
        StarredListItem starredListItem = new StarredListItem(singleAlloyItem,false,true,false,false);
        return starredListItem;
    }





}
