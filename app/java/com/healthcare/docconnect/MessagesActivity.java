package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesActivity extends AppCompatActivity{
    //Declaring views
    ImageView backArrowImage;
    RecyclerView recView;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        
        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        recView = findViewById(R.id.recyclerView);

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
