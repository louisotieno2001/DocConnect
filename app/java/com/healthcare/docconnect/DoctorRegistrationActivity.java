package com.healthcare.docconnect;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

public class DoctorRegistrationActivity extends AppCompatActivity {
    //Declaring views
    ImageView backArrowImage;
    TextView saveDetailsText;
    EditText institutionEdit;
    EditText specialityEdit;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    public final String DOCTOR_TITLE = "doctor";
    public final String DOC_NAME="doctor";
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        saveDetailsText = findViewById(R.id.save_text);
        institutionEdit = findViewById(R.id.institution_edit);
        specialityEdit = findViewById(R.id.speciality_edit);
 FirebaseApp.initializeApp(this);
        //Adding click listeners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        saveDetailsText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Get input values
                String institution = institutionEdit.getText().toString().trim();
                String speciality = specialityEdit.getText().toString().trim(); 

                //Get the user ID
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                //Initialize the database reference for the user
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
                DatabaseReference userRef = usersRef.child(userId);

                //Save the input values to users profile
                userRef.child("institution").setValue(institution);
                userRef.child("speciality").setValue(speciality);
                userRef.child("title").setValue(DOCTOR_TITLE);
                userRef.child("name").setValue(DOC_NAME);

                //Show success messsages
                Toast.makeText(DoctorRegistrationActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(DoctorRegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        return null;
        }
   }

}