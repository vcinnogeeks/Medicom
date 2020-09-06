package com.example.medicom;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FakeNewsFragment extends Fragment {
    RecyclerView fakenews;
    ImageView addNews;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    Button searchNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_fake_news,container,false);
        fakenews=rootview.findViewById(R.id.fake_news_recycler);
        addNews=rootview.findViewById(R.id.addIssue);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopup();
            }
        });
        fakenews.setLayoutManager(new LinearLayoutManager(getContext()));
        new FirestoreHandler(getContext()).fetchFakeNews(fakenews);
        return rootview;


    }

    private void openPopup() {
        builder=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.add_news_popup,null);
        builder.setView(view);
        searchNews=view.findViewById(R.id.search_bttn);
        searchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(getContext(), SearchNewsResults.class);
                        startActivity(intent);
                    }
                },1000);

            }
        });
        alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


    }
}