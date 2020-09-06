package com.example.medicom;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FakeNewsAdapter extends RecyclerView.Adapter<FakeNewsAdapter.ViewHolder> {

    Context context;
    ArrayList<String> fakenews;

    public FakeNewsAdapter(Context context, ArrayList<String> fakenews) {
        this.context = context;
        this.fakenews = fakenews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fake_news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fakenewsText.setText(fakenews.get(position));

    }

    @Override
    public int getItemCount() {
        return fakenews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fakenewsText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fakenewsText=itemView.findViewById(R.id.news_text);
        }
    }
}
