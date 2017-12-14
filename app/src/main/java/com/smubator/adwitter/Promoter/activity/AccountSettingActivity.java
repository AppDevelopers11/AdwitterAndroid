package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.Models.TopicTargetingModel;
import com.smubator.adwitter.R;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.volly.DwitterApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlArrow;
    private ImageView ivArrowRight;
    private MyCustomTextView title;
    private RelativeLayout rlSelectLocation, rlTopicTargetting;
    private TextView tvTopicTarget, tvLocation;
    private Spinner spAge, spGender, spLanguage;
    private StringRequest stringRequest;
    private String country_name, state_name, city_name;
    private String age, gender, language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        initViews();
        initListeners();
        spinner();
    }

    private void initListeners() {
        ivArrowRight.setOnClickListener(this);
        rlArrow.setOnClickListener(this);
        rlSelectLocation.setOnClickListener(this);
        rlTopicTargetting.setOnClickListener(this);
    }

    private void initViews() {
        rlArrow = findViewById(R.id.rlArrow);
        rlSelectLocation = findViewById(R.id.rlSelectLocation);
        ivArrowRight = findViewById(R.id.ivArrowRight);
        tvLocation = findViewById(R.id.tvLocation);
        title = findViewById(R.id.title);
        title.setText(getResources().getString(R.string.accountsettings));

        spAge = findViewById(R.id.spAge);
        rlTopicTargetting = findViewById(R.id.rlTopicTargetting);
        spGender = findViewById(R.id.spGender);
        spLanguage = findViewById(R.id.spLanguage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivArrowRight:
                DwitterApplication.getInstance().saveString("country_name", country_name);
                DwitterApplication.getInstance().saveString("state_name", state_name);
                DwitterApplication.getInstance().saveString("city_name", city_name);
                DwitterApplication.getInstance().saveString("age", age);
                DwitterApplication.getInstance().saveString("gender", gender);
                DwitterApplication.getInstance().saveString("language", language);

                Intent i = new Intent(AccountSettingActivity.this, PromoterSchedulingActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                break;
            case R.id.rlArrow:
                onBackPressed();
                break;
            case R.id.rlSelectLocation:
                Intent selectLocation = new Intent(AccountSettingActivity.this, SelectLocationActivity.class);
                startActivityForResult(selectLocation, 1);
                break;
            case R.id.rlTopicTargetting:
                // Toast.makeText(this, "ad", Toast.LENGTH_SHORT).show();
                topicTargetting();
                DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                country_name = data.getStringExtra("country_name");
                state_name = data.getStringExtra("state_name");
                city_name = data.getStringExtra("city_name");
                if (country_name.equalsIgnoreCase("") && city_name.equalsIgnoreCase("")) {
                    tvLocation.setText("");
                } else {
                    tvLocation.setText(country_name + "/" + city_name);

                }
            }
        }

        Log.i("", "");
    }

    public void spinner() {
        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this,
                R.array.spAge, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAge.setAdapter(adapterAge);
        spAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Object o = parent.getSelectedItem();
                age = String.valueOf(o);

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,
                R.array.spGender, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGender.setAdapter(adapterGender);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Object o = parent.getSelectedItem();
                gender = String.valueOf(o);

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(this,
                R.array.spLanguage, android.R.layout.simple_spinner_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLanguage.setAdapter(adapterLanguage);
        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Object o = parent.getSelectedItem();
                language = String.valueOf(o);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }


    public void topicTargetting() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Intent i = new Intent(AccountSettingActivity.this, IntroductionActivity.class);
                    // startActivity(i);
                    ArrayList<TopicTargetingModel> ttModel = new ArrayList<>();

                    // String s="\uFEFF\uFEFF{\"category\":[{\"id\":\"4\",\"name\":\"home\"},{\"id\":\"3\",\"name\":\"foods\"},{\"id\":\"2\",\"name\":\"beauty\"},{\"id\":\"1\",\"name\":\"baking & finance\"}]}"
                    response = response.replace("\uFEFF", "");

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        TopicTargetingModel topicTargetingModel = new TopicTargetingModel();
                        topicTargetingModel.setId(obj.optInt("id"));
                        topicTargetingModel.setName(obj.optString("name"));
                        ttModel.add(topicTargetingModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AccountSettingActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.TopicTargetting); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
    }
}
