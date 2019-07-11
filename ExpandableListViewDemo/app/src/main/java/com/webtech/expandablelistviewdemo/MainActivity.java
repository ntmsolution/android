package com.webtech.expandablelistviewdemo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context c;
    String[] heading = {
            "Gujarati",
            "Punjabi",
            "Chiness",
            "Italian"
    };
    String[][] items = {
        {"Dalbhat","Khichadi","Sev Tameta","Bhakhri"},
        {"Paneer Kadai","Paneer Tufani","Paneer Angara","Paneer Handi"},
        {"Manchuriyan Dry","Meggi"},
        {"Veg Pizza","Veg Spicy","Magerita","American Thin Crux"}
    };

    ExpandableListView exlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = MainActivity.this;
        exlv = findViewById(R.id.exlv);

        Custom adapter = new Custom();
        exlv.setAdapter(adapter);
    }

    class Custom extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return heading.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return items[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View v, ViewGroup viewGroup) {

            v = LayoutInflater.from(c).inflate(R.layout.ad_headinglayout,viewGroup,false);

            TextView tvheading = v.findViewById(R.id.tvheading);

            tvheading.setText(heading[i]);

            return v;
        }

        @Override
        public View getChildView(int row, int column, boolean b, View v, ViewGroup viewGroup) {
            v = LayoutInflater.from(c).inflate(R.layout.ad_childlayout,viewGroup,false);

            TextView tvitem = v.findViewById(R.id.tvitem);

            tvitem.setText(items[row][column]);

            return v;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }
}
