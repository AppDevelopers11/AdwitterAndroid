package com.smubator.adwitter.Promoter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.Promoter.activity.MainActivity;
import com.smubator.adwitter.R;
import com.smubator.adwitter.volly.DwitterApplication;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoterProfileFrag extends Fragment {
    MyCustomTextView tvEmail, tvName, tvLanguage, tvGender;
    private View v;
    private String status;
    private RadioButton rbInactive, rbActive;
    private ImageView userProfile;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_promoter_profile, container, false);
        ((MainActivity) getActivity()).ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.backimage));
        ((MainActivity) getActivity()).title.setText(getResources().getText(R.string.editProfile));
        initViews();
        initListners();

        Picasso.with(getContext()).load(DwitterApplication.getInstance().useString("mUserImage"))
                .placeholder(R.drawable.mode_selection_content_owner_avatar)
                .error(R.drawable.mode_selection_content_owner_avatar)
                .into(userProfile);


        tvEmail.setText(DwitterApplication.getInstance().useString("email"));
        tvName.setText(DwitterApplication.getInstance().useString("mUserName"));
        tvLanguage.setText(DwitterApplication.getInstance().useString("language"));
        tvGender.setText(DwitterApplication.getInstance().useString("gender"));
        status = DwitterApplication.getInstance().useString("status");

        if (status.equalsIgnoreCase("0")) {
            rbInactive.setChecked(true);
            rbActive.setChecked(false);
        } else {
            rbActive.setChecked(true);
            rbInactive.setChecked(false);

        }
        return v;
    }

    private void initListners() {
        ((MainActivity) getActivity()).rlArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ((MainActivity) getActivity()).ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    private void initViews() {

        tvLanguage = v.findViewById(R.id.tvLanguage);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvName = v.findViewById(R.id.tvName);
        tvGender = v.findViewById(R.id.tvGender);
        rbInactive = v.findViewById(R.id.rbInactive);
        rbActive = v.findViewById(R.id.rbActive);
        userProfile = v.findViewById(R.id.userProfile);

    }

}
