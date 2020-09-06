package com.example.medicom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private Context context;
    private List<DocumentSnapshot> chats;

    InboxAdapter(Context context, QuerySnapshot chats) {
        this.context = context;
        this.chats = chats.getDocuments();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void addChat(DocumentSnapshot anonymousDoc) {
        Log.d("Size", String.valueOf(chats.size()));
        chats.add(anonymousDoc);
        Log.d("Size", String.valueOf(chats.size()));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private RelativeLayout chatItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            chatItem = itemView.findViewById(R.id.chatItem);
        }

        public void bind(int position) {
            DocumentSnapshot currentChat = chats.get(position);
            String userId = (String) currentChat.get("chatIdDoc");
            final String docId = userId.substring(0, userId.indexOf("_" ));
            final String patId = userId.substring(userId.indexOf("_") + 1);

            if (FirestoreHandler.USER_TYPE.equals(FirestoreHandler.PAT_ID))
                userId = docId;
            else
                userId = patId;

            userName.setText(userId);

            chatItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chatIntent = new Intent(context, ChatScreen.class);
                    chatIntent.putExtra("doc", docId);
                    chatIntent.putExtra("pat", patId);
                    chatIntent.putExtra("type", "NORM");
                    context.startActivity(chatIntent);
                }
            });
        }
    }
}
