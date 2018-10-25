package com.psi.shen.primary;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class SortAlloys extends AppCompatActivity {
    private ArrayList<SingleAlloyItem> resultsArray = new ArrayList<>();

    private EditText SortET;
    private TopBar topBar;
    private TextView testTV;

    private BarChart barChart;

    private String selectedSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_alloys);

        Intent fromResultInterface = getIntent();
        resultsArray = fromResultInterface.getParcelableArrayListExtra("resultsArray");

        SortET = findViewById(R.id.SortET);
        testTV = findViewById(R.id.testTV);
        topBar = findViewById(R.id.sortAlloysTopbar);
        topBar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                SortAlloys.this.finish();
            }

            @Override
            public void rightListener() {

            }
        });

        SortET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });
    }

    private void showSelectDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        View view = View.inflate(this, R.layout.sort_select_dialog, null);
        final RadioGroup sortRG;
        final RelativeLayout sortRL, cancelRL;
        sortRG = view.findViewById(R.id.SortRG);
        sortRL = view.findViewById(R.id.SortRL);
        cancelRL = view.findViewById(R.id.CancelRL);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        view.setMinimumHeight((int)(ScreenSizeUtils.getInstance(this).getScreenHeight() * 0.23f));
        Window dialogWindow =dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int)(ScreenSizeUtils.getInstance(this).getScreenWidth());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        dialog.show();

        cancelRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        sortRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = sortRG.getCheckedRadioButtonId();
                switch (selected) {
                    case R.id.DensityRB:
                        selectedSort = "Density";
                        break;
                    case R.id.ThermalConRB:
                        selectedSort = "Thermal Conductivity";
                        break;
                    case R.id.ThermalExpanRB:
                        selectedSort = "Thermal Expansion";
                        break;
                    case R.id.SpecificHeatRB:
                        selectedSort = "Specific Heat";
                        break;
                    case R.id.ElasticModuRB:
                        selectedSort = "Elastic Modulus";
                        break;
                    case R.id.ResistivityRB:
                        selectedSort = "Resistivity";
                        break;
                    case R.id.PoissonsRatioRB:
                        selectedSort = "Poisson's Ratio";
                        break;
                    case R.id.DampingIndexRB:
                        selectedSort = "Damping Index";
                        break;
                    case R.id.FractureToughnessRB:
                        selectedSort = "Fracture Toughness";
                        break;
                }
                SortET.setText(selectedSort);
                dialog.dismiss();
                dialog.cancel();

                ArrayList<SingleAlloyItem> sortResult = sortBy(resultsArray, selectedSort);
                String output = "";
                for(int i = 0; i < sortResult.size(); i++) {
                    output += sortResult.get(i).getAlloyName() + " ";
                }
                testTV.setText(output);
            }
        });
    }

    private ArrayList<SingleAlloyItem> sortBy(ArrayList<SingleAlloyItem> singleAlloyItems, String selectedSort) {
        ArrayList<SingleAlloyItem> result = singleAlloyItems;
        for(int i = 0; i < result.size(); i++) {
            for(int j = 0; j < i; j++) {
                if(compare(result.get(i), result.get(j), selectedSort)) {
                    SingleAlloyItem temp = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, temp);
                }
            }
        }
        return result;
    }

    private boolean compare(SingleAlloyItem singleAlloyItem1, SingleAlloyItem singleAlloyItem2, String selectedSort) {
        Double data1 = null;
        Double data2 = null;
        if(selectedSort.equals("Density")) {
            data1 = singleAlloyItem1.getDensity();
            data2 = singleAlloyItem2.getDensity();
        } else if(selectedSort.equals("Thermal Conductivity")) {
            data1 = singleAlloyItem1.getThermalCon();
            data2 = singleAlloyItem2.getThermalCon();
        } else if(selectedSort.equals("Thermal Expansion")) {
            data1 = singleAlloyItem1.getThermalExpan();
            data2 = singleAlloyItem2.getThermalExpan();
        } else if(selectedSort.equals("Specific Heat")) {
            data1 = singleAlloyItem1.getSpecificHeat();
            data2 = singleAlloyItem2.getSpecificHeat();
        } else if(selectedSort.equals("Elastic Modulus")) {
            data1 = singleAlloyItem1.getElasticModu();
            data2 = singleAlloyItem2.getElasticModu();
        } else if(selectedSort.equals("Resistivity")) {
            data1 = singleAlloyItem1.getResistivity();
            data2 = singleAlloyItem2.getResistivity();
        } else if(selectedSort.equals("Poisson's Ratio")) {
            data1 = singleAlloyItem1.getPoissonsRatio();
            data2 = singleAlloyItem2.getPoissonsRatio();
        } else if(selectedSort.equals("Damping Index")) {
            data1 = singleAlloyItem1.getDampingIndex();
            data2 = singleAlloyItem2.getDampingIndex();
        } else if(selectedSort.equals("Fracture Toughness")) {
            data1 = singleAlloyItem1.getFractureToughness();
            data2 = singleAlloyItem2.getFractureToughness();
        }


        if((data1 == null && data2 == null)) {
            String name1, name2;
            name1 = singleAlloyItem1.getAlloyName();
            name2 = singleAlloyItem2.getAlloyName();
            int comp = name1.compareToIgnoreCase(name2);
            if(comp > 0)
                return true; // 1 > 2
            else if(comp < 0)
                return false; // 1 < 2
        } else if(data1 == null) {
            return false; // 1 < 2
        } else if(data2 == null) {
            return true; // 1 > 2
        } else {
            if(data1 > data2)
                return true; // 1 > 2
            else if(data1 < data2)
                return false; // 1 < 2
            else if(data1.equals(data2)) {
                String name1, name2;
                name1 = singleAlloyItem1.getAlloyName();
                name2 = singleAlloyItem2.getAlloyName();
                int comp = name1.compareToIgnoreCase(name2);
                if(comp > 0)
                    return true; // 1 > 2
                else if(comp < 0)
                    return false; // 1 < 2
            }
        }

        return false;
    }
}
