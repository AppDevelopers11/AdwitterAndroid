package com.smubator.adwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smubator.adwitter.ActivitySplitAnimation.ActivitySplitAnimationUtil;
import com.smubator.adwitter.Promoter.activity.AccountSettingActivity;
import com.smubator.adwitter.Promoter.activity.MainActivity;
import com.smubator.adwitter.Promoter.activity.PromoterAccountActivity;
import com.smubator.adwitter.volly.DwitterApplication;

public class ModeSelectionActivity extends Activity implements View.OnClickListener {
    private LinearLayout linearPromoter;
    private LinearLayout linearContentOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);
        initViews();
        initListeners();
    }

    private void initListeners() {
        linearPromoter.setOnClickListener(this);
    }

    private void initViews() {
        linearPromoter = findViewById(R.id.linearPromoter);
        linearContentOwner = findViewById(R.id.linearContentOwner);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearContentOwner:
                DwitterApplication.getInstance().saveString("account_type", "1");

                if(DwitterApplication.getInstance().useString("content_owner_data").equalsIgnoreCase("FALSE"))
            {
            }
            break;

            case R.id.linearPromoter:
                DwitterApplication.getInstance().saveString("account_type", "2");

                //if(DwitterApplication.getInstance().useString("promoter_data").equalsIgnoreCase("FALSE"))
                if(!DwitterApplication.getInstance().useString("user_status").equalsIgnoreCase("complete"))

                {
                    Intent i=new Intent(ModeSelectionActivity.this, AccountSettingActivity.class);
                    startActivity(i);
                    finish();
                }

                else
                {
                    ActivitySplitAnimationUtil.startActivity(ModeSelectionActivity.this, new Intent(ModeSelectionActivity.this, MainActivity.class));
                }
                break;

        }
    }
}
