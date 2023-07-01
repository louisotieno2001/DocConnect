package com.healthcare.docconnect;

import com.google.firebase.FirebaseApp;
import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        // Add other Firebase initialization code here if needed
    }
}
