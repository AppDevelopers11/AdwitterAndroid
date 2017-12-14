package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.smubator.adwitter.ActivitySplitAnimation.ActivitySplitAnimationUtil;
import com.smubator.adwitter.R;

public class PromoterAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivArrowRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplitAnimationUtil.prepareAnimation(this);

        setContentView(R.layout.activity_promoter_accounts);
        initViews();
        ActivitySplitAnimationUtil.animate(this, 500);
        initListeners();
    }

    private void initListeners() {
        ivArrowRight.setOnClickListener(this);
    }

    private void initViews() {
        ivArrowRight = findViewById(R.id.ivArrowRight);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivArrowRight:
                Intent i = new Intent(PromoterAccountActivity.this, AccountSettingActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                break;
        }
    }
}
