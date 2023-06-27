package com.healthcare.docconnect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.List;
import java.util.ArrayList;


public class MessagesActivity extends AppCompatActivity{
    //Declaring views
    private ImageView backArrowImage;
    private RecyclerView recView;
    private MyAdapter messageAdapter;
    private List<String> messages;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        
        //Initializing views
        backArrowImage = findViewById(R.id.back_arrow);
        recView = findViewById(R.id.recyclerView);
        
        messageAdapter = new MyAdapter(MessagesActivity.this, messages);
        messages = new ArrayList<>();

        messages.add("Hello");
        messages.add("How are you?");
        messages.add("I'm fine, thank you!");
      


        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(messageAdapter);


        //Clicklisteners
        backArrowImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }

        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> messages;
    private Context context;

    public MyAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), ChatsActivity.class);
                startActivity(intent);
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = messages.get(position);
        holder.textView.setText(data);
        holder.profileImage.setBackgroundResource(R.drawable.ic_profile);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.messages_view);
            profileImage = itemView.findViewById(R.id.profile_pic);
        }  
    }
}
}