package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatsActivity extends AppCompatActivity{
   //Declaring views
   ImageView backArrowImage;
   ImageView sendMessageIcon;
   ImageView attachIcon;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        
        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        sendMessageIcon = findViewById(R.id.icon_send);
        attachIcon = findViewById(R.id.icon_attach);

        //Functionalizing views by adding click listeners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent intent = new Intent(v.getContext(), MessagesActivity.class);
               startActivity(intent);
            }
        });

       
    }
}
