package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView compoPercen = findViewById(R.id.CompoPercentage);
        Intent intent = getIntent();
        SingleAlloyItem item = intent.getParcelableExtra("clickedItem");
        if(item.getComponentPercentage()==null) {
            compoPercen.setVisibility(View.GONE);
        }else {
            compoPercen.setText(item.getComponentPercentage());
        }
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
        fractureTou = findViewById(R.id.fractureTou);
        forging = findViewById(R.id.forging);
        weldability = findViewById(R.id.weldability);
        machining = findViewById(R.id.machining);
        surfTreat = findViewById(R.id.surfTreat);
        corriResis = findViewById(R.id.corriResis);
    }
    private void assignValue(SingleAlloyItem item){
        name.setText(item.getAlloyName());
        setNullableText(namingStandard,item.getNamingStandard());
        setNullableText(component,item.getComponent());
        setNullableText(density,item.getDensity());
        setNullableText(thermalExpan,item.getThermalExpan());
        setNullableText(thermalCon,item.getThermalCon());
        setNullableText(specificHeat,item.getSpecificHeat());
        setNullableText(elasticModu,item.getElasticModu());
        setNullableText(resistivity,item.getResistivity());
        setNullableText(meltingRange,item.getMeltingRangeString());
        setNullableText(hardness,item.getHardnessString());
        setNullableText(poissonRatio,item.getPoissonsRatio());
        setNullableText(dampingIndex,item.getDampingIndex());
        setNullableText(fractureTou,item.getFractureToughness());
        setNullableText(forging,item.getForging());
        setNullableText(weldability,item.getWeldability());
        setNullableText(machining,item.getMachining());
        setNullableText(surfTreat,item.getSurfaceTreatment());
        setNullableText(corriResis,item.getCorrisionResistance());
    }
    private void setNullableText(TextView editText,Double nullableDouble){
        editText.setText(nullableDouble==null? "No data":nullableDouble.toString());
    }
    private void setNullableText(TextView editText, String in){
        editText.setText(in==null? "No data":in);
    }



}
