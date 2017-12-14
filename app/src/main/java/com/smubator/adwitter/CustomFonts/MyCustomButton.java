package com.smubator.adwitter.CustomFonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Tech_Wizard_HP_Andy on 11/28/2017.
 */

@SuppressLint("AppCompatCustomView")
public class MyCustomButton extends Button {

    public MyCustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_regular.ttf");
            setTypeface(tf);
        }
    }
}
