package com.healthcare.docconnect.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    Context context;
    SharedPreferences prefs;
    
    public Preferences(Context context, String file){
        this.context = context;
        this.prefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    }
    public static Preferences getPrefs(Context context){
        return new  Preferences(context, "prefs.xml");
    }
    
    public String getUserId(){
        // return user id
        return this.prefs.getString("userId", null);
    }
}