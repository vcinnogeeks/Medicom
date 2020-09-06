package com.example.medicom;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private ArrayList<String> messages;
    private Context context;
    private String chatDoc;

    MessagesAdapter(Context context, ArrayList<String> messages) {
        this.messages = messages;
        this.context = context;
    }

    void refreshMessages(ArrayList<String> messages) {
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    void setChatDoc(String chatDoc) {
        this.chatDoc = chatDoc;
    }

    String getChatDoc() {
        return chatDoc;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private EditText messageText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }

        public void bind(int position) {
            String userMessage = messages.get(position);
            if (userMessage.startsWith("*(pat)*")){
                userMessage = userMessage.substring(userMessage.indexOf("*(pat)*") + 7);
                messageText.setBackground(context.getResources().getDrawable(R.drawable.message_back_white));

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)messageText.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                messageText.setLayoutParams(params);
            }
            else
                userMessage = userMessage.substring(userMessage.indexOf("*(doc)*") + 7);

            if (userMessage.contains("appointment")){
                userMessage =  "appointment fixed " + userMessage.substring(userMessage.indexOf("appointment")+11);
                messageText.setBackground(context.getResources().getDrawable(R.drawable.rounded_button_grad_blue));
                messageText.setTextColor(Color.WHITE);
                messageText.setPadding(10, 0, 10, 0);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)messageText.getLayoutParams();
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                messageText.setLayoutParams(params);
            }
            messageText.setText(userMessage);
        }
    }
}
