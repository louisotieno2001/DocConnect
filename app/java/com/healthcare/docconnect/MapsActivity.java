package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MapsActivity extends AppCompatActivity {
      ImageView backArrowImage;

    //sk.eyJ1Ijoib2NoaWVuZzIwMDEiLCJhIjoiY2xqdWFyMnM5MDRmczNlcXozcnptc3ZoNCJ9.tUi4VQ4ej9B5jCUWoLkh8Q

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        
        backArrowImage = findViewById(R.id.back_arrow);

        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                startActivity(intent);
                finish();
            }
        });

     
    }

   
}
