package com.example.snapchat_clone.RecyclerViewReciever;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchat_clone.R;
import com.example.snapchat_clone.userInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

// it choices what should be placed inside the recycler view and it is very important
public class RecieverAdapter extends RecyclerView.Adapter<RecieverViewHolders> {
    private List<RecieverObject> usersList;
    private Context myContext;

    public RecieverAdapter(List<RecieverObject> usersList, Context myContext) {
        this.usersList = usersList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public RecieverViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutVW = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_receiver_item, null);
        //instantiating the obj of RcVwholder's class !!!!
        RecieverViewHolders rcv = new RecieverViewHolders(layoutVW);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecieverViewHolders holder, int position) {
        // it contains the everydetail of the viewHolder
        holder.mEmail.setText(usersList.get(position).getEmail());
        holder.mReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean receiver = !usersList.get(holder.getLayoutPosition()).getRecieve();
                usersList.get(holder.getLayoutPosition()).setRecieve(receiver);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
