package com.webtech.volleylibrarydemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.webtech.volleylibrarydemo.Modal.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Context context;
    ListView lv;
    ArrayList<Category> data = new ArrayList<>();
    String MSG = "==MSG==";
    TextView loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        lv = findViewById(R.id.lv);
        loader = findViewById(R.id.loader);

        fetchdata();
    }

    void fetchdata(){

        final String apikey = getResources().getString(R.string.apikey);
        String url = getResources().getString(R.string.getcategory);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(context); // Create Request Queue

        StringRequest getcategory = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Define your code after fetching the data

                try{

                    if(response!=null){

                        loader.setVisibility(View.GONE);
                        JSONObject jobj = new JSONObject(response);

                        JSONArray jarray = jobj.getJSONArray("data");

                        // Get One By One Data
                        for(int i=0;i<jarray.length();i++){
                            JSONObject j = jarray.getJSONObject(i);

                            String id = j.getString("id");
                            String category_name = j.getString("category_name");

                            data.add(new Category(id,category_name));
                        }

                        // Set Adapter
                        Custom adapter = new Custom();
                        lv.setAdapter(adapter);

                    }else{
                        Log.e(MSG,"Response Return Null");
                    }

                }catch (Exception e){
                    Log.e(MSG,"Error In Success Response "+e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Define your code if any error raised

                Log.e(MSG,"Data not fetched Due to: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("api_key",getResources().getString(R.string.apikey));

                return params;
            }
        };
        MyRequestQueue.add(getcategory);// Method Call
    }

    class Custom extends BaseAdapter{


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
        public View getView(int i, View v, ViewGroup viewGroup) {

            v = LayoutInflater.from(context).inflate(R.layout.adapter_singleitem,viewGroup,false);

            TextView id = v.findViewById(R.id.tvid);
            TextView categoryname = v.findViewById(R.id.tvcategoryname);

            Category catdata = data.get(i);

            id.setText(catdata.id);
            categoryname.setText(catdata.category_name);

            return v;
        }
    }
}
