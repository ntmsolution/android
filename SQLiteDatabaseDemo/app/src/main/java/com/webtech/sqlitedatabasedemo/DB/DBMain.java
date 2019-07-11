package com.webtech.sqlitedatabasedemo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMain extends SQLiteOpenHelper {


    public DBMain(Context c) {
        super(c, "DB101", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists user  (id integer primary key, username text, password text, firstname text,lastname text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "drop table user";
        db.execSQL(sql);

        this.onCreate(db);
    }
}
