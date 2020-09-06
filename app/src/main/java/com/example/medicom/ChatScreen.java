package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class ChatScreen extends AppCompatActivity {

    private Toolbar messagesToolbar;
    private ImageView sendMessage;
    private RecyclerView messagesRecycler;
    private EditText userMessage;
    private FirestoreHandler firestoreHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        messagesToolbar = findViewById(R.id.messagesToolbar);
        messagesRecycler = findViewById(R.id.messagesRecycler);
        sendMessage = findViewById(R.id.sendMessage);
        userMessage = findViewById(R.id.userMessage);
        firestoreHandler = new FirestoreHandler(this);

        Intent currentIntent = getIntent();
        String pat = (String) currentIntent.getExtras().get("pat");
        String doc = (String) currentIntent.getExtras().get("doc");
        String type = (String) currentIntent.getExtras().get("type");

        String maskedUser = "";
        if (type.equals("ANON")) {
            maskedUser = pat;
            pat = (String) currentIntent.getExtras().get("maskedUser");
        }

        if (FirestoreHandler.USER_TYPE.equals(FirestoreHandler.PAT_ID))
            messagesToolbar.setTitle(doc);
        else
            messagesToolbar.setTitle(pat);

        setSupportActionBar(messagesToolbar);

        final MessagesAdapter messagesAdapter = new MessagesAdapter(this, new ArrayList<String>());
        messagesRecycler.setLayoutManager(new LinearLayoutManager(this));
        messagesRecycler.setAdapter(messagesAdapter);

        firestoreHandler.connectChat(doc, pat, messagesAdapter, type, maskedUser);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = userMessage.getText().toString().trim();
                if (message.length() != 0) {
                    firestoreHandler.sendMessage(message, messagesAdapter);
                    userMessage.setText("");
                }
            }
        });
    }
}