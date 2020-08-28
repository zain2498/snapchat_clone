package com.example.snapchat_clone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.snapchat_clone.RecyclerViewFollow.FollowAdapter;
import com.example.snapchat_clone.RecyclerViewFollow.FollowObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FindUser extends AppCompatActivity {

    private EditText finduser;
    private Button search;

    private RecyclerView myRecyclerVw;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

            // handlers----
        finduser = (EditText) findViewById(R.id.edt_findUser);
        search = (Button)findViewById(R.id.btn_search);


        myRecyclerVw =  (RecyclerView) findViewById(R.id.recylcerVw);
        myRecyclerVw.setNestedScrollingEnabled(false);
        myRecyclerVw.setHasFixedSize(false);
        myLayoutManager = new LinearLayoutManager(getApplication());
        myRecyclerVw.setLayoutManager(myLayoutManager);
        myAdapter = new FollowAdapter(getDataSet(),getApplication());
        myRecyclerVw.setAdapter(myAdapter);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Clear();
                    listenForData();
                }
            });

    }


    private void listenForData() {
        DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = db_user.orderByChild("email").startAt(finduser.getText().toString()).endAt(finduser.getText().toString() + "\uf8ff");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email = "";
                String Uid = snapshot.getRef().getKey();
                if (snapshot.child("email").getValue() != null){
                    email = snapshot.child("email").getValue().toString();
                }
                if (!email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                   FollowObject users_obj = new FollowObject(email,Uid);
                   results.add(users_obj);
                   myAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Clear() {
        int size = this.results.size();
        this.results.clear();
        myAdapter.notifyItemRangeChanged(0 , size);
    }


    private ArrayList<FollowObject> results  = new ArrayList<>();
    private ArrayList<FollowObject> getDataSet() {
        listenForData();
        return results;
    }
}