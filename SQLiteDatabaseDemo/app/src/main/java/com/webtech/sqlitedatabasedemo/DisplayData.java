package com.webtech.sqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.webtech.sqlitedatabasedemo.DB.DBMain;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {

    Context c;
    ListView lv;
    DBMain objdb ;
    ArrayList<ContentValues> ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        c = DisplayData.this;
        lv = findViewById(R.id.lv);
        objdb = new DBMain(c);

        displaydata();
    }


    void displaydata(){
        ar = new ArrayList();
        SQLiteDatabase db = objdb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user",null);

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                ContentValues cv = new ContentValues();
                cv.put("id",cursor.getInt(0));
                cv.put("username",cursor.getString(1));
                cv.put("password",cursor.getString(2));
                cv.put("fname",cursor.getString(3));
                cv.put("lname",cursor.getString(4));

                ar.add(cv);

            }

            Custom adapter = new Custom(ar);
            lv.setAdapter(adapter);
        }
    }

    class Custom extends BaseAdapter{

        ArrayList<ContentValues> data=new ArrayList<>();

        Custom(ArrayList<ContentValues> data){
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
        public View getView(int position, View v, ViewGroup viewGroup) {

            v = LayoutInflater.from(c).inflate(R.layout.ad_singledata,viewGroup,false);

            TextView tvfname = v.findViewById(R.id.tv_fname);
            TextView tvlname = v.findViewById(R.id.tv_lname);
            TextView tvusername = v.findViewById(R.id.tv_username);
            TextView tvpassword = v.findViewById(R.id.tv_password);
            Button btnedit = v.findViewById(R.id.btn_edit);
            final Button btndelete = v.findViewById(R.id.btn_delete);

            final ContentValues cv = ar.get(position);

            tvfname.setText(cv.getAsString("fname"));
            tvlname.setText(cv.getAsString("lname"));
            tvusername.setText(cv.getAsString("username"));
            tvpassword.setText(cv.getAsString("password"));

            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = objdb.getWritableDatabase();
                    int count = db.delete("user","id="+cv.getAsInteger("id"),null);

                    if(count>0){
                        Toast.makeText(c,"Record Deleted Successfully",Toast.LENGTH_SHORT).show();
                        displaydata();
                    }

                }
            });


            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",cv.getAsInteger("id"));
                    bundle.putString("fname",cv.getAsString("fname"));
                    bundle.putString("lname",cv.getAsString("lname"));
                    bundle.putString("username",cv.getAsString("username"));
                    bundle.putString("password",cv.getAsString("password"));

                    Intent i = new Intent(c,MainActivity.class);
                    i.putExtra("data",bundle);
                    startActivity(i);
                }
            });


            return v;
        }
    }
}
