package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity{
   //Declaring views
   ImageView backArrowImage;
   ImageView profileImage;
   EditText userNameEdit;
   EditText phoneNumberEdit;
   EditText localityEdit;
   TextView  saveSettingText;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        profileImage = findViewById(R.id.profile_pic);
        userNameEdit = findViewById(R.id.name_edit);
        phoneNumberEdit = findViewById(R.id.phone_edit);
        localityEdit = findViewById(R.id.locality_edit);
        saveSettingText = findViewById(R.id.save_text);

        //click listeners and intent setting
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        
    }
}
