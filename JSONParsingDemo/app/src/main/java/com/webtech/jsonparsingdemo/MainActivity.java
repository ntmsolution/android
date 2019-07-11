package com.webtech.jsonparsingdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.webtech.jsonparsingdemo.Modal.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView data;
    ImageView loader;
    Context context;
    ArrayList<Category> categorydata = new ArrayList<>();
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = (TextView) findViewById(R.id.data);
        loader = (ImageView) findViewById(R.id.loader);
        rv = (RecyclerView) findViewById(R.id.rv);
        context = MainActivity.this;

        fetchdata();

    }

    void fetchdata(){

        final String apikey = getResources().getString(R.string.apikey);
        String url = getResources().getString(R.string.getcategory);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);

        StringRequest getcategory = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loader.setVisibility(View.GONE);
//                data.setText(response);

                try{
                    JSONObject jobj = new JSONObject(response);

                    if(jobj!=null){
                        JSONArray jarray = jobj.getJSONArray("data");

                        for(int i=0;i<jarray.length();i++){
                            JSONObject j = jarray.getJSONObject(i);

                            String id = j.getString("id");
                            String categoryname = j.getString("category_name");

                            categorydata.add(new Category(id,categoryname));
                        }


                        Custom adapter = new Custom();
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(context);
                        rv.setLayoutManager(lm);
                        rv.setAdapter(adapter);

                    }else{
                        Toast.makeText(context,"Data Not Found",Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Toast.makeText(context,"JSON Object Error",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Sending Information / Data
                Map<String,String> mydata = new HashMap<>();
                mydata.put("api_key",apikey);

                return mydata;
            }
        };

        MyRequestQueue.add(getcategory);// Method Call
    }

    class Custom extends RecyclerView.Adapter<Custom.MyHolder>{

        public class MyHolder extends RecyclerView.ViewHolder {

            TextView tvid,tvcatname;

            public MyHolder(@NonNull View v) {
                super(v);

                tvid = (TextView) v.findViewById(R.id.tvid);
                tvcatname = (TextView) v.findViewById(R.id.tvname);
            }
        }

        @NonNull
        @Override
        public Custom.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(context).inflate(R.layout.singlelayout,viewGroup,false);
            return new MyHolder(v);

        }

        @Override
        public void onBindViewHolder(@NonNull Custom.MyHolder holder, int i) {

            Category data = categorydata.get(i);

            holder.tvid.setText(data.id);
            holder.tvcatname.setText(data.categoryname);
        }

        @Override
        public int getItemCount() {
            return categorydata.size();
        }


    }
}
