package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  // Declaring views
  EditText phoneEdit;
  EditText localityEdit;
  TextView nextStepText;
  public final String TITLE = "patient";
  public final String NAME = "patient";
  public final String DOC_NAME = "doctor";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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

        // Save the input values to the user's profile in Firebase Realtime Database
        Toast.makeText(MainActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();

        // Invoke the callback method in MainActivity
        /*
         * if (MainActivity.this instanceof RegistrationCallback) {
         * ((RegistrationCallback) MainActivity.this).onRegistrationSuccess(userId);
         * }
         */
      }
    });
  }

  private String getCurrentUserId() {
    return null;
  }

  //@Override
  public void onRegistrationSuccess(String userId) {
    // Transition to the HomeActivity
    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
    // Pass the user ID to the HomeActivity
    intent.putExtra("userId", userId);
    startActivity(intent);
    finish(); // Optional, if you want to close the MainActivity after transitioning
  }
}
