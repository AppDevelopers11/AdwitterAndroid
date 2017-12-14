package com.smubator.adwitter.Promoter.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.smubator.adwitter.R;

import java.util.ArrayList;

public class PromoterHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private SwipeMenuListView listView;
    private ArrayList<String> mUserList;
    private PromoterHomeAdapter mAppAdapter;
    private Paint p = new Paint();
    //  FloatingActionButton fab;
    private View view;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_home);

        listView = findViewById(R.id.listView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        mUserList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            mUserList.add(i + ":" + "UserName");
        }

        mAppAdapter = new PromoterHomeAdapter(this, mUserList);
        listView.setAdapter(mAppAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x2D, 0x9C,
                        0xDB)));
                // set item width
                openItem.setWidth(100);
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
                        getApplicationContext());

                space.setWidth(20);
                menu.addMenuItem(space);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x2D, 0x9C,
                        0xDB)));
                // set item width
                deleteItem.setWidth(170);
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
                        Toast.makeText(PromoterHomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        // delete
                        mAppAdapter.removeItem(position);
                        Toast.makeText(PromoterHomeActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    class PromoterHomeAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<String> mArrayList;

        public PromoterHomeAdapter(Context context, ArrayList<String> arrayList)

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
            mUserList.remove(position);
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
            return rowView;
        }
    }
}
