package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity{
   //Declaring views
   CardView contactsCard;
   CardView messagesCard;
   CardView settingsCard;
   CardView registerDoctorCard;
   CardView appointmentsCard;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initializing views
        contactsCard = findViewById(R.id.contacts_card);
        messagesCard = findViewById(R.id.messages_card);
        settingsCard = findViewById(R.id.settings_card);
        registerDoctorCard = findViewById(R.id.doctor_registration_card);
        appointmentsCard = findViewById(R.id.appointments_card);


        //Functionalizing views
    contactsCard.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ContactsActivity.class);
        startActivity(intent);
    }
    });

    messagesCard.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = new Intent(getActivity(), MessagesActivity.class);
            startActivity(intent);
        }
    });

    settingsCard.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(){
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
    });

    registerDoctorCard.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(){
            Intent intent = new Intent(getActivity(), DoctorRegistration.class);
            startActivity(intent);
        }
    });

    appointmentsCard.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(){
            Intent intent = new Intent(getActivity(), AppointmentsActivity.class);
            startActivity(intent);
        }
    });



    }
}
