package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private ImageView backArrowImage; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        backArrowImage = findViewById(R.id.back_arrow);

        //Clicklisteners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }

        });
    } 
}