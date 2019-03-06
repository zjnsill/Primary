package com.psi.shen.primary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.transition.Explode;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends AppCompatActivity {

    private int startX, startY;

    private signedUser currentUser;

    private TopBar searchTopBar;
    private RoundRectBtn searchBtn;

    private LinearLayout searchList;
    private LinearLayout alloyType;

    private int[][] spaceIds;
    private int[][] minIds;
    private int[][] maxIds;
    private int[] rbSpaceIds;
    private int[] typeIds;
    private int NameId;

    private String selectedType;

    private Bundle inquiry = new Bundle();

    private String[] searchAlloyType = {"Magnesium Alloy", "Aluminum Alloy", "Copper Alloy", "Iron Alloy", "Cobalt Alloy"};
    private String[] searchTitles = {"Name", "Mechanical Properties", "Thermal Properties", "Electrical Properties", "Otherwise Unclassified Properties", "Common Calculations", "Alloy Composition"};
    private String[][] searchItems = {{"Name"},
            {"Elastic (Young\'s, Tensile) Modulus", "Elongation at Break", "Fatigue Strength", "Poisson\'s Ratio", "Shear Modulus", "Shear Strength", "Tensile Strength: Ultimate (UTS)", "Tensile Strength: Yield (Proof)", "Brinell Hardness", "Compressive (Crushing) Strength", "Rockwell F Hardness", "Rockwell B Hardness", "Rockwell C Hardness", "Rockwell Superficial 30T Hardness", "Impact Strength: V-Notched Charpy", "Impact Strength: U-Notched Charpy", "Fracture Toughness", "Reduction in Area", "Flexural Strength"},
            {"Latent Heat of Fusion", "Maximum Temperature: Mechanical", "Melting Completion (Liquidus)", "Melting Onset (Solidus)", "Solidification (Pattern Maker\'s) Shrinkage", "Specific Heat Capacity", "Thermal Conductivity", "Thermal Expansion", "Brazing Temperature", "Maximum Temperature: Corrosion", "Curie Temperature"},
            {"Electrical Conductivity: Equal Volume", "Electrical Conductivity: Equal Weight (Specific)"},
            {"Base Metal Price", "Density", "Embodied Carbon", "Embodied Energy", "Embodied Water", "Calomel Potential"},
            {"Resilience: Ultimate (Unit Rupture Work)", "Resilience: Unit (Modulus of Resilience)", "Stiffness to Weight: Axial", "Stiffness to Weight: Bending", "Strength to Weight: Axial", "Strength to Weight: Bending", "Thermal Diffusivity", "Thermal Shock Resistance", "PREN (Pitting Resistance)"},
            {"Mg", "Al", "Mn", "Si", "Zn", "Cu", "Ni", "Y", "Zr", "Li", "Fe", "Be", "Ca", "Ag", "V", "Ti", "Ga", "B", "Cr", "Pb", "Sn", "Bi", "Co", "Sb", "S", "P", "As", "Cd", "C", "Nb", "Se", "Te", "O", "Mo", "N", "W", "Ta", "Ce", "La", "Rare Elements", "Residuals"}
    };
    private String[][] units = {{},
            {"GPa", "%", "MPa", "", "GPa", "MPa", "MPa", "MPa", "", "MPa", "", "", "", "", "J", "J", "MPa-m1/2", "%", "MPa"},
            {"J/g", "°C", "°C", "°C", "%", "J/kg-K", "W/m-K", "µm/m-K", "°C", "°C", "°C"},
            {"% IACS", "% IACS"},
            {"% relative", "g/cm3", "kg CO2/kg material", "MJ/kg", "L/kg", "mV"},
            {"MJ/m3", "kJ/m3", "points", "points", "points", "points", "m2/s", "points", ""},
            {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setEnterTransition(explode);

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
                //Toast.makeText(Search.this, selectedType, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupIds() {
        int tag = 1000;
        spaceIds = new int[searchTitles.length][];
        minIds = new int[searchTitles.length][];
        maxIds = new int[searchTitles.length][];
        rbSpaceIds = new int[(searchAlloyType.length + 1) / 2];
        typeIds = new int[searchAlloyType.length];
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
        tag = 4000;
        for(int i = 0; i < (searchAlloyType.length + 1) / 2; i++) {
            rbSpaceIds[i] = tag++;
        }
        tag = 5000;
        for(int i = 0; i < searchAlloyType.length; i++) {
            typeIds[i] = tag++;
        }
        NameId = 6000;
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

        alloyType = findViewById(R.id.searchType);

        for(int i = 0; i < (searchAlloyType.length + 1) / 2; i++) {
            RelativeLayout relativeLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(layoutParams);

            Space space = new Space(this);
            RelativeLayout.LayoutParams spaceLayoutParams = new RelativeLayout.LayoutParams(1, RelativeLayout.LayoutParams.WRAP_CONTENT);
            spaceLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            space.setLayoutParams(spaceLayoutParams);
            space.setId(rbSpaceIds[i]);
            relativeLayout.addView(space);

            RadioButton radioButton1 = new RadioButton(this);
            RelativeLayout.LayoutParams rbLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rbLayoutParams1.addRule(RelativeLayout.START_OF, rbSpaceIds[i]);
            radioButton1.setLayoutParams(rbLayoutParams1);
            radioButton1.setText(searchAlloyType[2 * i]);
            radioButton1.setId(typeIds[2 * i]);
            if(i == 0) {
                radioButton1.setChecked(true);
                selectedType = radioButton1.getText().toString();
            }
            relativeLayout.addView(radioButton1);

            if(2 * i + 1 < searchAlloyType.length) {
                RadioButton radioButton2 = new RadioButton(this);
                RelativeLayout.LayoutParams rbLayoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rbLayoutParams2.addRule(RelativeLayout.END_OF, rbSpaceIds[i]);
                radioButton2.setLayoutParams(rbLayoutParams2);
                radioButton2.setText(searchAlloyType[2 * i + 1]);
                radioButton2.setId(typeIds[2 * i + 1]);
                relativeLayout.addView(radioButton2);
            }

            alloyType.addView(relativeLayout);
        }

        for(int i = 0; i < searchAlloyType.length; i++) {
            final RadioButton radioButton = findViewById(typeIds[i]);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        for(int j = 0; j < searchAlloyType.length; j++) {
                            if(radioButton.getId() != typeIds[j]) {
                                RadioButton rb = findViewById(typeIds[j]);
                                rb.setChecked(false);
                            }
                        }
                        selectedType = radioButton.getText().toString();
                    }
                }
            });
        }

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

                if(i != 0) {
                    TextView searchItemTV = new TextView(this);
                    LinearLayout.LayoutParams searchItemTVLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    searchItemTVLayoutParams.setMarginStart(5);
                    if(searchTitles[i].equals("Alloy Composition"))
                        searchItemTV.setText(searchItems[i][j] + " (%)");
                    else if(!units[i][j].equals(""))
                        searchItemTV.setText(searchItems[i][j] + " (" + units[i][j] + ")");
                    else
                        searchItemTV.setText(searchItems[i][j]);
                    searchItemTV.setTextSize(15);
                    searchItemTV.setLayoutParams(searchItemTVLayoutParams);
                    linearLayout.addView(searchItemTV);
                }

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
        Bundle type = new Bundle();
        switch (selectedType.split(" ", 2)[0]) {
            case "Magnesium":
                type.putString("Type", "Mg");
                break;
            case "Aluminum":
                type.putString("Type", "Al");
                break;
            case "Copper":
                type.putString("Type", "Cu");
                break;
            case "Iron":
                type.putString("Type", "Fe");
                break;
            case "Cobalt":
                type.putString("Type", "Co");
                break;
        }
        inquiry.putBundle("Type", type);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int)event.getRawX();
                startY = (int)event.getRawY();
            case MotionEvent.ACTION_MOVE:
                int endX = (int)event.getRawX();
                int endY = (int)event.getRawY();
                if(Math.abs(endX - startX) > Math.abs(endY - startY)) {
                    if(endX > startX) {
                        this.finish();
                    }
                }
        }
        return super.dispatchTouchEvent(event);
    }
}