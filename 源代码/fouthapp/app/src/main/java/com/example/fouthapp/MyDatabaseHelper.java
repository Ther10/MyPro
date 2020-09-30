package com.example.fouthapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table User (" +
            "id integer primary key autoincrement," +
            "userId text," +
            "password text)";

    public static final String CREATE_STUDENT = "create table Student (" +
            "Sno text primary key," +
            "Sname text," +
            "Ssex text," +
            "Sage integer," +
            "Sdept text," +
            "Tclass text," +
            "Score real," +
            "isAttended integer)";

    public static final String CREATE_TEACHER = "create table Teahcer (" +
            "Tno text primary key," +
            "Tname text," +
            "Tsex text," +
            "Tage integer," +
            "Tdept text," +
            "Tclass text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_STUDENT);
        sqLiteDatabase.execSQL(CREATE_TEACHER);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("drop table if exists Student");
        sqLiteDatabase.execSQL("drop table if exists Teacher");
        onCreate(sqLiteDatabase);
    }
}
