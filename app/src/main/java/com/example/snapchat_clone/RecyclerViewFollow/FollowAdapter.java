package com.example.snapchat_clone.RecyclerViewFollow;

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
public class FollowAdapter extends RecyclerView.Adapter<FollowViewHolders> {
    private List<FollowObject> usersList;
    private Context myContext;

    public FollowAdapter(List<FollowObject> usersList, Context myContext) {
        this.usersList = usersList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public FollowViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutVW = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_follower_list, null);
        //instantiating the obj of RcVwholder's class !!!!
        FollowViewHolders rcv = new FollowViewHolders(layoutVW);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowViewHolders holder, int position) {
        // it contains the everydetail of the viewHolder
        holder.tv_email.setText(usersList.get(position).getEmail());

        if (userInformation.user_Following.contains(usersList.get(holder.getLayoutPosition()).getUid())){
            holder.follow.setText("following");
        }else {
            holder.follow.setText("follow");
        }
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(!userInformation.user_Following.contains(usersList.get(holder.getLayoutPosition()).getUid())){
                    holder.follow.setText("following");
                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("following").child(usersList
                            .get(holder.getLayoutPosition()).getUid()).setValue(true);
                }else {
                    holder.follow.setText("follow");
                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("following").child(usersList
                            .get(holder.getLayoutPosition()).getUid()).removeValue();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
