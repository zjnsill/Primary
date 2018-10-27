package com.psi.shen.primary;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class SortAlloys extends AppCompatActivity {
    private ArrayList<SingleAlloyItem> resultsArray = new ArrayList<>();

    private EditText SortET;
    private TopBar topBar;

    private BarChart barChart;

    private String selectedSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_alloys);

        Intent fromResultInterface = getIntent();
        resultsArray = fromResultInterface.getParcelableArrayListExtra("resultsArray");

        barChart = findViewById(R.id.barChart);
        SortET = findViewById(R.id.SortET);
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

        setChart();
    }

    private void setChart() {
        barChart.setNoDataText("No options selected！");
        barChart.setDescription(null);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);
        barChart.setFitBars(true);
        barChart.setScaleYEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(7f);
        xAxis.setGranularity(1f);
        //xAxis.setValueFormatter(new MyXFormatter());

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setDrawGridLines(true);
        yAxis.setTextColor(Color.BLACK);

        barChart.getAxisRight().setEnabled(false);
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
    }

    private void setChartData(final ArrayList<SingleAlloyItem> singleAlloyItems) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        int n = 0;

        for(int i = 0; i < singleAlloyItems.size(); i++) {
            if(selectedSort.equals("Density")) {
                if(singleAlloyItems.get(i).getDensity() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getDensity().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Thermal Conductivity")) {
                if(singleAlloyItems.get(i).getThermalCon() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getThermalCon().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Thermal Expansion")) {
                if(singleAlloyItems.get(i).getThermalExpan() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getThermalExpan().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Specific Heat")) {
                if(singleAlloyItems.get(i).getSpecificHeat() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getSpecificHeat().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Elastic Modulus")) {
                if(singleAlloyItems.get(i).getElasticModu() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getElasticModu().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Resistivity")) {
                if(singleAlloyItems.get(i).getResistivity() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getResistivity().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Poisson's Ratio")) {
                if(singleAlloyItems.get(i).getPoissonsRatio() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getPoissonsRatio().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Damping Index")) {
                if(singleAlloyItems.get(i).getDampingIndex() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getDampingIndex().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            } else if(selectedSort.equals("Fracture Toughness")) {
                if(singleAlloyItems.get(i).getFractureToughness() != null) {
                    barEntries.add(new BarEntry(i, singleAlloyItems.get(i).getFractureToughness().floatValue()));
                    names.add(singleAlloyItems.get(i).getAlloyName());
                    n++;
                }
            }
        }

        barChart.getXAxis().setLabelCount(n);
        barChart.getXAxis().setValueFormatter(new MyXFormatter(names));

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                SingleAlloyItem selectedAlloyItem = singleAlloyItems.get((int)e.getX());
                Intent jumpToDetail = new Intent(SortAlloys.this, detaied_alloy.class);
                Bundle item = new Bundle();
                item.putParcelable("clickedItem", selectedAlloyItem);
                jumpToDetail.putExtras(item);
                jumpToDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(jumpToDetail);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setDrawValues(true);

        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
        iBarDataSets.add(barDataSet);

        BarData barData = new BarData(iBarDataSets);
        barChart.setData(barData);
        barChart.notifyDataSetChanged();
        //barChart.invalidate();
        barChart.animateY(1000);
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
                setChartData(sortResult);
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

    public class MyXFormatter implements IAxisValueFormatter {
        private ArrayList<String> mValues;

        public MyXFormatter(ArrayList<String > values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if((int)value >= 0 && (int)value <mValues.size())
                return mValues.get((int)value);
            else
                return "";
        }
    }

}