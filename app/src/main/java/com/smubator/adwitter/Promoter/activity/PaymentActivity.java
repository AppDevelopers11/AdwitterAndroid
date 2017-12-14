package com.smubator.adwitter.Promoter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.smubator.adwitter.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ImageView checkTrack=findViewById(R.id.checkTrack);
        checkTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PaymentActivity.this, TrackOrderActivity.class);
                startActivity(i);
            }
        });
    }
}
