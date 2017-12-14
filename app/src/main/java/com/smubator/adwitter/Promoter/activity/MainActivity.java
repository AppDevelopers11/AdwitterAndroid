package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.smubator.adwitter.Promoter.fragment.AccountSettingsFrag;
import com.smubator.adwitter.Promoter.fragment.PaymentFrag;
import com.smubator.adwitter.Promoter.fragment.PromoterAboutUsFrag;
import com.smubator.adwitter.Promoter.fragment.PromoterHomeFrag;
import com.smubator.adwitter.Promoter.fragment.PromoterProfileFrag;
import com.smubator.adwitter.R;
import com.smubator.adwitter.SignInAcitivity;
import com.smubator.adwitter.volly.DwitterApplication;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView ivArrow;
    public TextView title;
    public DrawerLayout mDrawerLayout;
    public RelativeLayout rlArrow, rlHome, rlAccountSettings, rlAccounts, rlPaymentsAndFinancials, rlSwitchMode, rlCallSupport, rlAboutUs, rlLogOut;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolBar;
    private ImageView rlProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        initViews();
        initListners();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();

        PromoterUserScreenActivity myFragment = new PromoterUserScreenActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myFragment).commit();

    }

    private void initViews() {
        ivArrow = findViewById(R.id.ivArrow);
        rlHome = findViewById(R.id.rlHome);
        rlAccountSettings = findViewById(R.id.rlAccountSettings);
        rlAccounts = findViewById(R.id.rlAccounts);
        rlPaymentsAndFinancials = findViewById(R.id.rlPaymentsAndFinancials);
        rlSwitchMode = findViewById(R.id.rlSwitchMode);
        rlCallSupport = findViewById(R.id.rlCallSupport);
        rlAboutUs = findViewById(R.id.rlAboutUs);
        rlLogOut = findViewById(R.id.rlLogOut);
        rlArrow = findViewById(R.id.rlArrow);
        rlProfile = findViewById(R.id.rlProfile);
        ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.menu_blue));
        title = findViewById(R.id.title);
        title.setText(R.string.Home);
        Picasso.with(this).load(DwitterApplication.getInstance().useString("mUserImage"))
                .placeholder(R.drawable.mode_selection_content_owner_avatar)
                .error(R.drawable.mode_selection_content_owner_avatar)
                .into(rlProfile);
    }

    private void initListners() {
        rlHome.setOnClickListener(this);
        rlAccountSettings.setOnClickListener(this);
        rlAccounts.setOnClickListener(this);
        rlPaymentsAndFinancials.setOnClickListener(this);
        rlSwitchMode.setOnClickListener(this);
        rlCallSupport.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlLogOut.setOnClickListener(this);
        ivArrow.setOnClickListener(this);
        rlArrow.setOnClickListener(this);
        rlProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlArrow:
            case R.id.ivArrow:
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.rlProfile:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                PromoterProfileFrag profileFrag = new PromoterProfileFrag();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, profileFrag).addToBackStack(null).commit();
                break;

            case R.id.rlHome:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                PromoterUserScreenActivity promoterUserScreenActivity = new PromoterUserScreenActivity();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, promoterUserScreenActivity).commit();
                break;

            case R.id.rlAccountSettings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                AccountSettingsFrag accountSettingsFrag = new AccountSettingsFrag();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, accountSettingsFrag).addToBackStack(null).commit();
                break;

            case R.id.rlAccounts:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                PromoterHomeFrag promoterHomeFrag = new PromoterHomeFrag();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, promoterHomeFrag).addToBackStack(null).commit();
                break;

            case R.id.rlPaymentsAndFinancials:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                PaymentFrag paymentFrag = new PaymentFrag();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, paymentFrag).addToBackStack(null).commit();
                break;

            case R.id.rlSwitchMode:
                mDrawerLayout.closeDrawer(GravityCompat.START);
//                PromoterUserScreenActivity promoterUserScreenActivity = new PromoterUserScreenActivity();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, promoterUserScreenActivity).addToBackStack(null).commit();
                break;

            case R.id.rlCallSupport:
                mDrawerLayout.closeDrawer(GravityCompat.START);
//                PromoterUserScreenActivity promoterUserScreenActivity = new PromoterUserScreenActivity();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, promoterUserScreenActivity).addToBackStack(null).commit();
                break;

            case R.id.rlAboutUs:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                PromoterAboutUsFrag promoterAboutUsFrag = new PromoterAboutUsFrag();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, promoterAboutUsFrag).addToBackStack(null).commit();
                break;

            case R.id.rlLogOut:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                DwitterApplication.getInstance().clearSharedPreferences();

                Intent i=new Intent(MainActivity.this, SignInAcitivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
                super.onBackPressed();
            }
        }
    }
}
