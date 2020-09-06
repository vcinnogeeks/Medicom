package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;

public class IssueResponsesScreen extends AppCompatActivity {

    private RecyclerView responsesList;
    private ArrayList<HashMap<String, Object>> responses;
    private ResponseScreenAdapter responseScreenAdapter;
    private ImageView sendResponse;
    private EditText responseText;
    private FirestoreHandler firestoreHandler;
    private String docId;
    private RelativeLayout responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_responses_screen);

        responsesList = findViewById(R.id.responsesList);
        responsesList.setLayoutManager(new LinearLayoutManager(this));
        sendResponse = findViewById(R.id.sendResponse);
        responseText = findViewById(R.id.responseText);
        responseView = findViewById(R.id.responseView);

        Intent currentIntent = getIntent();
        responses = (ArrayList<HashMap<String, Object>>) currentIntent.getExtras().get("responses");
        docId = (String) currentIntent.getExtras().get("docId");
        responseScreenAdapter = new ResponseScreenAdapter(this, responses);
        responsesList.setAdapter(responseScreenAdapter);
        firestoreHandler = new FirestoreHandler(this);

        sendResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeResponse();
            }
        });

        if (FirestoreHandler.USER_TYPE.equals(FirestoreHandler.PAT_ID))
            responseView.setVisibility(View.GONE);
    }

    private void makeResponse() {
        if (responseText.getText().toString().trim().length() == 0)
            return;

        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("responseDescription", responseText.getText().toString().trim());
        responseData.put("responseDp", "https://us.123rf.com/450wm/nerthuz/nerthuz1608/nerthuz160800059/62345951-caduceus-medical-symbol.jpg?ver=6");
        responseData.put("responseId", firestoreHandler.getUser());
        responseData.put("responseTime", Timestamp.now());

        responses.add(responseData);
        firestoreHandler.addResponse(responses, docId, responseScreenAdapter);
        responseText.setText("");
    }
}