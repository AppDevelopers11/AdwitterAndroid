package com.smubator.adwitter.Promoter.activity;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.R;
import com.smubator.adwitter.Utils.DbHelper;

import java.util.Calendar;

public class SchedulingActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlSunday, rlMonday, rlTuesday, rlWednesday, rlThursday, rlFriday, rlSaturday,rlArrow;
    private TextView tvSunday, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday;
    private int mHour, mMinute;
    private TextView tvTime, tvAMPM;
    private String day = "Sun";
    private Button btnPlus;
    private TextView tvaaa;
    private MyCustomTextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);
        initViews();
        initListeners();

    }

    private void initListeners() {
        btnPlus.setOnClickListener(this);
       // tvaaa.setOnClickListener(this);
        rlSunday.setOnClickListener(this);
        rlMonday.setOnClickListener(this);
        rlTuesday.setOnClickListener(this);
        rlWednesday.setOnClickListener(this);
        rlThursday.setOnClickListener(this);
        rlFriday.setOnClickListener(this);
        rlSaturday.setOnClickListener(this);
        tvAMPM.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        rlArrow.setOnClickListener(this);
    }

    private void initViews() {
        btnPlus = findViewById(R.id.btnPlus);
        tvaaa = findViewById(R.id.tvaaa);
        rlSunday = findViewById(R.id.rlSunday);
        rlMonday = findViewById(R.id.rlMonday);
        rlTuesday = findViewById(R.id.rlTuesday);
        rlWednesday = findViewById(R.id.rlWednesday);
        rlThursday = findViewById(R.id.rlThursday);
        rlFriday = findViewById(R.id.rlFriday);
        rlSaturday = findViewById(R.id.rlSaturday);
        tvSunday = findViewById(R.id.tvSunday);
        tvMonday = findViewById(R.id.tvMonday);
        tvTuesday = findViewById(R.id.tvTuesday);
        tvWednesday = findViewById(R.id.tvWednesday);
        tvThursday = findViewById(R.id.tvThursday);
        tvFriday = findViewById(R.id.tvFriday);
        tvSaturday = findViewById(R.id.tvSaturday);

        title = findViewById(R.id.title);
        rlArrow = findViewById(R.id.rlArrow);

        tvTime = findViewById(R.id.tvTime);
        tvAMPM = findViewById(R.id.tvAMPM);
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        tvTime.setText(mHour + ":" + mMinute);
        if (mHour < 12) {
            tvAMPM.setText("AM");
        } else {
            tvAMPM.setText("PM");
        }
        title.setText(getResources().getText(R.string.scheduling));
    }

    public void selectTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(SchedulingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 12) {
                    tvAMPM.setText("AM");
                } else {
                    tvAMPM.setText("PM");
                }
                if (hourOfDay < 10) {
                    tvTime.setText("0" + hourOfDay + ":" + minute);
                } else {
                    tvTime.setText(hourOfDay + ":" + minute);
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvaaa:
                break;

            case R.id.btnPlus:
                DbHelper db = new DbHelper(SchedulingActivity.this);
                db.addTweet(day, tvTime.getText().toString() + " " + tvAMPM.getText().toString());
                break;
            case R.id.tvTime:

            case R.id.tvAMPM:
                selectTime();
                break;
            case R.id.rlSunday:
                day = "Sun";
                rlSunday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvSunday.setTextColor(Color.WHITE);

                rlMonday.setBackgroundColor(Color.TRANSPARENT);
                tvMonday.setTextColor(getResources().getColor(R.color.blue));

                rlTuesday.setBackgroundColor(Color.TRANSPARENT);
                tvTuesday.setTextColor(getResources().getColor(R.color.blue));

                rlWednesday.setBackgroundColor(Color.TRANSPARENT);
                tvWednesday.setTextColor(getResources().getColor(R.color.blue));

                rlThursday.setBackgroundColor(Color.TRANSPARENT);
                tvThursday.setTextColor(getResources().getColor(R.color.blue));

                rlFriday.setBackgroundColor(Color.TRANSPARENT);
                tvFriday.setTextColor(getResources().getColor(R.color.blue));

                rlSaturday.setBackgroundColor(Color.TRANSPARENT);
                tvSaturday.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.rlMonday:
                day = "Mon";
                rlSunday.setBackgroundColor(Color.TRANSPARENT);
                tvSunday.setTextColor(getResources().getColor(R.color.blue));

                rlMonday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvMonday.setTextColor(Color.WHITE);

                rlTuesday.setBackgroundColor(Color.TRANSPARENT);
                tvTuesday.setTextColor(getResources().getColor(R.color.blue));

                rlWednesday.setBackgroundColor(Color.TRANSPARENT);
                tvWednesday.setTextColor(getResources().getColor(R.color.blue));

                rlThursday.setBackgroundColor(Color.TRANSPARENT);
                tvThursday.setTextColor(getResources().getColor(R.color.blue));

                rlFriday.setBackgroundColor(Color.TRANSPARENT);
                tvFriday.setTextColor(getResources().getColor(R.color.blue));

                rlSaturday.setBackgroundColor(Color.TRANSPARENT);
                tvSaturday.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.rlTuesday:
                day = "Tue";
                rlSunday.setBackgroundColor(Color.TRANSPARENT);
                tvSunday.setTextColor(getResources().getColor(R.color.blue));

                rlMonday.setBackgroundColor(Color.TRANSPARENT);
                tvMonday.setTextColor(getResources().getColor(R.color.blue));

                rlTuesday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvTuesday.setTextColor(Color.WHITE);

                rlWednesday.setBackgroundColor(Color.TRANSPARENT);
                tvWednesday.setTextColor(getResources().getColor(R.color.blue));

                rlThursday.setBackgroundColor(Color.TRANSPARENT);
                tvThursday.setTextColor(getResources().getColor(R.color.blue));

                rlFriday.setBackgroundColor(Color.TRANSPARENT);
                tvFriday.setTextColor(getResources().getColor(R.color.blue));

                rlSaturday.setBackgroundColor(Color.TRANSPARENT);
                tvSaturday.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.rlWednesday:
                day = "Wed";
                rlSunday.setBackgroundColor(Color.TRANSPARENT);
                tvSunday.setTextColor(getResources().getColor(R.color.blue));

                rlMonday.setBackgroundColor(Color.TRANSPARENT);
                tvMonday.setTextColor(getResources().getColor(R.color.blue));

                rlTuesday.setBackgroundColor(Color.TRANSPARENT);
                tvTuesday.setTextColor(getResources().getColor(R.color.blue));

                rlWednesday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvWednesday.setTextColor(Color.WHITE);

                rlThursday.setBackgroundColor(Color.TRANSPARENT);
                tvThursday.setTextColor(getResources().getColor(R.color.blue));

                rlFriday.setBackgroundColor(Color.TRANSPARENT);
                tvFriday.setTextColor(getResources().getColor(R.color.blue));

                rlSaturday.setBackgroundColor(Color.TRANSPARENT);
                tvSaturday.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.rlThursday:
                day = "Thu";
                rlSunday.setBackgroundColor(Color.TRANSPARENT);
                tvSunday.setTextColor(getResources().getColor(R.color.blue));

                rlMonday.setBackgroundColor(Color.TRANSPARENT);
                tvMonday.setTextColor(getResources().getColor(R.color.blue));

                rlTuesday.setBackgroundColor(Color.TRANSPARENT);
                tvTuesday.setTextColor(getResources().getColor(R.color.blue));

                rlWednesday.setBackgroundColor(Color.TRANSPARENT);
                tvWednesday.setTextColor(getResources().getColor(R.color.blue));

                rlThursday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvThursday.setTextColor(Color.WHITE);

                rlFriday.setBackgroundColor(Color.TRANSPARENT);
                tvFriday.setTextColor(getResources().getColor(R.color.blue));

                rlSaturday.setBackgroundColor(Color.TRANSPARENT);
                tvSaturday.setTextColor(getResources().getColor(R.color.blue));
                break;

            case R.id.rlFriday:
                day = "Fri";
                rlSunday.setBackgroundColor(Color.TRANSPARENT);
                tvSunday.setTextColor(getResources().getColor(R.color.blue));

                rlMonday.setBackgroundColor(Color.TRANSPARENT);
                tvMonday.setTextColor(getResources().getColor(R.color.blue));

                rlTuesday.setBackgroundColor(Color.TRANSPARENT);
                tvTuesday.setTextColor(getResources().getColor(R.color.blue));

                rlWednesday.setBackgroundColor(Color.TRANSPARENT);
                tvWednesday.setTextColor(getResources().getColor(R.color.blue));

                rlThursday.setBackgroundColor(Color.TRANSPARENT);
                tvThursday.setTextColor(getResources().getColor(R.color.blue));

                rlFriday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvFriday.setTextColor(Color.WHITE);

                rlSaturday.setBackgroundColor(Color.TRANSPARENT);
                tvSaturday.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.rlSaturday:
                day = "Sat";
                rlSunday.setBackgroundColor(Color.TRANSPARENT);
                tvSunday.setTextColor(getResources().getColor(R.color.blue));

                rlMonday.setBackgroundColor(Color.TRANSPARENT);
                tvMonday.setTextColor(getResources().getColor(R.color.blue));

                rlTuesday.setBackgroundColor(Color.TRANSPARENT);
                tvTuesday.setTextColor(getResources().getColor(R.color.blue));

                rlWednesday.setBackgroundColor(Color.TRANSPARENT);
                tvWednesday.setTextColor(getResources().getColor(R.color.blue));

                rlThursday.setBackgroundColor(Color.TRANSPARENT);
                tvThursday.setTextColor(getResources().getColor(R.color.blue));

                rlFriday.setBackgroundColor(Color.TRANSPARENT);
                tvFriday.setTextColor(getResources().getColor(R.color.blue));

                rlSaturday.setBackgroundDrawable(getResources().getDrawable(R.drawable.days_back));
                tvSaturday.setTextColor(Color.WHITE);
                break;
            case R.id.rlArrow:
                finish();
                break;
        }

    }
}