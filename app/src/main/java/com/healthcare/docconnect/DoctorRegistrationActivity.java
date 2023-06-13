package com.healthcare.docconnect;

import android.os.Bundle;
import ndroid.widget.View;
import android.content.Intent;
import android.widget.ImageView;
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
               Intent intent = new Intent(getActivity(), HomeActivity.class);
               startActivity(intent);
            }
        });

        saveDetailsText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            
                //New text to take plac od "Done"
                public void changeText(){
                   //Text that appears after  handling of data in backend 
                   String newText;
                   newText = findViewById(R.id.save_text);
                   //Setting the new text
                   newText.setText("Done!");
                } 
            }
        });
    }
}