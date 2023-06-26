package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;
import java.util.ArrayList;
import android.widget.Toast;
import androidx.annotation.Nullable;
import android.provider.MediaStore;

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

public class ChatsActivity extends AppCompatActivity{
//Declaring views
private ImageView backArrowImage;
private ImageView sendMessageIcon;
private ImageView attachIcon;
private RecyclerView recView;
private EditText messageInput;
private MyAdapter messageAdapter;
private List<String> messages;
private StorageReference storageReference;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        
        //Initializing views
        messageInput = findViewById(R.id.editTextMessage);
        backArrowImage = findViewById(R.id.back_arrow);
        sendMessageIcon = findViewById(R.id.icon_send);
        attachIcon = findViewById(R.id.icon_attach);
        recView = findViewById(R.id.listViewMessages);

        //Creating instance of firebase database
        storageReference = FirebaseStorage.getInstance().getReference();

        //Getting user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Initialize the DatabaseReference for the user
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference userRef = usersRef.child(userId);

        messageAdapter = new MyAdapter(ChatsActivity.this, messages);
        messages = new ArrayList<>();

        messages.add("Hello");
        messages.add("How are you?");
        messages.add("I'm fine, thank you!");

        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(messageAdapter);

        sendMessageIcon.setOnClickListener(new View.OnClickListener() {
             // Get the user ID
            String userId = getCurrentUserId();
            
            @Override
            public void onClick(View v) {
                String message = messageInput.getText().toString().trim();
                if (!message.isEmpty()) {
                    messages.add(message);
                    messageAdapter.notifyDataSetChanged();
                    messageInput.setText("");
                    recView.scrollToPosition(messages.size() - 1);
                }
            }
        });

        //Functionalizing views by adding click listeners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent intent = new Intent(v.getContext(), MessagesActivity.class);
               startActivity(intent);
            }
        });

       
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> messages;
    private Context context;

    public MyAdapter(Context context, List<String> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_chats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = messages.get(position);
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_message_view);
        }
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
        Intent intent = new Intent(ChatsActivity.this, MainActivity.class);
        startActivity(intent);
        return null;
        }
    }
}
