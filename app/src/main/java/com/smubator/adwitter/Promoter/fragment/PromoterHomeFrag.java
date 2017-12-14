package com.smubator.adwitter.Promoter.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.smubator.adwitter.IntroductionActivity;
import com.smubator.adwitter.Models.FetchChildModel;
import com.smubator.adwitter.Promoter.activity.MainActivity;
import com.smubator.adwitter.R;
import com.smubator.adwitter.SignInAcitivity;
import com.smubator.adwitter.Utils.Utils;
import com.smubator.adwitter.VerificationActivity;
import com.smubator.adwitter.volly.DwitterApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PromoterHomeFrag extends Fragment implements View.OnClickListener {
    private SwipeMenuListView listView;
    private ArrayList<FetchChildModel> fetchChildModels;
    public PromoterHomeAdapter mAppAdapter;
    private Paint p = new Paint();
    //  FloatingActionButton fab;
    private ImageView ivAddChild;
    private View v;
    private StringRequest stringRequest;

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
        super.onCreate(savedInstanceState);

        v = inflater.inflate(R.layout.fragment_promoter_home, container, false);
        ((MainActivity) getActivity()).ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.backimage));
        ((MainActivity) getActivity()).title.setText(getResources().getText(R.string.accounts));
        initViews();
        initListners();

        listView = v.findViewById(R.id.listView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x2D, 0x9C,
                        0xDB)));
                // set item width
                openItem.setWidth((int) getResources().getDimension(R.dimen._40sdp));
                // set item title
                //openItem.setTitle("Open");
                openItem.setIcon(R.drawable.ic_filter);

                // set item title fontsize
                openItem.setTitleSize(14);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                SwipeMenuItem space = new SwipeMenuItem(
                        getActivity());

                space.setWidth((int) getResources().getDimension(R.dimen._10sdp));
                menu.addMenuItem(space);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x2D, 0x9C,
                        0xDB)));
                // set item width
                deleteItem.setWidth((int) getResources().getDimension(R.dimen._50sdp));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);

                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(14);

                deleteItem.setTitle("Delete");

                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        // delete
                        mAppAdapter.removeItem(position);
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        get_child_accounts();
        DwitterApplication.getInstance().addToRequestQueue(stringRequest, "tag_string_req");


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
        ivAddChild.setOnClickListener(this);
    }

    private void initViews() {
        ivAddChild = v.findViewById(R.id.ivAddChild);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddChild:
                Intent i = new Intent(getActivity(), SignInAcitivity.class);
                i.putExtra("addChild", "addChild");
                getActivity().startActivity(i);
                getActivity().finish();

                break;
        }
    }

    class PromoterHomeAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<FetchChildModel> mArrayList;

        public PromoterHomeAdapter(Context context, ArrayList<FetchChildModel> arrayList)

        {
            mContext = context;
            mArrayList = arrayList;

        }

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        public void removeItem(int position) {
            fetchChildModels.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.verify_users_list, parent, false);

            ImageView ivChildImage = rowView.findViewById(R.id.ivChildImage);
            TextView childUserName = rowView.findViewById(R.id.childUserName);
            childUserName.setText("@ " + mArrayList.get(position).getName());
            Picasso.with(mContext).load(mArrayList.get(position).getImageview())
                    .placeholder(R.drawable.mode_selection_content_owner_avatar)
                    .error(R.drawable.mode_selection_content_owner_avatar)
                    .into(ivChildImage);
            return rowView;
        }
    }

    public void get_child_accounts() {
        // final StringRequest strReq = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
        stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    ArrayList<FetchChildModel> mChildList = new ArrayList<>();
                    JSONArray data = jsonObject.optJSONArray("data");
                    if (data.length() != 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.getJSONObject(i);
                            FetchChildModel fetchChildModel = new FetchChildModel();
                            fetchChildModel.setImageview(obj.optString("profile_image"));
                            fetchChildModel.setName(obj.optString("fname"));
                            mChildList.add(fetchChildModel);
                        }
                        mAppAdapter = new PromoterHomeAdapter(getActivity(), mChildList);
                        listView.setAdapter(mAppAdapter);
                    } else {

                    }
//                    DwitterApplication.getInstance().saveString("id",data.optString("id")); //child database id
//                    DwitterApplication.getInstance().saveString("account_type",data.optString("account_type"));                     DwitterApplication.getInstance().saveString("id",data.optString("id")); //child database id
//                    DwitterApplication.getInstance().saveString("twitter_id",data.optString("twitter_id"));
//                    DwitterApplication.getInstance().saveString("oauth_token",data.optString("oauth_token"));
//                    DwitterApplication.getInstance().saveString("device_id",data.optString("device_id"));
//                    DwitterApplication.getInstance().saveString("parent_id",data.optString("parent_id"));
//                    DwitterApplication.getInstance().saveString("device_type",data.optString("device_type"));
//                    DwitterApplication.getInstance().saveString("fname",data.optString("fname"));
//                    DwitterApplication.getInstance().saveString("lname",data.optString("lname"));
//                    DwitterApplication.getInstance().saveString("gender",data.optString("gender"));
//                    DwitterApplication.getInstance().saveString("age",data.optString("age"));
//                    DwitterApplication.getInstance().saveString("email",data.optString("email"));
//                    DwitterApplication.getInstance().saveString("country",data.optString("country"));
//                    DwitterApplication.getInstance().saveString("state",data.optString("state"));
//                    DwitterApplication.getInstance().saveString("city",data.optString("city"));
//                    DwitterApplication.getInstance().saveString("language",data.optString("language"));
//                    DwitterApplication.getInstance().saveString("category",data.optString("category"));
//                    DwitterApplication.getInstance().saveString("profile_image",data.optString("profile_image"));
//                    DwitterApplication.getInstance().saveString("joining_date",data.optString("joining_date"));
//                    DwitterApplication.getInstance().saveString("status",data.optString("status"));
//                    DwitterApplication.getInstance().saveString("user_status",data.optString("user_status"));

                    Log.i("", "");
                    Toast.makeText(getActivity(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("action", Utils.Get_Child_Accounts);

                MyData.put("parent_id", DwitterApplication.getInstance().useString("user_id"));

                return MyData;
            }
        };
    }
}
