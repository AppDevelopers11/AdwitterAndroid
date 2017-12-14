package com.smubator.adwitter.Promoter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.smubator.adwitter.Promoter.activity.MainActivity;
import com.smubator.adwitter.R;


public class AccountSettingsFrag extends Fragment {

    private RelativeLayout titleLayout;
    private View v;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        titleLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_account_setting, container, false);
        ((MainActivity) getActivity()).ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.backimage));
        ((MainActivity) getActivity()).title.setText(getResources().getText(R.string.accountsettings));
        initListners();
        initViews();

        return v;
    }

    private void initViews() {
        titleLayout = v.findViewById(R.id.titleLayout);
        titleLayout.setVisibility(View.GONE);

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
}