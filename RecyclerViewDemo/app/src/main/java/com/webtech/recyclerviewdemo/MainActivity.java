package com.webtech.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context c;

    String[] heading = {"TV","Mobile","Fridge"};
    String[] description = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
    "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC."};

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = MainActivity.this;
        rv = findViewById(R.id.rv);

        Custom adapter = new Custom();
        
        //RecyclerView.LayoutManager lm = new LinearLayoutManager(c);
        RecyclerView.LayoutManager lm = new GridLayoutManager(c,2);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    class MyAd extends RecyclerView.Adapter<MyAd.ViewHolder>{

        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }


        @NonNull
        @Override
        public MyAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAd.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    class Custom extends RecyclerView.Adapter<Custom.ViewHolder>{

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView tvheading,tvdescription;

            public ViewHolder(@NonNull View v) {
                super(v);

                tvheading = v.findViewById(R.id.tv_heading);
                tvdescription = v.findViewById(R.id.tv_description);
            }
        }

        @NonNull
        @Override
        public Custom.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(c).inflate(R.layout.singleitem,viewGroup,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull Custom.ViewHolder holder, int position) {
            holder.tvheading.setText(heading[position]);
            holder.tvdescription.setText(description[position]);
        }

        @Override
        public int getItemCount() {
            return heading.length;
        }
    }
}
