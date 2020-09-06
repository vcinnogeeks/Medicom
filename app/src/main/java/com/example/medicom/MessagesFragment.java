package com.example.medicom;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagesFragment extends Fragment {

    private RecyclerView inboxRecycler;
    private FirestoreHandler firestoreHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_messages, container, false);
        inboxRecycler = rootView.findViewById(R.id.inboxRecycler);
        firestoreHandler = new FirestoreHandler(getContext());

        firestoreHandler.getChats(inboxRecycler);

        return rootView;
    }
}