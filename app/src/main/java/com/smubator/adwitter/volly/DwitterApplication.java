package com.smubator.adwitter.volly;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.misc.Utils;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.smubator.adwitter.R;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class DwitterApplication extends Application {
    public static final String TAG = Utils.class.getSimpleName();
    private static DwitterApplication instance;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
   // private SplashModel splashModel;
    private SharedPreferences sharedPreferences;


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

//    public SplashModel getSplashModel() {
//        return splashModel;
//    }

//    public void setSplashModel(SplashModel splashModel) {
//        this.splashModel = splashModel;
//    }

    public static synchronized DwitterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        instance = this;
        sharedPreferences = getApplicationContext().getSharedPreferences("Dwitter", 0);

    }

    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public boolean containsKey(String key)
    {

        if(sharedPreferences.contains(key))
        {
            return true;
        }
        else {
            return false;
        }
    }

    public void clearSharedPreferences() {
        sharedPreferences.edit().clear().apply();
    }

    public String useString(String key) {
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(90 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        getRequestQueue().cancelAll(tag);
    }
    public static ProgressDialog mProgressDialog;

    public void showProgress(Context context, String message) {

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.setMessage(context.getResources().getString(R.string.Fetching));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideProgress(Context context) {
        if (mProgressDialog.isShowing() && mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog.hide();
        }
    }
}
