package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class SearchNewsResults extends AppCompatActivity {
    CardView trueLink;
    CardView trueLink2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news_results);
        trueLink=findViewById(R.id.true_link);
        trueLink2=findViewById(R.id.true_link2);
        trueLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://economictimes.indiatimes.com/magazines/panache/amitabh-bachchan-tests-negative-for-covid-19-discharged-from-hospital/articleshow/77315673.cms"));
                startActivity(browserIntent);
            }
        });
        trueLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ndtv.com/entertainment/amitabh-bachchan-who-tested-positive-for-covid-19-discharged-from-hospital-2272942"));
                startActivity(browserIntent);
            }
        });
    }
}