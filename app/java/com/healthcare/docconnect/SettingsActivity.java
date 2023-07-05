package com.healthcare.docconnect;

import android.widget.TextView;
import android.content.Context;
import android.widget.EditText;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

public class SettingsActivity extends AppCompatActivity{
   //Declaring views
   ImageView backArrowImage;
   ImageView profileImage;
   EditText userNameEdit;
   EditText phoneNumberEdit;
   EditText localityEdit;
   TextView  saveSettingText;
   private static final int REQUEST_CODE_IMAGE = 1;
   private static final int REQUEST_CODE_PERMISSION = 2;
   private StorageReference storageReference;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        profileImage = findViewById(R.id.profile_pic);
        userNameEdit = findViewById(R.id.name_edit);
        phoneNumberEdit = findViewById(R.id.phone_edit);
        localityEdit = findViewById(R.id.locality_edit);
        saveSettingText = findViewById(R.id.save_text);
    FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openImagePicker();
                } else {
                    ActivityCompat.requestPermissions(SettingsActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                }
            }
        });
    

   

        //click listeners and intent setting for back arrow
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

   saveSettingText.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Get the input values from EditText fields
        String userName = userNameEdit.getText().toString().trim();
        String phoneNumber = phoneNumberEdit.getText().toString().trim();
        String locality = localityEdit.getText().toString().trim();

        // Get the user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize the DatabaseReference for the user
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference userRef = usersRef.child(userId);

        // Save the input values to the user's profile in Firebase Realtime Database
        userRef.child("userName").setValue(userName);
        userRef.child("phoneNumber").setValue(phoneNumber);
        userRef.child("locality").setValue(locality);

        // Show a success message or perform any additional actions
        Toast.makeText(SettingsActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
});

        
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            uploadProfilePicture(imageUri);
        }
    }

    private void uploadProfilePicture(Uri imageUri) {
        if (imageUri != null) {
            StorageReference profileImageRef = storageReference.child("profileImages/user.jpg");
            profileImageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(SettingsActivity.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
                            // TODO: Update the user's profile with the new image URL
                            //updateProfileWithImageUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(SettingsActivity.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfileWithImageUrl(String imageUrl) {
    // Get the user ID
    String userId = getCurrentUserId();

    // Check if the user ID is valid
    if (userId != null) {
        // Get a DatabaseReference to the user's profile
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        // Update the profile image URL
        userRef.child("profileImageUrl").setValue(imageUrl)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SettingsActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(SettingsActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });
    } else {
        // Handle the situation when user ID is not available
        Toast.makeText(SettingsActivity.this, "Please create an account first" ,Toast.LENGTH_SHORT).show();
    }
}


    private String getCurrentUserId() {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                  String userId = currentUser.getUid();
                   return userId;
          } else {
        // User is not authenticated or session expired
        // Handle the situation accordingly
        return null;
        }
    }

}

   
