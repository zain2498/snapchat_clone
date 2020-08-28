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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private RecyclerView myRecyclerVw;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    Button btn_refresh;

    public static ChatFragment newInstance(){
    ChatFragment chatFragment = new ChatFragment();
    return chatFragment;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw;
        vw = inflater.inflate(R.layout.fragment_chat,container,false);

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
        DatabaseReference db_received = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("received");
        db_received.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap: snapshot.getChildren()){
                        getUserInfo(snap.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getUserInfo(String key) {
        DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String email = snapshot.child("email").getValue().toString();
                    String Uid = snapshot.getRef().getKey();

                    StoryObject obj = new StoryObject(email,Uid,"chat");
                    if (!results.contains(obj)){
                        results.add(obj);
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

}
