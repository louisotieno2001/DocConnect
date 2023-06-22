package com.healthcare.docconnect.data;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "docconnect.db";
    public static final int DATABASE_VERSION = 1;
    private Context context;
    
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    
    public void onCreate(SQLiteDatabase db){
        // database creation and tables
        User.createTable(db);  // user table
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO write a database upgrade handler 
        onCreate(db);
    }
}