package com.example.medicom;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<IssueObject> issueList=new ArrayList<>();
    private ArrayList<String> docIds = new ArrayList<>();
    private FirestoreHandler firestoreHandler;
    private RecyclerView newsListRecycler;

    NewsFeedAdapter(Context context, QuerySnapshot issuesList, RecyclerView newsListRecycler) {
        this.context = context;
        this.newsListRecycler = newsListRecycler;
        parseIssues(issuesList);
        firestoreHandler = new FirestoreHandler(context);
    }

    private void parseIssues(QuerySnapshot issuesList) {
        for(DocumentSnapshot issue: issuesList){
            issueList.add(new IssueObject(issue));
            docIds.add(issue.getId());
        }
    }
    public void addContent(IssueObject issueObject){
        issueList.add(0,issueObject);
        this.notifyItemInserted(0);
        newsListRecycler.scrollToPosition(0);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_feed_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView postImage;
        private TextView postUsername, postTime, postDescription, answersCount;
        private Button consultPrivate, consultPublic, showAnswers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.postImage);
            postUsername = itemView.findViewById(R.id.postUsername);
            postTime = itemView.findViewById(R.id.postTime);
            postDescription = itemView.findViewById(R.id.postDescription);
            answersCount = itemView.findViewById(R.id.answersCount);
            consultPrivate = itemView.findViewById(R.id.consultPrivate);
            consultPublic = itemView.findViewById(R.id.consultPublic);
            showAnswers = itemView.findViewById(R.id.showAnswers);

            if (FirestoreHandler.USER_TYPE.equals(FirestoreHandler.PAT_ID)) {
                consultPublic.setVisibility(View.GONE);
                consultPrivate.setVisibility(View.GONE);
                showAnswers.setVisibility(View.VISIBLE);
            }

        }

        public void bind(final int position) {
            final IssueObject currentIssue = issueList.get(position);
            new FirestoreHandler(context).setImage(postImage, currentIssue.getUserDp());
            postUsername.setText(currentIssue.getUserId());
            postTime.setText(DateUtils.getRelativeTimeSpanString(currentIssue.getTime().getSeconds() * 1000));
            postDescription.setText(currentIssue.getDescription());
            answersCount.setText(String.valueOf(currentIssue.getResponses().size()));

            consultPrivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chatIntent = new Intent(context, ChatScreen.class);
                    chatIntent.putExtra("doc", firestoreHandler.getUser());
                    chatIntent.putExtra("pat", currentIssue.getUserId());
                    chatIntent.putExtra("type", "NORM");
                    context.startActivity(chatIntent);
                }
            });

            consultPublic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent responseIntent = new Intent(context, IssueResponsesScreen.class);
                    responseIntent.putExtra("responses", currentIssue.getResponses());
                    responseIntent.putExtra("docId", docIds.get(position));

                    context.startActivity(responseIntent);
                }
            });

            showAnswers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    consultPublic.callOnClick();
                }
            });
        }

    }
}
