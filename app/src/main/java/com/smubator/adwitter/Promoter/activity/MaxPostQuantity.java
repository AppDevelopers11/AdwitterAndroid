package com.smubator.adwitter.Promoter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.R;

public class MaxPostQuantity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlArrow;
    private MyCustomTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maximum_post_quantity);
        initListeners();

        initViews();
        title.setText(getResources().getText(R.string.Posts));
    }

    private void initViews() {
        rlArrow.setOnClickListener(this);

    }

    private void initListeners() {
        rlArrow = findViewById(R.id.rlArrow);
        title = findViewById(R.id.title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlArrow:
                finish();
                break;
        }
    }
}
