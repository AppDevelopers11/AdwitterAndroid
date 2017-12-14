package com.smubator.adwitter.Promoter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.smubator.adwitter.Carousel.CoverFlowCarousel;
import com.smubator.adwitter.Models.CampaignModel;
import com.smubator.adwitter.R;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.volly.DwitterApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PromoterUserScreenActivity extends Fragment implements View.OnClickListener {
    private CoverFlowCarousel carousel;
    private ListView listView;
    private StringRequest stringRequest;
    private View v;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) this.getActivity()).title.setText(getResources().getText(R.string.Home));
    }

    private void getCampaign() {
        DwitterApplication.getInstance().showProgress(getActivity(),"Fetching Campaings");
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.optString("status");
                    if (status.equalsIgnoreCase("1")) {
                        DwitterApplication.getInstance().hideProgress(getActivity());

                        String status_text = jsonObject.optString("status_text");
                        String message = jsonObject.optString("message");
                        JSONArray data = jsonObject.optJSONArray("data");
                        final ArrayList<CampaignModel> campaignModelArrayList = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            CampaignModel campaignModel = new CampaignModel();
                            JSONObject object = data.getJSONObject(i);
                            campaignModel.setId(object.optString("id"));
                            campaignModel.setUser_id(object.optString("user_id"));
                            campaignModel.setCategory_id(object.optString("category_id"));
                            campaignModel.setCamp_name(object.optString("camp_name"));
                            campaignModel.setCamp_description(object.optString("camp_description"));
                            campaignModel.setCamp_from(object.optString("camp_from"));
                            campaignModel.setCamp_to(object.optString("camp_to"));
                            campaignModel.setCamp_daily_budget(object.optString("camp_daily_budget"));
                            campaignModel.setCamp_max_cpc(object.optString("camp_max_cpc"));
                            campaignModel.setCamp_text_url(object.optString("camp_text_url"));
                            campaignModel.setCamp_media(object.optString("camp_media"));
                            campaignModel.setCamp_status(object.optString("camp_status"));
                            campaignModel.setCamp_active(object.optBoolean("camp_active"));
                            campaignModelArrayList.add(campaignModel);
                        }
                        listView.setAdapter(new MyListAdapter(getActivity(), campaignModelArrayList));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent i = new Intent(getActivity(), CampaignDetailActivity.class);
                                i.putExtra("camp_name",campaignModelArrayList.get(position).getCamp_name());
                                i.putExtra("camp_from",campaignModelArrayList.get(position).getCamp_from());
                                i.putExtra("camp_daily_budget",campaignModelArrayList.get(position).getCamp_daily_budget());
                                i.putExtra("camp_max_cpc",campaignModelArrayList.get(position).getCamp_max_cpc());
                                i.putExtra("camp_media",campaignModelArrayList.get(position).getCamp_media());
                                i.putExtra("camp_to",campaignModelArrayList.get(position).getCamp_to());
                                getActivity().startActivity(i);
                            }
                        });
                    } else {
                        String a = jsonObject.optString("message");
                        Toast.makeText(getActivity(), a, Toast.LENGTH_LONG).show();
                    }

                    Log.i("", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("", "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DwitterApplication.getInstance().hideProgress(getActivity());
                Log.i("", "");
                //Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.getCampaign); //Add the data you'd like to send to the server.
                MyData.put("user_id", DwitterApplication.getInstance().useString("user_id")); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_promoter_user_screen, container, false);
        initViews();
        initListners();
        getCampaign();
        DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");
        return v;
    }

    private void initViews() {

        carousel = v.findViewById(R.id.carousel);
        listView = v.findViewById(R.id.listView);
        ((MainActivity) getActivity()).ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.menu_blue));
        final MyAdapter adapter = new MyAdapter();
        carousel.setAdapter(adapter);
        carousel.setSelection(adapter.getCount() / 2); //adapter.getCount()-1
        carousel.setSlowDownCoefficient(1);
        carousel.setSpacing(0.60f);
    }

    private void initListners() {
        ((MainActivity) getActivity()).ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    ((MainActivity) getActivity()).mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    ((MainActivity) getActivity()).mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        ((MainActivity) getActivity()).rlArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    ((MainActivity) getActivity()).mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    ((MainActivity) getActivity()).mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public static class MyFrame extends FrameLayout {
        private ImageView mImageView;

        public MyFrame(Context context) {
            super(context);
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(mImageView);

            setBackgroundColor(Color.WHITE);
            setSelected(false);
        }

        public void setImageResource(int resId) {
            mImageView.setImageResource(resId);
        }

        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);

            if (selected) {
                mImageView.setAlpha(1.0f);
            } else {
                mImageView.setAlpha(0.5f);
            }
        }
    }

    private class MyAdapter extends BaseAdapter {
        private int[] mResourceIds = {R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image,
                R.drawable.image};

        private int mCount = mResourceIds.length * 5;

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(int position) {
            return mResourceIds[position % mResourceIds.length];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyFrame v;
            if (convertView == null) {
                v = new MyFrame(getActivity());
            } else {
                v = (MyFrame) convertView;
            }

            v.setImageResource(mResourceIds[position % mResourceIds.length]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "clicked position:" + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), GraphActivity.class);
                    startActivity(i);
                }
            });


            return v;
        }

        public void addView() {
            mCount++;
            notifyDataSetChanged();
        }
    }

    public class MyListAdapter extends BaseAdapter {
        private ArrayList<CampaignModel> campaignModelArrayList;
        private LayoutInflater inflater;
        private Context context;

        public MyListAdapter(Context context, ArrayList<CampaignModel> campaignModelArrayList) {
            this.campaignModelArrayList = campaignModelArrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return campaignModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.item_user_screen, null, false);
            TextView tvTitle = row.findViewById(R.id.tvTitle);
            TextView tvDays = row.findViewById(R.id.tvDays);
            ImageView ivAudio = row.findViewById(R.id.ivAudio);
            tvTitle.setText(campaignModelArrayList.get(position).getCamp_name());
            tvDays.setText("25 days");
            return row;
        }
    }
}
