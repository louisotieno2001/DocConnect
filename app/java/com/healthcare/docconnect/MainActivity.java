package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

import com.healthcare.docconnect.RegistrationCallback;

public class MainActivity extends AppCompatActivity  implements RegistrationCallback{
    // Declaring views
    EditText phoneEdit;
    EditText localityEdit;
    TextView nextStepText;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    public final String TITLE = "patient";
    public final String NAME = "patient";
    public final String DOC_NAME = "doctor";
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 FirebaseApp.initializeApp(this);
        // Initializing views
        phoneEdit = findViewById(R.id.phone_edit);
        localityEdit = findViewById(R.id.locality_edit);
        nextStepText = findViewById(R.id.next_step);

        // Handling data input in the EditText
        nextStepText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from EditText fields
                String phoneNumber = phoneEdit.getText().toString().trim();
                String locality = localityEdit.getText().toString().trim();

                // Get the user ID
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Initialize the DatabaseReference for the user
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
                DatabaseReference userRef = usersRef.child(userId);

                // Save the input values to the user's profile in Firebase Realtime Database
                userRef.child("phoneNumber").setValue(phoneNumber);
                userRef.child("locality").setValue(locality);
                userRef.child("title").setValue(TITLE);
                userRef.child("name").setValue(NAME);

               
                // Show a success message or perform any additional actions
                Toast.makeText(MainActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();

               // Invoke the callback method in MainActivity
               if (MainActivity.this instanceof RegistrationCallback) {
                   ((RegistrationCallback) MainActivity.this).onRegistrationSuccess(userId);
               }
            }
        });
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            return userId;
        } else {
            // User is not authenticated or session expired
            // Handle the situation accordingly
            return null;
        }
    }

     @Override
    public void onRegistrationSuccess(String userId) {
    // Transition to the HomeActivity
    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
    // Pass the user ID to the HomeActivity
    intent.putExtra("userId", userId);
    startActivity(intent);
    finish(); // Optional, if you want to close the MainActivity after transitioning
    }
}
  
