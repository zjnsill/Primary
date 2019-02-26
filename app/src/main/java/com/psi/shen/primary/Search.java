package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

public class Search extends AppCompatActivity {

    private signedUser currentUser;

    private TopBar searchTopBar;
    private RoundRectBtn searchBtn;

    private LinearLayout searchList;

    private int[][] spaceIds;
    private int[][] minIds;
    private int[][] maxIds;
    private int NameId;

    private Bundle inquiry = new Bundle();

    private String[] searchTitles = {"Name", "Mechanical Properties", "Thermal Properties", "Electrical Properties", "Otherwise Unclassified Properties", "Common Calculations", "Alloy Composition"};
    private String[][] searchItems = {{"Name"},
            {"Elastic (Young\'s, Tensile) Modulus", "Elongation at Break", "Fatigue Strength", "Poisson\'s Ratio", "Shear Modulus", "Shear Strength", "Tensile Strength: Ultimate (UTS)", "Tensile Strength: Yield (Proof)", "Brinell Hardness", "Compressive (Crushing) Strength", "Rockwell F Hardness", "Impact Strength: V-Notched Charpy", "Fracture Toughness"},
            {"Latent Heat of Fusion", "Maximum Temperature: Mechanical", "Melting Completion (Liquidus)", "Melting Onset (Solidus)", "Solidification (Pattern Maker\'s) Shrinkage", "Specific Heat Capacity", "Thermal Conductivity", "Thermal Expansion", "Brazing Temperature"},
            {"Electrical Conductivity: Equal Volume", "Electrical Conductivity: Equal Weight (Specific)"},
            {"Base Metal Price", "Density", "Embodied Carbon", "Embodied Energy", "Embodied Water"},
            {"Resilience: Ultimate (Unit Rupture Work)", "Resilience: Unit (Modulus of Resilience)", "Stiffness to Weight: Axial", "Stiffness to Weight: Bending", "Strength to Weight: Axial", "Strength to Weight: Bending", "Thermal Diffusivity", "Thermal Shock Resistance"},
            {"Mg", "Al", "Mn", "Si", "Zn", "Cu", "Ni", "Y", "Zr", "Li", "Fe", "Be", "Ca", "Ag", "Rare Elements", "Residuals"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent fromMainInterface = getIntent();
        currentUser = fromMainInterface.getParcelableExtra("user");

        setupIds();
        setupUI();

        searchBtn.setBtnOnClickListener(new RoundRectBtn.BtnOnClickListenr(){
            @Override
            public void BtnOnClick(){
                gatherInput();
                Intent intent = new Intent(Search.this, SearchResults.class);
                intent.putExtra("inquiry", inquiry);
                intent.putExtra("user", currentUser);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void setupIds() {
        int tag = 1000;
        spaceIds = new int[searchTitles.length][];
        minIds = new int[searchTitles.length][];
        maxIds = new int[searchTitles.length][];
        for(int i = 1; i < searchTitles.length; i++) {
            spaceIds[i] = new int[searchItems[i].length];
            for(int j = 0; j < searchItems[i].length; j++) {
                spaceIds[i][j] = tag++;
            }
        }
        tag = 2000;
        for(int i = 1; i < searchTitles.length; i++) {
            minIds[i] = new int[searchItems[i].length];
            for(int j = 0; j < searchItems[i].length; j++) {
                minIds[i][j] = tag++;
            }
        }
        tag = 3000;
        for(int i = 1; i < searchTitles.length; i++) {
            maxIds[i] = new int[searchItems[i].length];
            for(int j = 0; j < searchItems[i].length; j++) {
                maxIds[i][j] = tag++;
            }
        }
        NameId = 4000;
    }

    private void setupUI() {
        searchBtn = findViewById(R.id.searchBtn);
        searchTopBar = findViewById(R.id.searchTopBar);
        searchTopBar.setLeftAndRightListener(new TopBar.LeftAndRightListener(){
            @Override
            public void rightListener(){
                clearAll();
            }
            @Override
            public void leftListener(){
                Search.this.finish();
            }
        });


        searchList = findViewById(R.id.searchList);

        for(int i = 0; i < searchTitles.length; i++) {
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
            title.setText(searchTitles[i]);
            title.setTextSize(25);
            title.setGravity(Gravity.CENTER);
            title.getPaint().setFakeBoldText(true);
            layout.addView(title);

            for (int j = 0; j < searchItems[i].length; j++) {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayoutLayoutParams.setMargins(15, 3, 15, 3);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(linearLayoutLayoutParams);

                TextView searchItemTV = new TextView(this);
                LinearLayout.LayoutParams searchItemTVLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                searchItemTVLayoutParams.setMarginStart(5);
                searchItemTV.setText(searchItems[i][j]);
                searchItemTV.setTextSize(15);
                searchItemTV.setLayoutParams(searchItemTVLayoutParams);
                linearLayout.addView(searchItemTV);

                RelativeLayout inputRL = new RelativeLayout(this);
                RelativeLayout.LayoutParams inputRLLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                inputRLLayoutParams.setMarginStart(5);
                inputRLLayoutParams.setMarginEnd(5);
                inputRL.setLayoutParams(inputRLLayoutParams);

                if(i != 0) {
                    Space space = new Space(this);
                    RelativeLayout.LayoutParams spaceLayoutParams = new RelativeLayout.LayoutParams(20, 1);
                    spaceLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    space.setId(spaceIds[i][j]);
                    space.setLayoutParams(spaceLayoutParams);
                    inputRL.addView(space);

                    EditText minET = new EditText(this);
                    RelativeLayout.LayoutParams minETLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    minETLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                    minETLayoutParams.addRule(RelativeLayout.START_OF, spaceIds[i][j]);
                    minET.setLayoutParams(minETLayoutParams);
                    minET.setId(minIds[i][j]);
                    minET.setHint("Min");
                    minET.setTextSize(15);
                    minET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    inputRL.addView(minET);

                    EditText maxET = new EditText(this);
                    RelativeLayout.LayoutParams maxETLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    maxETLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                    maxETLayoutParams.addRule(RelativeLayout.END_OF, spaceIds[i][j]);
                    maxET.setLayoutParams(maxETLayoutParams);
                    maxET.setId(maxIds[i][j]);
                    maxET.setHint("Max");
                    maxET.setTextSize(15);
                    maxET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    inputRL.addView(maxET);
                } else {
                    EditText editText = new EditText(this);
                    RelativeLayout.LayoutParams editTextLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    editTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                    editText.setLayoutParams(editTextLayoutParams);
                    editText.setId(NameId);
                    editText.setHint("Name");
                    editText.setTextSize(15);
                    inputRL.addView(editText);
                }

                linearLayout.addView(inputRL);

                layout.addView(linearLayout);
            }

            cardView.addView(layout);

            searchList.addView(cardView);
        }
    }

    private void clearAll() {
        EditText editText = findViewById(NameId);
        editText.setText("");
        for(int i = 1; i < searchTitles.length; i++) {
            for(int j = 0; j < searchItems[i].length; j++) {
                editText = findViewById(minIds[i][j]);
                editText.setText("");
                editText = findViewById(maxIds[i][j]);
                editText.setText("");
            }
        }
    }

    private void gatherInput() {
        inquiry = new Bundle();
        EditText editText = findViewById(NameId);
        if(editText.getText().length() != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("Name", editText.getText().toString());
            inquiry.putBundle("Name", bundle);
        }

        for(int i = 1; i < searchTitles.length; i++) {
            for(int j = 0; j < searchItems[i].length; j++) {
                Bundle bundle = new Bundle();
                editText = findViewById(minIds[i][j]);
                if(editText.getText().length() != 0) {
                    bundle.putString("Min", editText.getText().toString());
                }
                editText = findViewById(maxIds[i][j]);
                if(editText.getText().length() != 0) {
                    bundle.putString("Max", editText.getText().toString());
                }
                if(bundle.size() != 0) {
                    inquiry.putBundle(searchItems[i][j], bundle);
                }
            }
        }
    }
}