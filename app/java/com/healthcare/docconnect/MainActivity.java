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
              
              Intent intent = new Intent(MainActivity.this, HomeActivity.class);
              startActivity(intent);

              Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_SHORT);
            }
        });
    }
   
}
  
