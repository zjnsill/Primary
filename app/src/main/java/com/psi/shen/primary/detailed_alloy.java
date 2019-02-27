package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class detailed_alloy extends AppCompatActivity {

    private signedUser currentUser;

    private TopBar detailedAlloyTopbar;
    private LinearLayout detailList;

    private Bundle alloy = new Bundle();
    private boolean modify = false;

    private String[] detailTitles = {"Name", "Introduction", "Mechanical Properties", "Thermal Properties", "Electrical Properties", "Otherwise Unclassified Properties", "Common Calculations", "Alloy Composition"};
    private String[][] detailItems = {{"Name"},
            {"Introduction"},
            {"Elastic (Young\'s, Tensile) Modulus", "Elongation at Break", "Fatigue Strength", "Poisson\'s Ratio", "Shear Modulus", "Shear Strength", "Tensile Strength: Ultimate (UTS)", "Tensile Strength: Yield (Proof)", "Brinell Hardness", "Compressive (Crushing) Strength", "Rockwell F Hardness", "Impact Strength: V-Notched Charpy", "Fracture Toughness"},
            {"Latent Heat of Fusion", "Maximum Temperature: Mechanical", "Melting Completion (Liquidus)", "Melting Onset (Solidus)", "Solidification (Pattern Maker\'s) Shrinkage", "Specific Heat Capacity", "Thermal Conductivity", "Thermal Expansion", "Brazing Temperature"},
            {"Electrical Conductivity: Equal Volume", "Electrical Conductivity: Equal Weight (Specific)"},
            {"Base Metal Price", "Density", "Embodied Carbon", "Embodied Energy", "Embodied Water"},
            {"Resilience: Ultimate (Unit Rupture Work)", "Resilience: Unit (Modulus of Resilience)", "Stiffness to Weight: Axial", "Stiffness to Weight: Bending", "Strength to Weight: Axial", "Strength to Weight: Bending", "Thermal Diffusivity", "Thermal Shock Resistance"},
            {"Mg", "Al", "Mn", "Si", "Zn", "Cu", "Ni", "Y", "Zr", "Li", "Fe", "Be", "Ca", "Ag", "Rare Elements", "Residuals"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_alloy);

        Intent intent = getIntent();
        alloy = intent.getBundleExtra("alloy");
        if(intent.hasExtra("modify")) {
            currentUser = intent.getParcelableExtra("user");
            modify = true;
        }

        setupUI();
    }

    private void setupUI() {
        detailedAlloyTopbar = findViewById(R.id.DetailedAlloyTopbar);
        detailedAlloyTopbar.setTitle(alloy.getString("Name"));
        detailedAlloyTopbar.setTitleSize(20);
        detailedAlloyTopbar.setTitleBold(true);
        if(modify) {
            detailedAlloyTopbar.setRightImg(R.drawable.ic_edit);
        }
        detailedAlloyTopbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                if(modify)
                    detailed_alloy.this.setResult(0);
                detailed_alloy.this.finish();
            }

            @Override
            public void rightListener() {
                if(modify) {
                    Intent intent = new Intent(detailed_alloy.this, CreateAlloy.class);
                    intent.putExtra("alloy", alloy);
                    intent.putExtra("user", currentUser);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, 3);
                }
            }
        });

        detailList = findViewById(R.id.detailList);

        for(int i = 1; i < detailTitles.length; i++) {
            CardView cardView = new CardView(this);
            CardView.LayoutParams cardViewLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
            cardViewLayoutParams.setMargins(5, 20, 5, 20);
            cardView.setLayoutParams(cardViewLayoutParams);
            cardView.setCardElevation(10);
            cardView.setRadius(15);

            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(15, 15, 15, 15);

            TextView title = new TextView(this);
            LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            titleLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            title.setLayoutParams(titleLayoutParams);
            title.setText(detailTitles[i]);
            title.setTextSize(25);
            title.setGravity(Gravity.CENTER);
            title.getPaint().setFakeBoldText(true);
            layout.addView(title);

            boolean flag = false;
            for(int j = 0; j < detailItems[i].length; j++) {
                if(alloy.containsKey(detailItems[i][j])) {
                    flag = true;

                    LinearLayout linearLayout = new LinearLayout(this);
                    LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayoutLayoutParams.setMargins(15, 3, 15, 3);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.setLayoutParams(linearLayoutLayoutParams);

                    if(!detailTitles[i].equals("Introduction")) {
                        TextView detailItemTV = new TextView(this);
                        LinearLayout.LayoutParams detailItemTVLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        detailItemTVLayoutParams.setMarginStart(5);
                        detailItemTV.setText(detailItems[i][j]);
                        detailItemTV.setTextSize(15);
                        detailItemTV.setLayoutParams(detailItemTVLayoutParams);
                        linearLayout.addView(detailItemTV);
                    }

                    TextView valueTV = new TextView(this);
                    LinearLayout.LayoutParams valueTVLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    valueTVLayoutParams.setMarginStart(5);
                    valueTVLayoutParams.setMarginEnd(5);
                    valueTV.setLayoutParams(valueTVLayoutParams);
                    if(!detailTitles[i].equals("Introduction"))
                        valueTV.setGravity(Gravity.END);
                    valueTV.setTextSize(15);
                    valueTV.setText(alloy.getString(detailItems[i][j]));
                    linearLayout.addView(valueTV);

                    layout.addView(linearLayout);
                }
            }

            if(!flag) {
                continue;
            }

            cardView.addView(layout);

            detailList.addView(cardView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) {
            detailed_alloy.this.setResult(1);
            detailed_alloy.this.finish();
        }
    }
}
