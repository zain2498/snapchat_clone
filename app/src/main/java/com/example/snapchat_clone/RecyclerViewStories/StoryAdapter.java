package com.example.snapchat_clone.RecyclerViewStories;

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
public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolders> {
    private List<StoryObject> storyList;
    private Context myContext;

    public StoryAdapter(List<StoryObject> storyList, Context myContext) {
        this.storyList = storyList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public StoryViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutVW = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_vw_story_item, null);
        //instantiating the obj of RcVwholder's class !!!!
        StoryViewHolders rcv = new StoryViewHolders(layoutVW);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryViewHolders holder, int position) {
        // it contains the every detail of the viewHolder
        holder.tv_Storyemail.setText(storyList.get(position).getEmail());
        holder.tv_Storyemail.setTag(storyList.get(position).getUid());

        holder.my_Layout.setTag(storyList.get(position).getChatOrStory());

    }

    @Override
    public int getItemCount() {
        return this.storyList.size();
    }
}
