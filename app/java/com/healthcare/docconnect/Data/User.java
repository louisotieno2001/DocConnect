package com.healthcare.docconnect.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;
import java.sql.SQLException;

import java.util.UUID;

public class User implements BaseColumns, Parcelable {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_LOCALITY = "locality";
    public static final String COLUMN_TITLE = "title";
    private static final String DEFAULT_TITLE = "patient";
    private static final String DOCTOR_TITLE = "doctor";

    private String id;
    private String phone;
    private String locality;
    private String title;

    public User(String phone, String locality) {
        this.id = UUID.randomUUID().toString();
        this.phone = phone;
        this.locality = locality;
        this.title = DEFAULT_TITLE;
    }

    private User(Parcel in) {
        this.id = in.readString();
        this.phone = in.readString();
        this.locality = in.readString();
        this.title = in.readString();
    }

    public static void createTable(SQLiteDatabase db) {
        String SQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_PHONE + " TEXT," +
                COLUMN_LOCALITY + " TEXT," +
                COLUMN_TITLE + " TEXT)";
        db.execSQL(SQL);
    }

    public String insertUser(SQLiteDatabase db) {
        
        String _id = UUID.randomUUID().toString();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_LOCALITY, locality);

        if (title.equals(DOCTOR_TITLE)) {
            values.put(COLUMN_TITLE, DOCTOR_TITLE);
        } else {
            values.put(COLUMN_TITLE, DEFAULT_TITLE);
        }

        
            long id = db.insert(TABLE_NAME, null, values);
            if (id == -1) {
                // Handle insertion error
                new SQLException("Failed to insert user.");
            }
        return _id;
    }

    public static User getUser(SQLiteDatabase db, String id) {
        String[] columns = {COLUMN_ID, COLUMN_PHONE, COLUMN_LOCALITY, COLUMN_TITLE};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {id};
        User user = null;

        try (Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToNext()) {
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String locality = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCALITY));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));

                user = new User(phone, locality);
                user.id = id;
                user.title = title;
            }
        } catch (Exception e) {
            // Handle query error
            Log.e("Error", e.getMessage());
        }

        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(phone);
        dest.writeString(locality);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
