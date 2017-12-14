package com.smubator.adwitter.Promoter.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smubator.adwitter.Promoter.activity.MainActivity;
import com.smubator.adwitter.Promoter.activity.TrackOrderActivity;
import com.smubator.adwitter.R;

public class PaymentFrag extends Fragment {

    private View v;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_payment, container, false);
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.backimage));
        ((MainActivity) getActivity()).title.setText(getResources().getText(R.string.Payment));
        initListners();
        initViews();
        ImageView checkTrack=v.findViewById(R.id.checkTrack);
        checkTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), TrackOrderActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    private void initViews() {
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
