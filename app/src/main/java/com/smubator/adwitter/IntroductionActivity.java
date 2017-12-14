package com.smubator.adwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IntroductionActivity extends Activity implements View.OnClickListener {
    private ImageView ivArrowRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        initViews();
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
                Intent i = new Intent(IntroductionActivity.this, ModeSelectionActivity.class);
                startActivity(i);
                break;
        }
    }
}
