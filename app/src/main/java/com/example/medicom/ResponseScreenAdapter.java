package com.example.medicom;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseScreenAdapter extends RecyclerView.Adapter<ResponseScreenAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, Object>> responses;

    ResponseScreenAdapter(Context context, ArrayList<HashMap<String, Object>> responses) {
        this.context = context;
        this.responses = responses;
    }

    public void updateResponses(ArrayList<HashMap<String, Object>> responses) {
        this.responses = responses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.response_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView responseImage;
        private TextView responseUsername, responseTime, responseDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            responseImage = itemView.findViewById(R.id.postImage);
            responseUsername = itemView.findViewById(R.id.postUsername);
            responseTime = itemView.findViewById(R.id.postTime);
            responseDescription = itemView.findViewById(R.id.postDescription);
        }

        public void bind(int position) {
            IssueResponseObject currentResponse = new IssueResponseObject(responses.get(position));
            new FirestoreHandler(context).setImage(responseImage, currentResponse.getResponseDp());
            responseUsername.setText(currentResponse.getResponseId());
            responseTime.setText(DateUtils.getRelativeTimeSpanString(currentResponse.getResponseTime().getSeconds() * 1000));
            responseDescription.setText(currentResponse.getResponseDescription());
        }
    }
}
