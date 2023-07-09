package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Context;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class AppointmentsActivity extends AppCompatActivity{
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
            Intent intent = new Intent(v.getContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
       });

    addAppointmentText.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout); 
        Drawable drawable = getResources().getDrawable(R.drawable.rounded_shape);
        // Create TextView for "Date"
        TextView dateTextView = new TextView(AppointmentsActivity.this);
        dateTextView.setText("Date");
        dateTextView.setTextSize(16);
        linearLayout.addView(dateTextView);

        
        // Create EditText for "Date"
        EditText dateEditText = new EditText(AppointmentsActivity.this);
        linearLayout.addView(dateEditText);
        dateEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_date, 0, 0, 0); // Set the drawable here
        dateEditText.setCompoundDrawablePadding(16);
        dateEditText.setPadding(16, 8, 16, 8);
        // Set background using rounded_shape drawable
        if (drawable instanceof GradientDrawable) {
            dateEditText.setBackground(drawable);
        }

        // Create TextView for "Time"
        TextView timeTextView = new TextView(AppointmentsActivity.this);
        timeTextView.setText("Time");
        timeTextView.setTextSize(16);
        linearLayout.addView(timeTextView);

        // Create EditText for "Time"
        EditText timeEditText = new EditText(AppointmentsActivity.this);
        timeEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0); // Set the drawable here
        timeEditText.setCompoundDrawablePadding(16);
        timeEditText.setPadding(16, 8, 16, 8);
        // Set background using rounded_shape drawable
        if (drawable instanceof GradientDrawable) {
            timeEditText.setBackground(drawable);
        }

        linearLayout.addView(timeEditText);

        // Create TextView for "Doctor"
        TextView doctorTextView = new TextView(AppointmentsActivity.this);
        doctorTextView.setText("Doctor");
        doctorTextView.setTextSize(16);
        linearLayout.addView(doctorTextView);

        // Create EditText for "Doctor"
        EditText doctorEditText = new EditText(AppointmentsActivity.this);
        doctorEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0); // Set the drawable here
        doctorEditText.setCompoundDrawablePadding(16);
        doctorEditText.setPadding(16, 8, 16, 8);
        // Set background using rounded_shape drawable
        if (drawable instanceof GradientDrawable) {
            doctorEditText.setBackground(drawable);
        }
        linearLayout.addView(doctorEditText);
        }
     });

    }
    
}
