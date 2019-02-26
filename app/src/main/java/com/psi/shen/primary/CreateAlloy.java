package com.psi.shen.primary;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateAlloy extends AppCompatActivity {

    private signedUser currentUser;

    private TopBar createTopbar;
    private RoundRectBtn finishBtn;
    private LinearLayout createList;

    private int[][] editTextIds;
    private int[] minIds;
    private int[] maxIds;
    private int[] spaceIds;

    private Bundle alloy;

    private String[] createTitles = {"Name", "Mechanical Properties", "Thermal Properties", "Electrical Properties", "Otherwise Unclassified Properties", "Common Calculations", "Alloy Composition"};
    private String[][] createItems = {{"Name"},
            {"Elastic (Young\'s, Tensile) Modulus", "Elongation at Break", "Fatigue Strength", "Poisson\'s Ratio", "Shear Modulus", "Shear Strength", "Tensile Strength: Ultimate (UTS)", "Tensile Strength: Yield (Proof)", "Brinell Hardness", "Compressive (Crushing) Strength", "Rockwell F Hardness", "Impact Strength: V-Notched Charpy", "Fracture Toughness"},
            {"Latent Heat of Fusion", "Maximum Temperature: Mechanical", "Melting Completion (Liquidus)", "Melting Onset (Solidus)", "Solidification (Pattern Maker\'s) Shrinkage", "Specific Heat Capacity", "Thermal Conductivity", "Thermal Expansion", "Brazing Temperature"},
            {"Electrical Conductivity: Equal Volume", "Electrical Conductivity: Equal Weight (Specific)"},
            {"Base Metal Price", "Density", "Embodied Carbon", "Embodied Energy", "Embodied Water"},
            {"Resilience: Ultimate (Unit Rupture Work)", "Resilience: Unit (Modulus of Resilience)", "Stiffness to Weight: Axial", "Stiffness to Weight: Bending", "Strength to Weight: Axial", "Strength to Weight: Bending", "Thermal Diffusivity", "Thermal Shock Resistance"},
            {"Mg", "Al", "Mn", "Si", "Zn", "Cu", "Ni", "Y", "Zr", "Li", "Fe", "Be", "Ca", "Ag", "Rare Elements", "Residuals"}};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if(message.what == 0) { // success
                Toast.makeText(CreateAlloy.this, "success", Toast.LENGTH_SHORT).show();
                CreateAlloy.this.setResult(1);
                CreateAlloy.this.finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alloy);


        Intent intent = getIntent();
        currentUser = intent.getParcelableExtra("user");

        setupIds();
        setupUI();
    }

    private void setupIds() {
        int tag = 1000;
        editTextIds = new int[createTitles.length - 1][];
        minIds = new int[createItems[createTitles.length - 1].length];
        maxIds = new int[createItems[createTitles.length - 1].length];
        spaceIds = new int[createItems[createTitles.length - 1].length];
        for(int i = 0; i < createTitles.length - 1; i++) {
            editTextIds[i] = new int[createItems[i].length];
            for(int j = 0; j < createItems[i].length; j++) {
                editTextIds[i][j] = tag++;
            }
        }

        tag = 2000;
        for(int i = 0; i < createItems[createTitles.length - 1].length; i++) {
            minIds[i] = tag++;
        }

        tag = 3000;
        for(int i = 0; i < createItems[createTitles.length - 1].length; i++) {
            maxIds[i] = tag++;
        }

        tag = 4000;
        for(int i = 0; i < createItems[createTitles.length - 1].length; i++) {
            spaceIds[i] = tag++;
        }
    }

    private void setupUI() {
        createTopbar = findViewById(R.id.createTopBar);
        createTopbar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                CreateAlloy.this.setResult(0);
                CreateAlloy.this.finish();
            }

            @Override
            public void rightListener() {
                clearAll();
            }
        });

        finishBtn = findViewById(R.id.finishBtn);
        finishBtn.setBtnOnClickListener(new RoundRectBtn.BtnOnClickListenr() {
            @Override
            public void BtnOnClick() {
                if(gatherInput()) {
                    //Log.i("MainActivity", "success");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            createAlloy();
                        }
                    }).start();
                }
            }
        });

        createList = findViewById(R.id.createList);

        for(int i = 0; i < createTitles.length; i++) {
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
            title.setText(createTitles[i]);
            title.setTextSize(25);
            title.setGravity(Gravity.CENTER);
            title.getPaint().setFakeBoldText(true);
            layout.addView(title);

            for(int j = 0; j < createItems[i].length; j++) {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayoutLayoutParams.setMargins(15, 3, 15, 3);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(linearLayoutLayoutParams);

                TextView searchItemTV = new TextView(this);
                LinearLayout.LayoutParams searchItemTVLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                searchItemTVLayoutParams.setMarginStart(5);
                searchItemTV.setText(createItems[i][j]);
                searchItemTV.setTextSize(15);
                searchItemTV.setLayoutParams(searchItemTVLayoutParams);
                linearLayout.addView(searchItemTV);

                if(i != createTitles.length - 1) {
                    EditText editText = new EditText(this);
                    LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //RelativeLayout.LayoutParams editTextLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    //editTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                    editText.setLayoutParams(editTextLayoutParams);
                    editText.setId(editTextIds[i][j]);
                    editText.setTextSize(15);
                    if(i != 0)
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    linearLayout.addView(editText);
                } else {
                    RelativeLayout inputRL = new RelativeLayout(this);
                    RelativeLayout.LayoutParams inputRLLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    inputRLLayoutParams.setMarginStart(5);
                    inputRLLayoutParams.setMarginEnd(5);
                    inputRL.setLayoutParams(inputRLLayoutParams);

                    Space space = new Space(this);
                    RelativeLayout.LayoutParams spaceLayoutParams = new RelativeLayout.LayoutParams(20, 1);
                    spaceLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    space.setId(spaceIds[j]);
                    space.setLayoutParams(spaceLayoutParams);
                    inputRL.addView(space);

                    EditText minET = new EditText(this);
                    RelativeLayout.LayoutParams minETLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    minETLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                    minETLayoutParams.addRule(RelativeLayout.START_OF, spaceIds[j]);
                    minET.setLayoutParams(minETLayoutParams);
                    minET.setId(minIds[j]);
                    minET.setHint("Min");
                    minET.setTextSize(15);
                    minET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    inputRL.addView(minET);

                    EditText maxET = new EditText(this);
                    RelativeLayout.LayoutParams maxETLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    maxETLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                    maxETLayoutParams.addRule(RelativeLayout.END_OF, spaceIds[j]);
                    maxET.setLayoutParams(maxETLayoutParams);
                    maxET.setId(maxIds[j]);
                    maxET.setHint("Max");
                    maxET.setTextSize(15);
                    maxET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    inputRL.addView(maxET);

                    linearLayout.addView(inputRL);
                }

                layout.addView(linearLayout);
            }

            cardView.addView(layout);
            createList.addView(cardView);
        }
    }

    private void clearAll() {
        EditText editText;
        for(int i = 0; i < createTitles.length - 1; i++) {
            for(int j = 0; j < createItems[i].length; j++) {
                editText = findViewById(editTextIds[i][j]);
                editText.setText("");
            }
        }
        for(int i = 0; i < createItems[createTitles.length - 1].length; i++) {
            editText = findViewById(minIds[i]);
            editText.setText("");
            editText = findViewById(maxIds[i]);
            editText.setText("");
        }
    }

    private boolean gatherInput() {
        alloy = new Bundle();
        EditText editText;
        for(int i = 0; i < createTitles.length - 1; i++) {
            for(int j = 0; j < createItems[i].length; j++) {
                editText = findViewById(editTextIds[i][j]);
                if(createItems[i][j].equals("Name") && editText.getText().length() == 0) {
                    Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(editText.getText().length() != 0) {
                    alloy.putString(createItems[i][j], editText.getText().toString());
                }
            }
        }
        for(int i = 0; i < createItems[createTitles.length - 1].length; i++) {
            String min = "", max = "";
            editText = findViewById(minIds[i]);
            if(editText.getText().length() != 0) {
                min = editText.getText().toString();
            }
            editText = findViewById(maxIds[i]);
            if(editText.getText().length() != 0) {
                if(min.equals("")) {
                    Toast.makeText(this, "Please enter the min value", Toast.LENGTH_SHORT).show();
                    return false;
                }
                max = editText.getText().toString();
            }
            if(max.equals("") && !min.equals("")) {
                Toast.makeText(this, "Please enter the max value", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(!min.equals("") && !max.equals(""))
                alloy.putString(createItems[createTitles.length - 1][i], min + " to " + max);
        }
        return true;
    }

    private void createAlloy() {
        String path = "http://118.25.122.232/android_connect/customize.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", currentUser.getPhone());
            for(String key : alloy.keySet()) {
                formBody.add(key, alloy.getString(key));
            }
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                if(jsonObject.getInt("errorCode") == 0) {
                    handler.sendEmptyMessage(0);
                }
            } else {
                Log.e("MainActivity", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
