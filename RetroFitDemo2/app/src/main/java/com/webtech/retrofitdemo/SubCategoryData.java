package com.webtech.retrofitdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.webtech.retrofitdemo.modal.Category;
import com.webtech.retrofitdemo.modal.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubCategoryData extends AppCompatActivity {

    Context context;
    ListView lv;
    TextView loader;
    String TAG = "==MSG==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        context = SubCategoryData.this;
        lv = findViewById(R.id.lv);
        loader = findViewById(R.id.loader);

        if(getIntent().getStringExtra("catid")!=null){
            String catid = getIntent().getStringExtra("catid");
            fetchdata(catid);
        }

    }

    void fetchdata(String catid){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASEPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<SubCategory> data= service.getSubCategory(Service.APIKEY,catid);

        try {

            data.enqueue(new Callback<SubCategory>() {
                @Override
                public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {
                    if(response!=null){
                        loader.setVisibility(View.GONE);
                        List<SubCategory.information> subcategorydata = response.body().data;
                        Adapter adapter = new Adapter(subcategorydata);
                        lv.setAdapter(adapter);

                    }else{
                        Toast.makeText(context,"Sorry, Response Return Null",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SubCategory> call, Throwable t) {
                    Toast.makeText(context,"Sorry data not found",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.e(TAG,"Error = "+e);
        }
    }

    class Adapter extends BaseAdapter {
        List<SubCategory.information> data;

        Adapter(List<SubCategory.information> data){
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View v, ViewGroup viewGroup) {
            v = LayoutInflater.from(context).inflate(R.layout.adapter_subcategorydata,viewGroup,false);

            TextView id = v.findViewById(R.id.tv_id);
            TextView catid = v.findViewById(R.id.tv_catid);
            TextView subcategoryname = v.findViewById(R.id.tv_subcatname);

            id.setText(""+data.get(i).id);
            catid.setText(""+data.get(i).category_id);
            subcategoryname.setText(""+data.get(i).subcategory_name);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Id = "+data.get(i).id,Toast.LENGTH_SHORT).show();

                    /*if(data.get(i).id!=null){
                        Intent intent = new Intent(context, SubCategoryData.class);
                        intent.putExtra("catid",data.get(i).id);
                        startActivity(intent);
                    }*/

                }
            });

            return v;
        }
    }
}
