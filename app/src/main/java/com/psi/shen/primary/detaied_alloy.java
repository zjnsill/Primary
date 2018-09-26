package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class detaied_alloy extends AppCompatActivity {
    private TopBar detailedAlloyTopbar;
    private TextView name, namingStandard,component,density,thermalCon,thermalExpan
                    ,specificHeat,elasticModu,resistivity,meltingRange,hardness,poissonRatio,
                    dampingIndex,fractureTou,forging,weldability,machining,surfTreat,corriResis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaied_alloy);
        detailedAlloyTopbar = findViewById(R.id.DetailedAlloyTopbar);
        detailedAlloyTopbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                detaied_alloy.this.finish();
            }

            @Override
            public void rightListener() {}
        });
        setupComponent();
        Intent intent = getIntent();
        SingleAlloyItem item = intent.getParcelableExtra("clickedItem");
        assignValue(item);
    }


    private void setupComponent(){
        name = findViewById(R.id.name);
        namingStandard = findViewById(R.id.namingStandard);
        component = findViewById(R.id.component);
        density = findViewById(R.id.density);
        thermalCon = findViewById(R.id.thermalCon);
        thermalExpan = findViewById(R.id.thermalExpan);
        specificHeat = findViewById(R.id.specificHeat);
        elasticModu = findViewById(R.id.elasticModu);
        resistivity = findViewById(R.id.resistivity);
        meltingRange = findViewById(R.id.meltingRange);
        hardness = findViewById(R.id.hardness);
        poissonRatio = findViewById(R.id.poissonRatio);
        dampingIndex = findViewById(R.id.dampingIndex);
        fractureTou = findViewById(R.id.fractureToughness);
        forging = findViewById(R.id.forging);
        weldability = findViewById(R.id.weldability);
        machining = findViewById(R.id.machining);
        surfTreat = findViewById(R.id.surfaceTreatment);
        corriResis = findViewById(R.id.corrisionResistance);
    }
    private void assignValue(SingleAlloyItem item){
        name.setText(item.getAlloyName());
        namingStandard.setText(item.getNamingStandard());
        component.setText(item.getComponent());
        density.setText(item.getDensity()+"");
        thermalExpan.setText(item.getThermalExpan()+"");
        thermalCon.setText(item.getThermalCon()+"");
        specificHeat.setText(item.getSpecificHeat()+"");
        elasticModu.setText(item.getElasticModu()+"");
        resistivity.setText(item.getResistivity()+"");
        meltingRange.setText(item.getMeltingRangeString());
        hardness.setText(item.getHardness_Min()+"~"+item.getHardness_Max());
        poissonRatio.setText(item.getPoissonsRatio()+"");
        dampingIndex.setText(item.getDampingIndex()+"");
        fractureTou.setText(item.getFractureToughness()+"");
        forging.setText(item.getForging());
        weldability.setText(item.getWeldability());
        machining.setText(item.getMachining());
        surfTreat.setText(item.getSurfaceTreatment());
        corriResis.setText(item.getCorrisionResistance());
    }



}
