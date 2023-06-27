package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    // Declaring views
    CardView contactsCard;
    CardView messagesCard;
    CardView settingsCard;
    CardView registerDoctorCard;
    CardView appointmentsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initializing views
        contactsCard = findViewById(R.id.contacts_card);
        messagesCard = findViewById(R.id.messages_card);
        settingsCard = findViewById(R.id.settings_card);
        registerDoctorCard = findViewById(R.id.doctor_registration_card);
        appointmentsCard = findViewById(R.id.appointments_card);

        // Functionality of views by adding intents
        contactsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });

        messagesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MessagingActivity.class);
                startActivity(intent);
            }
        });

        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        registerDoctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DoctorRegistrationActivity.class);
                startActivity(intent);
            }
        });

        appointmentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AppointmentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
