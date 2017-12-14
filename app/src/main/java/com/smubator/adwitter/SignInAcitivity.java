package com.smubator.adwitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.smubator.adwitter.Promoter.activity.MainActivity;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.volly.DwitterApplication;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class SignInAcitivity extends AppCompatActivity implements View.OnClickListener {
    private StringRequest stringRequest;
    private RelativeLayout rlSignIn;
    private static final String CONSUMER_KEY = "RSKw5Ke4Ybd2bRm0T8JTVZccn";
    private static final String CONSUMER_SECRET = "MgNfVGi38wHYbBMAa4llCXY1dBWbunMTRUNWvD9sJ1HEpnY8o5";
    TwitterAuthToken authToken;
    private TwitterLoginButton loginButton;
    TwitterSession session;
    private SharedPreferences sharedpreferences;
    ProgressDialog progressBar;
    String photoUrlBiggerSize, name, device_id, mUserName, mUserImage, Status_success, twitter_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);
        setContentView(R.layout.activity_sign_in);
        initViews();
        initListeners();

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loginButton = findViewById(R.id.btnLoginTwitter);
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DwitterApplication.getInstance().saveString("device_id", device_id);

        loginButton.setCallback(new Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {

                final Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false, true);
                user.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        Log.e("SessionTokenA", String.valueOf(TwitterCore.getInstance().getSessionManager().getActiveSession()));
                        name = userResult.data.name;
                        twitter_id = userResult.data.idStr;

                        //   String email = userResult.data.email;

                        // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                        photoUrlBiggerSize = userResult.data.profileImageUrl.replace("_normal", "_bigger");

                        DwitterApplication.getInstance().saveString("twitter_id", twitter_id);
                        DwitterApplication.getInstance().saveString("mUserName", name);
                        DwitterApplication.getInstance().saveString("mUserImage", photoUrlBiggerSize);

                    }

                    @Override
                    public void failure(TwitterException exc) {
                        Log.d("TwitterKit", "Verify Credentials Failure", exc);
                    }
                });
                progressBar.show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }

        });
        try {
            fetchLocalJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchLocalJson() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.cities);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        String jsonString = writer.toString();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
//                sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("login", "login");
//                //editor.putString("mUserImage", mUserImage);
//                editor.putString("name", mUserName);
//                editor.apply();
                progressBar.dismiss();
                String child_parent = getIntent().getStringExtra("addChild");
                Log.i("", "");
                if (child_parent != null) {
                    if (child_parent.equalsIgnoreCase("addChild")) {
                        createChildAccount();
                        DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
                    } else {
                        createAccount();
                        DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
                    }
                } else {
                    createAccount();
                    DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(SignInAcitivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });
        DwitterApplication.getInstance().saveString("token", token);
        DwitterApplication.getInstance().saveString("secret", secret);

    }

    private void initListeners() {
        rlSignIn.setOnClickListener(this);
    }

    private void initViews() {
        rlSignIn = findViewById(R.id.rlSignIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.rlSignIn:
//                Intent i = new Intent(SignInAcitivity.this, VerificationActivity.class);
//                //  Intent i = new Intent(SignInAcitivity.this, GraphActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                overridePendingTransition(0, 0);
//                startActivity(i);
//                //finish();
//                break;
        }
    }

    public void createAccount() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");
//                    Log.i("", "");

                    DwitterApplication.getInstance().saveString("user_id", data.optString("user_id"));
                    DwitterApplication.getInstance().saveString("parent_id", data.optString("parent_id"));
                    DwitterApplication.getInstance().saveString("content_owner_data", data.optString("content_owner_data"));
                    DwitterApplication.getInstance().saveString("promoter_data", data.optString("promoter_data"));
                    DwitterApplication.getInstance().saveString("user_status", data.optString("user_status"));
                    DwitterApplication.getInstance().saveString("account_status", data.optString("account_status"));
                    DwitterApplication.getInstance().saveString("language", data.optString("language"));
                    DwitterApplication.getInstance().saveString("gender", data.optString("gender"));
                    DwitterApplication.getInstance().saveString("mUserImage", data.optString("profile_image"));
                    DwitterApplication.getInstance().saveString("age", data.optString("age"));
                    DwitterApplication.getInstance().saveString("country", data.optString("country"));
                    DwitterApplication.getInstance().saveString("city", data.optString("city"));
                    DwitterApplication.getInstance().saveString("mUserName", data.optString("fname"));
                    DwitterApplication.getInstance().saveString("parent_id", data.optString("parent_id"));
                    DwitterApplication.getInstance().saveString("id", data.optString("id"));
                    DwitterApplication.getInstance().saveString("user_id", data.optString("user_id"));      //for adding child

                    if(data.optString("registered").equalsIgnoreCase("TRUE"))
                    {
                        Toast.makeText(SignInAcitivity.this, "This user is already exist!!", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        DwitterApplication.getInstance().saveString("account_type", data.optString("account_type"));
                        if (data.optString("account_type").equalsIgnoreCase("1")) {

                        } else if (data.optString("account_type").equalsIgnoreCase("2")) {
                            if (data.optString("user_status").equalsIgnoreCase("not_verified")) {
                                Intent i = new Intent(SignInAcitivity.this, VerificationActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("verified")) {
                                Intent i = new Intent(SignInAcitivity.this, IntroductionActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("complete")) {
                                Intent i = new Intent(SignInAcitivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } else {
                            if (data.optString("user_status").equalsIgnoreCase("not_verified")) {
                                Intent i = new Intent(SignInAcitivity.this, VerificationActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("verified")) {
                                Intent i = new Intent(SignInAcitivity.this, IntroductionActivity.class);
                                startActivity(i);
                                finish();
                            } else if (data.optString("user_status").equalsIgnoreCase("complete")) {
                                Intent i = new Intent(SignInAcitivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInAcitivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.CreateUser);
                MyData.put("device_id", DwitterApplication.getInstance().useString("device_id"));
                MyData.put("device_type", "android");
                MyData.put("twitter_id", DwitterApplication.getInstance().useString("twitter_id"));
                MyData.put("twitter_auth_token", DwitterApplication.getInstance().useString("token"));
                MyData.put("twitter_auth_secret_token", DwitterApplication.getInstance().useString("secret"));
                MyData.put("fname", DwitterApplication.getInstance().useString("mUserName"));
                MyData.put("lname", "");
                MyData.put("email", "");
                MyData.put("image", DwitterApplication.getInstance().useString("mUserImage"));

                MyData.put("parent_id", "0");

                return MyData;
            }
        };
    }

    public void createChildAccount() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");
//                    Log.i("", "");

                    DwitterApplication.getInstance().saveString("user_id", data.optString("user_id"));
                    DwitterApplication.getInstance().saveString("parent_id", data.optString("parent_id"));
                    DwitterApplication.getInstance().saveString("content_owner_data", data.optString("content_owner_data"));
                    DwitterApplication.getInstance().saveString("promoter_data", data.optString("promoter_data"));
                    DwitterApplication.getInstance().saveString("user_status", data.optString("user_status"));
                    DwitterApplication.getInstance().saveString("account_status", data.optString("account_status"));
                    DwitterApplication.getInstance().saveString("language", data.optString("language"));
                    DwitterApplication.getInstance().saveString("gender", data.optString("gender"));
                    DwitterApplication.getInstance().saveString("mUserImage", data.optString("profile_image"));
                    DwitterApplication.getInstance().saveString("age", data.optString("age"));
                    DwitterApplication.getInstance().saveString("country", data.optString("country"));
                    DwitterApplication.getInstance().saveString("city", data.optString("city"));
                    DwitterApplication.getInstance().saveString("mUserName", data.optString("fname"));

                    DwitterApplication.getInstance().saveString("account_type", data.optString("account_type"));
                    if (data.optString("account_type").equalsIgnoreCase("1")) {

                    } else if (data.optString("account_type").equalsIgnoreCase("2")) {
                        if (data.optString("user_status").equalsIgnoreCase("not_verified")) {
                            Intent i = new Intent(SignInAcitivity.this, VerificationActivity.class);
                            startActivity(i);
                            finish();
                        } else if (data.optString("user_status").equalsIgnoreCase("verified")) {
                            Intent i = new Intent(SignInAcitivity.this, IntroductionActivity.class);
                            startActivity(i);
                            finish();
                        } else if (data.optString("user_status").equalsIgnoreCase("complete")) {
                            Intent i = new Intent(SignInAcitivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        if (data.optString("user_status").equalsIgnoreCase("not_verified")) {
                            Intent i = new Intent(SignInAcitivity.this, VerificationActivity.class);
                            startActivity(i);
                            finish();
                        } else if (data.optString("user_status").equalsIgnoreCase("verified")) {
                            Intent i = new Intent(SignInAcitivity.this, IntroductionActivity.class);
                            startActivity(i);
                            finish();
                        } else if (data.optString("user_status").equalsIgnoreCase("complete")) {
                            Intent i = new Intent(SignInAcitivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInAcitivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.Create_Child);
                MyData.put("device_id", DwitterApplication.getInstance().useString("device_id"));
                MyData.put("device_type", "android");
                MyData.put("twitter_id", DwitterApplication.getInstance().useString("twitter_id"));
                MyData.put("twitter_auth_token", DwitterApplication.getInstance().useString("token"));
                MyData.put("twitter_auth_secret_token", DwitterApplication.getInstance().useString("secret"));
                MyData.put("fname", DwitterApplication.getInstance().useString("mUserName"));
                MyData.put("lname", "");
                MyData.put("email", "");
                MyData.put("image", DwitterApplication.getInstance().useString("mUserImage"));
                if (DwitterApplication.getInstance().useString("parent_id").equalsIgnoreCase("0")) {
                    MyData.put("parent_id", DwitterApplication.getInstance().useString("user_id"));

                } else {
                    MyData.put("parent_id", DwitterApplication.getInstance().useString("parent_id"));
                }

                return MyData;
            }
        };
    }

}
