package com.example.medicom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NeedHelpAdapter extends RecyclerView.Adapter<NeedHelpAdapter.MyViewHolder>{

    private Context context;
    private QuerySnapshot signals;
    private FirestoreHandler firestoreHandler;

    public NeedHelpAdapter(Context context, QuerySnapshot queryDocumentSnapshots) {
        this.context = context;
        this.signals = queryDocumentSnapshots;
        firestoreHandler = new FirestoreHandler(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stress_signal_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return signals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private Button helpUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            helpUser = itemView.findViewById(R.id.helpUser);
        }

        public void bind(int position) {
            final DocumentSnapshot currentSignal = signals.getDocuments().get(position);
            helpUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chatIntent = new Intent(context, ChatScreen.class);
                    chatIntent.putExtra("doc", firestoreHandler.getUser());
                    chatIntent.putExtra("pat", (String) currentSignal.get("initiator"));
                    chatIntent.putExtra("type", "ANON");
                    chatIntent.putExtra("maskedUser", (String) currentSignal.get("user"));
                    context.startActivity(chatIntent);
                }
            });
        }
    }
}
