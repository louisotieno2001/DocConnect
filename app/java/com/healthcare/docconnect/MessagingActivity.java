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


import com.parse.ParseUser;

public class MessagingActivity extends AppCompatActivity implements View.OnClickListener {
  // Declaring views
  private ImageView backArrowImage;
  private ImageView sendMessageIcon;
  private ImageView attachIcon;
  private RecyclerView recView;
  private EditText messageInput;
  private MyAdapter messageAdapter;
  private List<String> messages;
  public static final int RC_PHOTO_PICKER = 1001;

  ParseUser user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chats);
    // Initializing views
    messageInput = findViewById(R.id.editTextMessage);
    backArrowImage = findViewById(R.id.back_arrow);
    sendMessageIcon = findViewById(R.id.icon_send);
    attachIcon = findViewById(R.id.icon_attach);
    recView = findViewById(R.id.listViewMessages);

    user = ParseUser.getCurrentUser();

    messages = new ArrayList<>();
    messages.add("Hello, Still up for our appointment??");
   
    messageAdapter = new MyAdapter(MessagingActivity.this, messages);


    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    recView.setLayoutManager(layoutManager);
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
    
    // Functionalizing views by adding click listeners
    backArrowImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MessagesActivity.class);
        startActivity(intent);
        finish();
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
      View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
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
        textView = itemView.findViewById(R.id.messages_view);
      }
    }
  }

  private String getCurrentUserId() {
    return null;
  }

  @Override
  public void onClick(View v) {
    int TAG = (int) v.getTag();

    switch (TAG) {

      default:
        break;
    }
  }
}
