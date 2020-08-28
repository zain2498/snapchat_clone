package com.example.snapchat_clone.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchat_clone.R;
import com.example.snapchat_clone.RecyclerViewStories.StoryAdapter;
import com.example.snapchat_clone.RecyclerViewStories.StoryObject;
import com.example.snapchat_clone.userInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoryFragment extends Fragment {

    private RecyclerView myRecyclerVw;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    Button btn_refresh;

    public static StoryFragment newInstance(){
        StoryFragment storyFragment = new StoryFragment();
        return storyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw;
        vw = inflater.inflate(R.layout.fragment_story,container,false);


        myRecyclerVw =  (RecyclerView) vw.findViewById(R.id.recylcerVw);
        myRecyclerVw.setNestedScrollingEnabled(false);
        myRecyclerVw.setHasFixedSize(false);
        myLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerVw.setLayoutManager(myLayoutManager);
        myAdapter = new StoryAdapter(getDataSet(),getContext());
        myRecyclerVw.setAdapter(myAdapter);

        btn_refresh = (Button) vw.findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
                listenForData();
            }
        });
        return vw;
    }
    private void Clear() {
        int size = this.results.size();
        this.results.clear();
        myAdapter.notifyItemRangeChanged(0 , size);
    }


    private ArrayList<StoryObject> results  = new ArrayList<>();
    private ArrayList<StoryObject> getDataSet() {
        listenForData();
        return results;
    }

    private void listenForData() {
        for (int i = 0; i < userInformation.user_Following.size(); i++) {
            DatabaseReference db_followingStory = FirebaseDatabase.getInstance().getReference().child("users").child(userInformation.user_Following.get(i));
            db_followingStory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = snapshot.child("email").getValue().toString();
                    String Uid = snapshot.getRef().getKey();
                    long timestampBeg = 0;
                    long timestampEnd = 0;
                    for (DataSnapshot storySnapshot : snapshot.child("story").getChildren()){
                        if (storySnapshot.child("timestampBeg").getValue() != null){
                            timestampBeg = Long.parseLong(storySnapshot.child("timestampBeg").getValue().toString());
                        }
                        if (storySnapshot.child("timestampEnd").getValue() != null){
                            timestampEnd = Long.parseLong(storySnapshot.child("timestampEnd").getValue().toString());
                        }
                        // to get the current time
                        long timestampCurrent = System.currentTimeMillis();
                        if (timestampCurrent >= timestampBeg && timestampCurrent <= timestampEnd){
                            StoryObject obj = new StoryObject(email,Uid, "story");
                            if (!results.contains(obj)) {
                                results.add(obj);
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}
