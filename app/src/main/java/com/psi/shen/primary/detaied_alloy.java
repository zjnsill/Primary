package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class detaied_alloy extends AppCompatActivity {
    private TopBar detailedAlloyTopbar;
    private TextView name,component,density,thermalCon,thermalExpan,
            specificHeat,elasticModu,resistivity,meltingPoint,hardness;

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
        Bundle AlloyItem = intent.getBundleExtra("alloyItem");
        assignValue(AlloyItem);

    }


    private void setupComponent(){
        name = findViewById(R.id.name);
        density = findViewById(R.id.density);
        component = findViewById(R.id.component);
        thermalCon = findViewById(R.id.thermalCon);
        thermalExpan = findViewById(R.id.thermalExpan);
        specificHeat = findViewById(R.id.specificHeat);
        elasticModu = findViewById(R.id.elasticModu);
        resistivity = findViewById(R.id.resistivity);
        meltingPoint = findViewById(R.id.meltingPoint);
        hardness = findViewById(R.id.meltingPoint);
    }
    private void assignValue(Bundle Item){
        boolean[] Validation = Item.getBooleanArray("validation");
        name.setText(Validation[0]? Item.getString("name"):"");
        component.setText(Validation[1]? Item.getString("component"):"");
        density.setText(Validation[2]? Item.getDouble("density")+"":"");
        thermalExpan.setText(Validation[3]? Item.getDouble("thermalExpan")+"":"");
        thermalCon.setText(Validation[4]? Item.getDouble("thermalCon")+"":"");
        specificHeat.setText(Validation[5]? Item.getDouble("specificHeat")+"":"");
        resistivity.setText(Validation[6]? Item.getDouble("resistivity")+"":"");
        elasticModu.setText(Validation[7]? Item.getDouble("elasticModu")+"":"");
        meltingPoint.setText(Validation[8]? Item.getDouble("meltingPoint_min")+"~"+Item.getDouble("meltingPoint_max"):"");
        hardness.setText(Validation[9]? Item.getDouble("hardness")+"":"");
    }



}
