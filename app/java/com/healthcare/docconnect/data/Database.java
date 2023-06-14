package com.healthcare.docconnect.data;

import android.content.Context;

import java.security.MessageDigest;

public class Database  {
    private static Database db = null;
    private Context context;
    private DatabaseHandler handler;
    
    public Database(Context context){
        this.context = context;
        this.handler = new DatabaseHandler(context);
    }
    public static Database getInstance(Context context){
        if(db == null)
            db = new Database(context);
        return db;
    }
    public String createUser(User user){
        if(user == null)
            return null;
            
       return user.insertUser(handler.getWritableDatabase());
    }
    public User getUser(String id){
        return User.getUser(handler.getWritableDatabase(), id);
    }
    
    public static String sha256(String s) throws Exception{
        if(s == null)
           return null;
        byte[] source = s.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(source);
        String hashCode = null;
        
        if(hash != null){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < hash.length; i++){
                String hex = Integer.toHexString(hash[i]);
                if (hex.length() == 1){
                    sb.append('0');
                    sb.append(hex.charAt(0));
                }else{
                    sb.append(hex.substring(hex.length() - 2));
                }
            }
            hashCode = sb.toString();
        }
        return hashCode;
    }
    
}