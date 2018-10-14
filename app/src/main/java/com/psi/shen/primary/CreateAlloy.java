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
                if(nameET.getText().toString().isEmpty()){Toast.makeText(CreateAlloy.this,"Please enter alloy name!",Toast.LENGTH_SHORT).show();}
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
        String namingStand = toolSet.EditTextToString(namingStandardET);
        String name = toolSet.EditTextToString(nameET);
        boolean[] component = new boolean[6];
        component[0]=AlCB.isSelected();
        component[1]=ZnCB.isSelected();
        component[2]=MnCB.isSelected();
        component[3]=ZrCB.isSelected();
        component[4]=YrCB.isSelected();
        component[5]=OtherCB.isSelected();
        String componentString = toolSet.getStringComponent(component);
        Double density = toolSet.EditTextToDouble(densityET);
        Double thermalExpan = toolSet.EditTextToDouble(thermalExpanET);
        Double thermalCon = toolSet.EditTextToDouble(thermalConET);
        Double specificHeat = toolSet.EditTextToDouble(specificHeatET);
        Double resistivity = toolSet.EditTextToDouble(resistivityET);
        Double elasticModu = toolSet.EditTextToDouble(elasticModuET);
        Double possionRatio = toolSet.EditTextToDouble(possionsET);
        Double meltingPoint_min = toolSet.EditTextToDouble(meltingPoint_minET);
        Double meltingPoint_max = toolSet.EditTextToDouble(meltingPoint_maxET);
        Double dampingIndex = toolSet.EditTextToDouble(dampingIndexET);
        Double hardnessMin = toolSet.EditTextToDouble(hardnessMinET);
        Double hardnessMax = toolSet.EditTextToDouble(hardnessMaxET);
        String forging = toolSet.EditTextToString(forgingET);
        String weldability = toolSet.EditTextToString(weldablityET);
        String machining = toolSet.EditTextToString(machiningET);
        String surfaceTreat = toolSet.EditTextToString(surfaceTreatET);
        String corrision = toolSet.EditTextToString(corrisionET);
        Double fracture = toolSet.EditTextToDouble(fractureET);

        SingleAlloyItem singleAlloyItem = new SingleAlloyItem.Builder(name).NamingStandard(namingStand).Component(componentString).Density(density).ThermalCon(thermalCon)
                .ThermalExpan(thermalExpan).SpecificHeat(specificHeat).Resistivity(resistivity).ElasticModu(elasticModu).PoissonRatio(possionRatio).MeltingRange(meltingPoint_min,meltingPoint_max)
                .DampingIndex(dampingIndex).Hardness(hardnessMin,hardnessMax).Forging(forging).Weldability(weldability).Machining(machining).SurfaceTreatment(surfaceTreat).CorrisionResistance(corrision)
                .FractureTou(fracture).build();

        StarredListItem starredListItem = new StarredListItem(singleAlloyItem,false,true,false,false);
        return starredListItem;
    }

}
