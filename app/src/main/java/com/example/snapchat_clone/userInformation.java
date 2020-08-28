package com.example.snapchat_clone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class userInformation {

    private FirebaseAuth mAuth ;

    public static ArrayList<String> user_Following = new ArrayList<>();

    public void startFetching(){
        user_Following.clear();
        getUserFollowing();
    }

    private void getUserFollowing() {
        DatabaseReference db_userFollowing = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).child("following");
        db_userFollowing.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    String Uid;
                    Uid = snapshot.getRef().getKey();
                    if (Uid != null && !user_Following.contains(Uid)){
                        user_Following.add(Uid);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String Uid;
                    Uid = snapshot.getRef().getKey();
                    if (Uid != null){
                        user_Following.remove(Uid);
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
