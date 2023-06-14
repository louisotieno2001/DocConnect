package com.healthcare.docconnect;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorRegistrationActivity extends AppCompatActivity {
    //Declaring views
    ImageView backArrowImage;
    TextView saveDetailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        saveDetailsText = findViewById(R.id.save_text);

        //Adding click listeners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }

}