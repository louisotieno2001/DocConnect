package com.healthcare.docconnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.parse.ParseUser;

import android.util.Log;

public class SplashActivity extends AppCompatActivity  {

  // Duration of splash screen in milliseconds
  private static final long SPLASH_DURATION = 3000; // 3 seconds

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    ParseUser user = ParseUser.getCurrentUser();
    if (user == null){
      // Login screen 
       Intent intent = new Intent(this, AuthActivity.class);
       startActivity(intent);
    }else{
       Toast.makeText(this, "Welcome back "+user.getUsername(), Toast.LENGTH_LONG).show();

       Intent intent = new Intent(this, HomeActivity.class);
       startActivity(intent);
    }
    
  }
}
