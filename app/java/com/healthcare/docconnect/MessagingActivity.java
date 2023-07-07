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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

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
  private StorageReference storageReference;
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

    messageAdapter = new MyAdapter(MessagingActivity.this, messages);
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

    // ImagePickerButton shows an image picker to upload a image for a message
    attachIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        requestExternalStorage();
      }
    });

    // Functionalizing views by adding click listeners
    backArrowImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
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
    return null;
  }

  private void requestExternalStorage() {
    Dexter.withActivity(this)
        .withPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE)
        .withListener(new PermissionListener() {
          @Override
          public void onPermissionGranted(PermissionGrantedResponse response) {
            Intent inte = new Intent(Intent.ACTION_GET_CONTENT);
            inte.setType("image/jpeg");
            inte.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(Intent.createChooser(inte, "Complete Action Using"), RC_PHOTO_PICKER);

          }

          @Override
          public void onPermissionDenied(PermissionDeniedResponse response) {
            // check for permanent denial of any permission
            if (response.isPermanentlyDenied()) {
              // show alert dialog navigating to Settings
              Toast.makeText(MessagingActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
          }
        })
        .withErrorListener(new PermissionRequestErrorListener() {
          @Override
          public void onError(DexterError error) {
            Log.e("Dexter", "There was an error: " + error.toString());
          }
        })
        .onSameThread()
        .check();
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
