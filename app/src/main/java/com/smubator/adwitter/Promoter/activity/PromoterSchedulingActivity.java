package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.R;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.volly.DwitterApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PromoterSchedulingActivity extends AppCompatActivity implements View.OnClickListener {
    public String Status_success;
    private RelativeLayout rlArrow, rlPublishingSchedule, rlMaxPostQuantity;
    private ImageView ivArrowRight;
    private MyCustomTextView title;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_scheduling);
        initViews();
        initListeners();
    }

    private void initListeners() {
        ivArrowRight.setOnClickListener(this);
        rlPublishingSchedule.setOnClickListener(this);
        rlMaxPostQuantity.setOnClickListener(this);
        rlArrow.setOnClickListener(this);
    }

    private void initViews() {
        rlArrow = findViewById(R.id.rlArrow);
        title = findViewById(R.id.title);
        rlPublishingSchedule = findViewById(R.id.rlPublishingSchedule);
        rlMaxPostQuantity = findViewById(R.id.rlMaxPostQuantity);
        ivArrowRight = findViewById(R.id.ivArrowRight);
        title.setText(getResources().getString(R.string.schedulingSettings));
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
                updateUser();
                DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
                break;
            case R.id.rlArrow:
                onBackPressed();
                break;
            case R.id.rlPublishingSchedule:

                Intent i = new Intent(PromoterSchedulingActivity.this, SchedulingActivity.class);
                startActivity(i);
                break;

            case R.id.rlMaxPostQuantity:

                Intent intent = new Intent(PromoterSchedulingActivity.this, MaxPostQuantity.class);
                startActivity(intent);
                break;
        }

    }

    public void updateUser() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Status_success = jsonObject.optString("status_text");

                    JSONObject data = jsonObject.optJSONObject("data");
                    Log.i("", "");
                    DwitterApplication.getInstance().saveString("id", data.optString("id"));
                    DwitterApplication.getInstance().saveString("account_type", data.optString("account_type"));
                    DwitterApplication.getInstance().saveString("twitter_id", data.optString("twitter_id"));
                    DwitterApplication.getInstance().saveString("mUserName", data.optString("fname"));
                    DwitterApplication.getInstance().saveString("lname", data.optString("lname"));
                    DwitterApplication.getInstance().saveString("gender", data.optString("gender"));
                    DwitterApplication.getInstance().saveString("email", data.optString("email"));
                    DwitterApplication.getInstance().saveString("phone", data.optString("phone"));
                    DwitterApplication.getInstance().saveString("country", data.optString("country"));
                    DwitterApplication.getInstance().saveString("state", data.optString("state"));
                    DwitterApplication.getInstance().saveString("city", data.optString("city"));
                    DwitterApplication.getInstance().saveString("language", data.optString("language"));
                    DwitterApplication.getInstance().saveString("category", data.optString("category"));
                    DwitterApplication.getInstance().saveString("mUserImage", data.optString("profile_image"));
                    DwitterApplication.getInstance().saveString("joining_date", data.optString("joining_date"));
                    DwitterApplication.getInstance().saveString("status", data.optString("status"));

                    Intent i1 = new Intent(PromoterSchedulingActivity.this, MainActivity.class);
                    startActivity(i1);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PromoterSchedulingActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.Update_User);
                MyData.put("user_id", DwitterApplication.getInstance().useString("user_id"));
                MyData.put("account_type", DwitterApplication.getInstance().useString("account_type"));
                MyData.put("gender", DwitterApplication.getInstance().useString("gender"));
                MyData.put("country", DwitterApplication.getInstance().useString("country_name"));
                MyData.put("state", DwitterApplication.getInstance().useString("state_name"));
                MyData.put("city", DwitterApplication.getInstance().useString("city_name"));
                MyData.put("age", DwitterApplication.getInstance().useString("age"));
                MyData.put("language", DwitterApplication.getInstance().useString("language"));
                MyData.put("category", "1,2");// Static for now

                return MyData;
            }
        };
    }
}