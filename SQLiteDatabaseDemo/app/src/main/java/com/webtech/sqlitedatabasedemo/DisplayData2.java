package com.webtech.sqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.webtech.sqlitedatabasedemo.DB.DBMain;

import java.util.ArrayList;

public class DisplayData2 extends AppCompatActivity {

    Context c;
    ListView lv;
    DBMain objdb ;
    ArrayList<ContentValues> ar;
    /*String[] id,fname,lname,username,password;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        c = DisplayData2.this;
        lv = findViewById(R.id.lv);
        objdb = new DBMain(c);


        /*
        id = new String[cursor.getCount()];
        fname = new String[cursor.getCount()];
        lname = new String[cursor.getCount()];
        username = new String[cursor.getCount()];
        password = new String[cursor.getCount()];
        int i =0;

        while (cursor.moveToNext()){

                id[i] = ""+cursor.getInt(0);
                fname[i] = cursor.getString(3);
                lname[i] = cursor.getString(4);
                username[i] = cursor.getString(1);
                password[i] = cursor.getString(2);

               i++
        }

            ArrayAdapter adapter = new ArrayAdapter(c,android.R.layout.simple_list_item_1,ar);
            lv.setAdapter(adapter);
        */

        displaydata();


    }

    class Custom extends BaseAdapter{

        @Override
        public int getCount() {
            return ar.size();
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
            v = LayoutInflater.from(c).inflate(R.layout.ad_singledata,viewGroup,false);

            TextView tvfname = v.findViewById(R.id.tv_fname);
            TextView tvlname = v.findViewById(R.id.tv_lname);
            TextView tvusername = v.findViewById(R.id.tv_username);
            TextView tvpassword = v.findViewById(R.id.tv_password);

            Button btndelete = v.findViewById(R.id.btn_delete);
            Button btnedit = v.findViewById(R.id.btn_edit);

            final ContentValues cv = ar.get(i);

            tvfname.setText(cv.getAsString("fname"));
            tvlname.setText(cv.getAsString("lname"));
            tvusername.setText(cv.getAsString("username"));
            tvpassword.setText(cv.getAsString("password"));

            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = objdb.getWritableDatabase();
                    long id = db.delete("user","id="+cv.getAsInteger("id"),null);

                    if(id!=-1){
                        Toast.makeText(c,"Record Deleted Successfully",Toast.LENGTH_SHORT).show();
                        displaydata();
                    }

                }
            });

            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = objdb.getReadableDatabase();
                    Cursor cursor = db.rawQuery("select * from user where id="+cv.getAsInteger("id"),null);
                    cursor.moveToFirst();

                    Bundle bundle = new Bundle();
                    bundle.putInt("id",cursor.getInt(0));
                    bundle.putString("fname",cursor.getString(3));
                    bundle.putString("lname",cursor.getString(4));
                    bundle.putString("username",cursor.getString(1));
                    bundle.putString("password",cursor.getString(2));

                    Intent i = new Intent(c,MainActivity.class);
                    i.putExtra("data",bundle);
                    startActivity(i);


                }
            });

            return v;
        }
    }

    void displaydata(){
        ar = new ArrayList();
        SQLiteDatabase db = objdb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user",null);

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                ContentValues cv = new ContentValues();
                cv.put("id",cursor.getInt(0));
                cv.put("fname",cursor.getString(3));
                cv.put("lname",cursor.getString(4));
                cv.put("username",cursor.getString(1));
                cv.put("password",cursor.getString(2));

                ar.add(cv);

            }

            Custom adapter = new Custom();
            lv.setAdapter(adapter);
        }
    }
}
