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
    private ImageView messagesImage;
    private ImageView localityImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        backArrowImage = findViewById(R.id.back_arrow);
        messagesImage = findViewById(R.id.icon_chat);
        localityImage = findViewById(R.id.icon_locality);

        //Clicklisteners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }

        });

        messagesImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =  new Intent(v.getContext(), MessagingActivity.class );
                startActivity(intent);
                finish();
            }
        });

        localityImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    } 
}