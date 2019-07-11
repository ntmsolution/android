package com.webtech.retrofitdemo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMain extends SQLiteOpenHelper {


    public DBMain(Context c) {
        super(c, "DB101", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists cart  (id integer primary key, productid integer, productname text, productprice integer,productqty integer)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "drop table cart";
        db.execSQL(sql);

        this.onCreate(db);
    }
}
