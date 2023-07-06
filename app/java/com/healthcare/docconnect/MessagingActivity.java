package com.healthcare.docconnect;

import android.os.Bundle;
import android.net.Uri;
import android.Manifest;
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
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;
import java.util.ArrayList;
import android.widget.Toast;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.util.Log;

public class MessagingActivity extends AppCompatActivity{
//Declaring views
private ImageView backArrowImage;
private ImageView sendMessageIcon;
private ImageView attachIcon;
private RecyclerView recView;
private EditText messageInput;
private MyAdapter messageAdapter;
private List<String> messages;
public static final int RC_PHOTO_PICKER = 1001;

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

        messages = new ArrayList<>();

        messages.add("Hello");
        messages.add("How are you?");
        messages.add("I'm fine, thank you!");
        messageAdapter = new MyAdapter(MessagingActivity.this, messages);

        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(messageAdapter);

        sendMessageIcon.setOnClickListener(new View.OnClickListener() {
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

        // ImagePickerButton shows an image picker to upload a image for a message
        attachIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              
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


}