package com.smubator.adwitter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.smubator.adwitter.CountryPicker.Country;
import com.smubator.adwitter.CountryPicker.CountryPicker;
import com.smubator.adwitter.CountryPicker.CountryPickerListener;
import com.smubator.adwitter.CustomFonts.MyCustomButton;
import com.smubator.adwitter.Pinview.Pinview;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.volly.DwitterApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {

    private MyCustomButton verifyButton;
    private RelativeLayout rlCountryCode, rlSendOtpBtn;
    private TextView tvCountryCode;
    private String tag_string_req = "string_req", otp;
    private StringRequest stringRequest;
    private EditText etNumber;
    private CountryPicker mCountryPicker;
    private ProgressDialog mProgressDialog;
    private Pinview pinview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        initViews();
        initListeners();
        initCountryPickerListeners();
        getUserCountryInfo();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getResources().getString(R.string.Fetching));
        mProgressDialog.setCancelable(false);
    }

    private void initCountryPickerListeners() {
        mCountryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                //mCountryNameTextView.setText(name);
                //mCountryIsoCodeTextView.setText(code);
                tvCountryCode.setText(dialCode);
                ///mCountryFlagImageView.setImageResource(flagDrawableResID);
                mCountryPicker.dismiss();
            }
        });
    }

    private void getUserCountryInfo() {
        Country country = Country.getCountryFromSIM(getApplicationContext());
        if (country != null) {
            //mCountryFlagImageView.setImageResource(country.getFlag());
            tvCountryCode.setText(country.getDialCode());
            //mCountryIsoCodeTextView.setText(country.getCode());
            //mCountryNameTextView.setText(country.getName());
        }
    }

    private void initListeners() {
        rlCountryCode.setOnClickListener(this);
        verifyButton.setOnClickListener(this);
        rlSendOtpBtn.setOnClickListener(this);
    }

    private void initViews() {
        verifyButton = findViewById(R.id.verifyButton);
        etNumber = findViewById(R.id.etNumber);
        rlCountryCode = findViewById(R.id.rlCountryCode);
        tvCountryCode = findViewById(R.id.tvCountryCode);
        pinview = findViewById(R.id.pinview);
        //     progress_bar = findViewById(R.id.progress_bar);
        rlSendOtpBtn = findViewById(R.id.rlSendOtpBtn);
        mCountryPicker = CountryPicker.newInstance("Select Country");
        // You can limit the displayed countries
        ArrayList<Country> nc = new ArrayList<>();
        for (Country c : Country.getAllCountries()) {
            nc.add(c);
        }
        // and decide, in which order they will be displayed
        Collections.reverse(nc);
        mCountryPicker.setCountriesList(nc);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verifyButton:
                if (pinview.getValue().equalsIgnoreCase(otp)) {
                    verify_user();
                    DwitterApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);

                } else {
                    Toast.makeText(VerificationActivity.this, "Otp not matched", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rlCountryCode:
                mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                break;
            case R.id.rlSendOtpBtn:
                if (etNumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, R.string.etMobileNumberEmpty, Toast.LENGTH_SHORT).show();
                } else {
                    //     progress_bar.setVisibility(View.VISIBLE);
                    mProgressDialog.show();
                    request();
                    DwitterApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
                    DwitterApplication.getInstance().saveString("mobileNo", etNumber.getText().toString());

                }
                break;

        }
    }

    public void request() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");

                    otp = data.optString("otp_code");
                    pinview.setValue(otp);
//                    if (otp.equalsIgnoreCase(pinview.toString())) {
//                        Intent i = new Intent(VerificationActivity.this, IntroductionActivity.class);
//                        startActivity(i);
//                    } else {
//                        Toast.makeText(VerificationActivity.this, "Otp not matched", Toast.LENGTH_SHORT).show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerificationActivity.this, "error", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.SEND_SMS); //Add the data you'd like to send to the server.
                MyData.put("phone", etNumber.getText().toString()); //Add the data you'd like to send to the server.
                MyData.put("user_id", DwitterApplication.getInstance().useString("user_id")); //Add the data you'd like to send to the server.
                MyData.put("code", tvCountryCode.getText().toString()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void verify_user() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");
                    DwitterApplication.getInstance().saveString("id", data.optString("id"));//user id
                    DwitterApplication.getInstance().saveString("twitter_id", data.optString("twitter_id"));//user id
                    DwitterApplication.getInstance().saveString("parent_id", data.optString("parent_id"));//user id
                  //  DwitterApplication.getInstance().saveString("fname", data.optString("fname"));//user id
                    DwitterApplication.getInstance().saveString("phone", data.optString("phone"));//user id
                //    DwitterApplication.getInstance().saveString("profile_image", data.optString("profile_image"));//user id
                    DwitterApplication.getInstance().saveString("joining_date", data.optString("joining_date"));//user id
                    DwitterApplication.getInstance().saveString("user_status", data.optString("status"));//user id

                    Intent i = new Intent(VerificationActivity.this, IntroductionActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerificationActivity.this, "error", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.Verify_User); //Add the data you'd like to send to the server.
                MyData.put("user_id", DwitterApplication.getInstance().useString("user_id")); //Add the data you'd like to send to the server.

                return MyData;
            }
        };
    }

}