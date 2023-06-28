package com.healthcare.docconnect;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.util.Log;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    // Duration of splash screen in milliseconds
    private static final long SPLASH_DURATION = 3000; // 3 seconds
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Simulating user verification in the background
        boolean isUserVerified = verifyUserInBackground();

        // Create a handler to delay the transition to the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class<?> nextActivity;

                // Check if the user is verified
                if (isUserVerified) {
                    nextActivity = HomeActivity.class;
                } else {
                    nextActivity = MainActivity.class;
                }

                // Start the next activity
                Intent intent = new Intent(SplashActivity.this, nextActivity);
                startActivity(intent);

                // Finish the splash activity
                finish();
            }
        }, SPLASH_DURATION);
    }

    private boolean verifyUserInBackground() {
       
        // Return true if the user is verified, false otherwise
       
        //storage reference
        StorageReference storageReference;

        //Creating instance of firebase database
        storageReference = FirebaseStorage.getInstance().getReference();

        //Getting user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Initialize the DatabaseReference for the user
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference userRef = usersRef.child(userId);
        getCurrentUserId();
        return true;
    }

     private String getCurrentUserId() {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                  String userId = currentUser.getUid();
                   return userId;
          } else {
        // User is not authenticated or session expired
        // Handle the situation accordingly
        Toast.makeText(SplashActivity.this, "Create an account", Toast.LENGTH_SHORT).show();
        return null;
        }
    }
}
