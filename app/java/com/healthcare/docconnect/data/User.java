package com.healthcare.docconnect.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;
import java.util.UUID;

public class User implements BaseColumns, Parcelable{
   
    
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_LOCALITY = "locality";
    
    public String _id;
    public String phone;
    public String locality;
    
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel in){
            return new User(in);
        }
        @Override
        public User[] newArray(int size){
            return new User[size];
        }
    };
   
    public User( String phone, String locality){
        this.phone = phone;
        this.locality = locality;
    }
    private User(Parcel in){
        this._id = in.readString();
        this.phone = in.readString();
        this.locality = in.readString();
    }
    public static void createTable(SQLiteDatabase db){
        String SQL = String.format("CREATE TABLE %s ("+
                     "%s TEXT PRIMARY KEY,"+
                     "%s TEXT, "+
                     "%s TEXT)", TABLE_NAME, _ID, COLUMN_PHONE,
                     COLUMN_LOCALITY);
        db.execSQL(SQL);
    }
    public String insertUser(SQLiteDatabase db){
        String hash = null;
        try {
            hash = Database.sha256(password);
        }catch(Exception e){
            // Log
        }
        
        if(hash == null) 
           return null;
           
        String _id = UUID.randomUUID().toString();
        ContentValues values = new ContentValues();
        values.put(_ID, _id);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_LOCALITY, locality);
        
     
        long id = db.insert(TABLE_NAME, null, values);
        if(id == -1)
            return null;
            
        return _id;
    }
    public static User getUser(SQLiteDatabase db, String id){
        String[] columns = {
            
            COLUMN_PHONE,
            COLUMN_LOCALITY,
            _ID
        };
        
        String selection = _ID +" = ?";
        String[] args = {id};
      
        User user = null;
        try(Cursor cursor = db.query(TABLE_NAME, columns, selection, args, null, null, null)){
            if(cursor.moveToNext()){
                String locality = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCALITY));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String _id = cursor.getString(cursor.getColumnIndexOrThrow(_ID));
               
                
                user = new User(phone,locaility, null);
                user._id = id;
            }
        }catch(Exception e){
            
        }
        return user;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: "+_id+", ");
        sb.append("Phone: "+phone);
        sb.append("Locality: "+locaility+", ");
        
        return sb.toString();
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flag){
        out.writeString(_id);
        out.writeString(phone);
        out.writeString(locality);
    }
}