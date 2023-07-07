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

    // Delay navigation to the next activity
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        // Start the next activity after the splash duration
        navigateToNextActivity();
      }
    }, SPLASH_DURATION);
  }

  private void navigateToNextActivity() {
    // Determine the next activity based on your logic
    // For example, if the user is already logged in, navigate to HomeActivity
    // If the user needs to log in, navigate to AuthActivity

    ParseUser user = ParseUser.getCurrentUser();
    if (user == null) {
      Intent intent = new Intent(this, AuthActivity.class);
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, HomeActivity.class);
      startActivity(intent);
    }

    // Close the splash activity
    finish();
  }
}