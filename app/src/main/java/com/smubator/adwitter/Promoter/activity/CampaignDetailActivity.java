package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.R;
import com.smubator.adwitter.volly.DwitterApplication;
import com.squareup.picasso.Picasso;


public class CampaignDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private MyCustomTextView title,tvClicks,tvIncome,tvStartDate,campaignTitle;
    private ImageView ivArrow;
    private ImageView ivDemo;

    @Override
    protected void onResume() {
        super.onResume();
        campaignTitle.setText(getIntent().getStringExtra("camp_name"));
        tvClicks.setText(getIntent().getStringExtra("camp_max_cpc"));
        tvIncome.setText(getIntent().getStringExtra("camp_daily_budget"));
        tvStartDate.setText(getIntent().getStringExtra("camp_from"));
        tvStartDate.setText(getIntent().getStringExtra("camp_from"));

        Picasso.with(this).load((getIntent().getStringExtra("camp_media")))
                .placeholder(R.drawable.mode_selection_content_owner_avatar)
                .error(R.drawable.mode_selection_content_owner_avatar)
                .into(ivDemo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        i.getStringExtra("camp_name");
        i.getStringExtra("camp_from");
        i.getStringExtra("camp_daily_budget");
        i.getStringExtra("camp_max_cpc");
        i.getStringExtra("camp_media");
        i.getStringExtra("camp_to");
        setContentView(R.layout.activity_campaign_detail);




        title = findViewById(R.id.title);
        tvClicks = findViewById(R.id.tvClicks);
        campaignTitle = findViewById(R.id.campaignTitle);
        tvIncome = findViewById(R.id.tvIncome);
        tvStartDate = findViewById(R.id.tvStartDate);
        ivArrow = findViewById(R.id.ivArrow);
        ivDemo = findViewById(R.id.ivDemo);
        ivArrow.setOnClickListener(this);

        title.setText(R.string.campaignDetail);
        title.setText(R.string.campaignDetail);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivArrow:
                finish();

        }
    }
}
