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
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class SettingsActivity extends AppCompatActivity{
   //Declaring views
   ImageView backArrowImage;
   ImageView profileImage;
   EditText userNameEdit;
   EditText phoneNumberEdit;
   EditText localityEdit;
   TextView  saveSettingText;
   RecyclerView recView;
   private static final int REQUEST_CODE_IMAGE = 1;
   private static final int REQUEST_CODE_PERMISSION = 2;
  
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
        recView = findViewById(R.id.recyclerView);

        List<String> items = new ArrayList<>(); // Add your data here
        MyAdapter adapter = new MyAdapter(this, items);
        
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);
   
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
                Toast.makeText(SettingsActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        
    
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> items;
    private Context context;

    public MyAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.id.profile_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = items.get(position);
        holder.profileView.setBackgroundResource(R.drawable.ic_profile);
        holder.nameText.setText(item);
        holder.nameEdit.setText(item);
        holder.phoneText.setText(item);
        holder.phoneEdit.setText(item);
        holder.localityText.setText(item);
        holder.localityEdit.setText(item);
        holder.saveText.setText(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileView;
        public TextView nameText;
        public EditText nameEdit;
        public TextView phoneText;
        public EditText phoneEdit;
        public TextView localityText;
        public EditText localityEdit;
        public TextView saveText;

        public ViewHolder(View v) {
            super(v);
            profileView   = v.findViewById(R.id.profile_pic);
            nameText      = v.findViewById(R.id.name_text);
            nameEdit      = v.findViewById(R.id.name_edit);
            phoneText     = v.findViewById(R.id.number_text);
            phoneEdit     = v.findViewById(R.id.phone_edit);
            localityText  = v.findViewById(R.id.locality_text);
            localityEdit  = v.findViewById(R.id.locality_edit);
            saveText      = v.findViewById(R.id.save_text);
        }

    }
 
}

}

   
