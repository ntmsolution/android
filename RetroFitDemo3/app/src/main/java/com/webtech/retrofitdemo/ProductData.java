package com.webtech.retrofitdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webtech.retrofitdemo.modal.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductData extends AppCompatActivity {

    ListView lv;
    TextView loader;
    Context context;
    String TAG = "==MSG==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_data);

        context = ProductData.this;
        lv = findViewById(R.id.lv);
        loader = findViewById(R.id.loader);

        if(getIntent().getStringExtra("subcat_id")!=null){
            String subcat_id = getIntent().getStringExtra("subcat_id");
            String subcat_name = getIntent().getStringExtra("subcat_name");

            getSupportActionBar().setTitle(subcat_name);
            fetchdata(subcat_id);
        }

    }

    void fetchdata(String subcat_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASEPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<Product> data= service.getProduct(Service.APIKEY,subcat_id);

        try {
            data.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response!=null){
                        loader.setVisibility(View.GONE);
                        List<Product.information> productdata = response.body().data;
                        Adapter adapter = new Adapter(productdata);
                        lv.setAdapter(adapter);

                    }else{
                        Toast.makeText(context,"Sorry, Response Return Null",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(context,"Sorry data not found",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.e(TAG,"Error = "+e);
        }
    }

    class Adapter extends BaseAdapter {
        List<Product.information> data;

        Adapter(List<Product.information> data){
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
            v = LayoutInflater.from(context).inflate(R.layout.adapter_productdata,viewGroup,false);

            TextView id = v.findViewById(R.id.tv_id);
            TextView product_title = v.findViewById(R.id.tv_producttitle);
            ImageView image1 = v.findViewById(R.id.tv_image1);
            TextView price = v.findViewById(R.id.tv_price);

            id.setText(""+data.get(i).id);
            product_title.setText(data.get(i).product_title);

            price.setText(data.get(i).price);

            Picasso.get()
                    .load(Service.IMGPATH+data.get(i).image1)
                    .placeholder(R.color.colorPrimary)
                    .error(android.R.color.holo_red_dark)
                    .into(image1);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    /*
                    Toast.makeText(context,"Id = "+data.get(i).id,Toast.LENGTH_SHORT).show();

                    if(data.get(i).id!=null){
                        Intent intent = new Intent(context, SubCategoryData.class);
                        intent.putExtra("subcat_id",data.get(i).id);
                        startActivity(intent);
                    }
                    */

                }
            });

            return v;
        }
    }

}
