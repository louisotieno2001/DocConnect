package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private List<String> doctorNames;
    public final String NAME = "patient";
    public final String DOC_NAME = "doctor";
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
 FirebaseApp.initializeApp(this);
        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get the current user ID
        String userId = getCurrentUserId();

        // Check the user's title in the database
        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userTitle = dataSnapshot.child("title").getValue(String.class);
                    if (userTitle.equals("patient")) {
                        // User is a patient, populate contacts activity layout with doctors matching locality

                        // Get the user's locality
                        String userLocality = dataSnapshot.child("locality").getValue(String.class);

                        // Query the database for doctors with matching locality and title
                        Query doctorsQuery = databaseReference.child("users").orderByChild("title").equalTo("doctor");
                        doctorsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    doctorNames = new ArrayList<>();
                                    for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
                                        String doctorLocality = doctorSnapshot.child("locality").getValue(String.class);
                                        if (doctorLocality.equals(userLocality)) {
                                            // Add doctor's name to the list
                                            String doctorName = doctorSnapshot.child("name").getValue(String.class);
                                            doctorNames.add(doctorName);
                                        }
                                    }

                                    // Populate the contacts activity layout with doctor names
                                    populateContactsLayout(doctorNames);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle onCancelled event
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            // User is not authenticated or session expired
            // Handle the situation accordingly
            return null;
        }
    }

    private void populateContactsLayout(List<String> doctorNames) {
        // Programmatically populate the contacts activity layout

        // Get the layout container
        LinearLayout contactsLayout = findViewById(R.id.contactsLayout);

        // Clear existing views
        contactsLayout.removeAllViews();

        // Create and add views for each doctor name
        for (String doctorName : doctorNames) {
            // Create a new TextView for each doctor
            TextView textViewDoctor = new TextView(this);
            textViewDoctor.setText(doctorName);

            // Add the TextView to the contacts layout
            contactsLayout.addView(textViewDoctor);
        }
    }
}
