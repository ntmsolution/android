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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    TextView loader;
    Context context;
    String TAG = "==MSG==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        loader = findViewById(R.id.loader);
        context = MainActivity.this;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASEPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);


        Call<Category> data= service.getCategory(Service.APIKEY);

        try {

            data.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    if(response!=null){
                        loader.setVisibility(View.GONE);
                        List<Category.information> categorydata = response.body().data;
                        Adapter adapter = new Adapter(categorydata);
                        lv.setAdapter(adapter);

                    }else{
                        Toast.makeText(context,"Sorry, Response Return Null",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {
                    Toast.makeText(context,"Sorry data not found",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.e(TAG,"Error = "+e);
        }

    }

    class Adapter extends BaseAdapter{
        List<Category.information> data;

        Adapter(List<Category.information> data){
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
            v = LayoutInflater.from(context).inflate(R.layout.adapter_categorydata,viewGroup,false);

            TextView id = v.findViewById(R.id.tv_id);
            TextView categoryname = v.findViewById(R.id.tv_catname);

            id.setText(""+data.get(i).id);
            categoryname.setText(""+data.get(i).categoryName);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Id = "+data.get(i).id,Toast.LENGTH_SHORT).show();

                    if(data.get(i).id!=null){
                        Intent intent = new Intent(context, SubCategoryData.class);
                        intent.putExtra("catid",data.get(i).id);
                        startActivity(intent);
                    }

                }
            });

            return v;
        }
    }
}
