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



public class DoctorRegistrationActivity extends AppCompatActivity {
    //Declaring views
    ImageView backArrowImage;
    TextView saveDetailsText;
    EditText institutionEdit;
    EditText specialityEdit;
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
                //Show success messsages
                Toast.makeText(DoctorRegistrationActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
            }
        });

    }


}