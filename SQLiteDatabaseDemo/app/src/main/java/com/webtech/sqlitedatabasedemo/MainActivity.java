package com.webtech.sqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.webtech.sqlitedatabasedemo.DB.DBMain;

public class MainActivity extends AppCompatActivity {

    Context c;
    EditText edtfname,edtlname,edtusername,edtpassword;
    DBMain objdb;
    Button btnsubmit,btndisplay,btnedit;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = MainActivity.this;
        edtfname = findViewById(R.id.edtfname);
        edtlname = findViewById(R.id.edtlname);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        btnsubmit = findViewById(R.id.btnsubmit);
        btndisplay = findViewById(R.id.btndisplay);
        btnedit = findViewById(R.id.btnedit);

        objdb = new DBMain(c);

        Intent i = getIntent();
        if(i.getBundleExtra("data")!=null){
            btnedit.setVisibility(View.VISIBLE);
            btnsubmit.setVisibility(View.GONE);

            Bundle bundle = i.getBundleExtra("data");

            id = bundle.getInt("id");
            edtfname.setText(bundle.getString("fname"));
            edtlname.setText(bundle.getString("lname"));
            edtusername.setText(bundle.getString("username"));
            edtpassword.setText(bundle.getString("password"));
        }


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("firstname",edtfname.getText().toString());
                cv.put("lastname",edtlname.getText().toString());
                cv.put("username",edtusername.getText().toString());
                cv.put("password",edtpassword.getText().toString());


                edtfname.setText("");
                edtlname.setText("");
                edtusername.setText("");
                edtpassword.setText("");

                SQLiteDatabase db = objdb.getWritableDatabase();
                long id = db.insert("user",null,cv);

                if(id!=-1){
                    Toast.makeText(c,"Record inserted Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(c,"Record not inserted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = objdb.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("firstname",edtfname.getText().toString());
                cv.put("lastname",edtlname.getText().toString());
                cv.put("username",edtusername.getText().toString());
                cv.put("password",edtpassword.getText().toString());


                int recid = db.update("user",cv,"id="+id,null);

                if(recid!=-1){
                    Toast.makeText(c,"Record Updated Successfully",Toast.LENGTH_SHORT).show();

                    edtfname.setText("");
                    edtlname.setText("");
                    edtusername.setText("");
                    edtpassword.setText("");

                    btnedit.setVisibility(View.GONE);
                    btnsubmit.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(c,"Record not Updated",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btndisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,DisplayData.class);
                startActivity(i);
            }
        });

    }
}
