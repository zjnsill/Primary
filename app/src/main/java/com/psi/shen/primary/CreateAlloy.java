package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAlloy extends AppCompatActivity {
    private String currentUser;
    private UserDatabaseManager userDatabaseManager;

    private EditText nameET,namingStandardET,hardnessMinET,hardnessMaxET,meltingPoint_minET,meltingPoint_maxET,
            elasticModuET,specificHeatET,resistivityET,thermalConET,thermalExpanET,densityET,possionsET,
            dampingIndexET,forgingET,weldablityET,machiningET,surfaceTreatET,corrisionET,fractureET;
    private CheckBox AlCB,ZnCB,MnCB,ZrCB,YrCB,OtherCB;
    private TopBar customizeTopbar;
    private assistingTools toolSet = new assistingTools();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("userName");
        userDatabaseManager = UserDatabaseManager.getInstance(this,currentUser);
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
                    userDatabaseManager.addStarredItem(starredListItem);
                    Toast ts = Toast.makeText(CreateAlloy.this, "Item successfully saved to starred list!", Toast.LENGTH_SHORT);
                    ts.show();
                    Intent backToSearch = new Intent(CreateAlloy.this, Search.class);
                    startActivity(backToSearch);
                }
            }
        });
        nameET=findViewById(R.id.name);
        namingStandardET=findViewById(R.id.namingStandard);
        hardnessMinET=findViewById(R.id.hardnessMin);
        hardnessMaxET = findViewById(R.id.hardnessMax);
        meltingPoint_minET=findViewById(R.id.meltingPoint_min);
        meltingPoint_maxET = findViewById(R.id.meltingPoint_max);
        elasticModuET=findViewById(R.id.elasticModu);
        specificHeatET=findViewById(R.id.specificHeat);
        resistivityET=findViewById(R.id.resistivity);
        thermalConET=findViewById(R.id.thermalCon);
        thermalExpanET=findViewById(R.id.thermalExpan);
        densityET=findViewById(R.id.density);
        possionsET = findViewById(R.id.poissonsRatio);
        dampingIndexET = findViewById(R.id.dampingIndex);
        forgingET = findViewById(R.id.forging);
        weldablityET = findViewById(R.id.weldability);
        machiningET = findViewById(R.id.machining);
        surfaceTreatET = findViewById(R.id.surfaceTreatment);
        corrisionET =findViewById(R.id.corrisionResistance);
        fractureET = findViewById(R.id.fractureToughness);
        AlCB=findViewById(R.id.AlCB);
        ZnCB=findViewById(R.id.ZnCB);
        MnCB=findViewById(R.id.MnCB);
        ZrCB=findViewById(R.id.ZrCB);
        YrCB=findViewById(R.id.YrCB);
        OtherCB=findViewById(R.id.OtherCB);
    }

    private StarredListItem gatherData(){
        boolean[] validation=new boolean[21];
        boolean[] component = new boolean[6];
        validation[0]=toolSet.hasInput(nameET);
        validation[1]=toolSet.hasInput(namingStandardET);
        String namingStand = toolSet.EditTextToString(namingStandardET);
        String name = toolSet.EditTextToString(nameET);
        component[0]=AlCB.isSelected();
        component[1]=ZnCB.isSelected();
        component[2]=MnCB.isSelected();
        component[3]=ZrCB.isSelected();
        component[4]=YrCB.isSelected();
        component[5]=OtherCB.isSelected();
        validation[2]= toolSet.isAllFalse(component);
        String componentString = toolSet.getStringComponent(component);
        validation[3]=toolSet.hasInput(densityET);
        double density = toolSet.EditTextTodouble(densityET);
        validation[4]=toolSet.hasInput(thermalExpanET);
        double thermalExpan = toolSet.EditTextTodouble(thermalExpanET);
        validation[5]=toolSet.hasInput(thermalConET);
        double thermalCon = toolSet.EditTextTodouble(thermalConET);
        validation[6]=toolSet.hasInput(specificHeatET);
        double specificHeat = toolSet.EditTextTodouble(specificHeatET);
        validation[7]=toolSet.hasInput(resistivityET);
        double resistivity = toolSet.EditTextTodouble(resistivityET);
        validation[8]=toolSet.hasInput(elasticModuET);
        double elasticModu = toolSet.EditTextTodouble(elasticModuET);
        validation[9]=toolSet.hasInput(possionsET);
        double possionRatio = toolSet.EditTextTodouble(possionsET);
        validation[10]=(toolSet.hasInput(meltingPoint_minET))&(toolSet.hasInput(meltingPoint_maxET));
        double meltingPoint_min = toolSet.EditTextTodouble(meltingPoint_minET);
        double meltingPoint_max = toolSet.EditTextTodouble(meltingPoint_maxET);
        validation[11]=toolSet.hasInput(dampingIndexET);
        double dampingIndex = toolSet.EditTextTodouble(dampingIndexET);
        validation[12]=toolSet.hasInput(hardnessMinET)&&toolSet.hasInput(hardnessMaxET);
        double hardnessMin = toolSet.EditTextTodouble(hardnessMinET);
        double hardnessMax = toolSet.EditTextTodouble(hardnessMaxET);
        validation[13] = toolSet.hasInput(forgingET);
        String forging = toolSet.EditTextToString(forgingET);
        validation[14] = toolSet.hasInput(weldablityET);
        String weldability = toolSet.EditTextToString(weldablityET);
        validation[15] = toolSet.hasInput(machiningET);
        String machining = toolSet.EditTextToString(machiningET);
        validation[16] = toolSet.hasInput(surfaceTreatET);
        String surfaceTreat = toolSet.EditTextToString(surfaceTreatET);
        validation[17] = toolSet.hasInput(corrisionET);
        String corrision = toolSet.EditTextToString(corrisionET);
        validation[18] = toolSet.hasInput(fractureET);
        double fracture = toolSet.EditTextTodouble(fractureET);

        SingleAlloyItem singleAlloyItem = new SingleAlloyItem(name,namingStand,componentString,density,thermalExpan,thermalCon,specificHeat,resistivity,
                elasticModu,possionRatio,meltingPoint_min,meltingPoint_max,dampingIndex,hardnessMin,hardnessMax,forging,weldability,machining,surfaceTreat,corrision,
                fracture,validation);

        StarredListItem starredListItem = new StarredListItem(singleAlloyItem,false,true,false,false);
        return starredListItem;
    }





}
