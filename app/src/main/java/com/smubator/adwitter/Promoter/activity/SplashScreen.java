package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.smubator.adwitter.IntroductionActivity;
import com.smubator.adwitter.R;
import com.smubator.adwitter.SignInAcitivity;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.VerificationActivity;
import com.smubator.adwitter.volly.DwitterApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    private StringRequest stringRequest;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        boolean checkID = DwitterApplication.getInstance().containsKey("user_id");
        if (!checkID) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, SignInAcitivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);

        } else {
            get_User_Min_Details();
            DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    //
//       boolean check=(DwitterApplication.getInstance().containsKey("user_id"));
//       if(check) {
//           if (DwitterApplication.getInstance().useString("user_status").equalsIgnoreCase("not_verified")) {
//
//           }
//           else if()
//           {
//
//           }
//
//           if (DwitterApplication.getInstance().useString("user_status").equalsIgnoreCase("complete")) {
//               if (DwitterApplication.getInstance().useString("               if (DwitterApplication.getInstance().useString(\"account_type\").equalsIgnoreCase(\"2\")) {\n").equalsIgnoreCase("2")) {
//                   Intent i = new Intent(SplashScreen.this, PromoterHomeActivity.class);
//                   startActivity(i);
//                   finish();
//               } else if(DwitterApplication.getInstance().useString("account_type").equalsIgnoreCase("1")) {
////                   Intent i = new Intent(SplashScreen.this, .class);
////                   startActivity(i);
////                   finish();
//               }
//           }
//       }
//       else {
//            Intent i = new Intent(SplashScreen.this, SignInAcitivity.class);
//            startActivity(i);
//            finish();
//        }
//    }
    public void get_User_Min_Details() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //{"status":1,"status_text":"success","message":"User Found.","data":{"account_type":"2","user_status":"complete"}}
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.optString("status");
                    if (status.equalsIgnoreCase("1")) {
                        JSONObject data = jsonObject.optJSONObject("data");

                        //DwitterApplication.getInstance().saveString("id", data.optString("id"));//user id
                        DwitterApplication.getInstance().saveString("account_type", data.optString("account_type"));
                        if (data.optString("account_type").equalsIgnoreCase("1")) {

                        } else if (data.optString("account_type").equalsIgnoreCase("2")) {
                            if (data.optString("user_status").equalsIgnoreCase("not_verified")) {
                                Intent i = new Intent(SplashScreen.this, VerificationActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("verified")) {
                                Intent i = new Intent(SplashScreen.this, IntroductionActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("complete")) {
                                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } else {
                            if (data.optString("user_status").equalsIgnoreCase("not_verified")) {
                                Intent i = new Intent(SplashScreen.this, VerificationActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("verified")) {
                                Intent i = new Intent(SplashScreen.this, IntroductionActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("complete")) {
                                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }

                    } else {
                        Toast.makeText(SplashScreen.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashScreen.this, "error", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.Get_User_Min_Details); //Add the data you'd like to send to the server.
                MyData.put("user_id", DwitterApplication.getInstance().useString("user_id")); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
    }

}
