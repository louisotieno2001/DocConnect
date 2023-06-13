package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity{
   //Declaring views
   ImageView backArrowImage;
   TextView  addAppointmentText;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        
        //initializing variables
        backArrowImage = findViewById(R.id.back_arrow);
        addAppointmentText = findViewById(R.id.add_text);


       //Functionalizing views by adding click listeners
       backArrowImage.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        }
       });

    addAppointmentText.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout); 

        // Create TextView for "Date"
        TextView dateTextView = new TextView(AppointmentsActivity.this);
        dateTextView.setText("Date");
        linearLayout.addView(dateTextView);

        // Set background using rounded_shape drawable
        dateEditText.setBackground(getResources().getDrawable(R.drawable.rounded_shape)); 
        
        // Create EditText for "Date"
        EditText dateEditText = new EditText(AppointmentsActivity.this);
        linearLayout.addView(dateEditText);

        // Create TextView for "Time"
        TextView timeTextView = new TextView(AppointmentsActivity.this);
        timeTextView.setText("Time");
        linearLayout.addView(timeTextView);

        // Create EditText for "Time"
        EditText timeEditText = new EditText(AppointmentsActivity.this);
        
        // Set background using rounded_shape drawable
        timeEditText.setBackground(getResources().getDrawable(R.drawable.rounded_shape)); 
        linearLayout.addView(timeEditText);

        // Create TextView for "Doctor"
        TextView doctorTextView = new TextView(AppointmentsActivity.this);
        doctorTextView.setText("Doctor");
        linearLayout.addView(doctorTextView);

        // Create EditText for "Doctor"
        EditText doctorEditText = new EditText(AppointmentsActivity.this);
        
        // Set background using rounded_shape drawable
        doctorEditText.setBackground(getResources().getDrawable(R.drawable.rounded_shape)); 
        linearLayout.addView(doctorEditText);
    }
});

    }
}
