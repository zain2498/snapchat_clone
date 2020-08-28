package com.example.snapchat_clone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.snapchat_clone.RecyclerViewFollow.FollowObject;
import com.example.snapchat_clone.RecyclerViewReciever.RecieverAdapter;
import com.example.snapchat_clone.RecyclerViewReciever.RecieverObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showReciever_screen extends AppCompatActivity {

    private RecyclerView myRecyclerVw;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    String Uid;
    Bitmap bitmap;
    CheckBox mStory;
    FloatingActionButton fab ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reciever_screen);

        try {
            bitmap = BitmapFactory.decodeStream(getApplication().openFileInput("imageToSend"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finish();
            return;
        }
        // creating the variable to get the userId from the firebaseDatabase where the userStories are going to be save !!!!!
        Uid = FirebaseAuth.getInstance().getUid();

        myRecyclerVw =  (RecyclerView) findViewById(R.id.recyclerView);
        //myRecyclerVw.setNestedScrollingEnabled(false);
        myRecyclerVw.setHasFixedSize(false);
        myLayoutManager = new LinearLayoutManager(getApplication());
        myRecyclerVw.setLayoutManager(myLayoutManager);
        myAdapter = new RecieverAdapter(getDataSet(),getApplication());
        myRecyclerVw.setAdapter(myAdapter);

        //call the floating action button
        fab = (FloatingActionButton) findViewById(R.id.btn_floating);
        // FAB ON CLICK LISTENER WHERE THE SAVE_STORY METHOD IS CALL
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // CALLING METHOD HERE !!! save_story
                saveStories();
            }
        });

    }

    private ArrayList<RecieverObject> results  = new ArrayList<>();
    private ArrayList<RecieverObject> getDataSet() {
        listenForData();
        return results;
    }
    private void listenForData() {
        for (int i = 0; i < userInformation.user_Following.size(); i++) {
            DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("users").child(userInformation.user_Following.get(i));
            db_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = "";
                    String Uid = snapshot.getRef().getKey();

                    if (snapshot.child("email").getValue() != null) {
                        email = snapshot.child("email").getValue().toString();
                    }
                    RecieverObject receivers_obj = new RecieverObject(email, Uid, false);
                    if (!results.contains(receivers_obj)) {
                        results.add(receivers_obj);
                        myAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        }
    }
    // function in which we are storing the userStories into the firebasedatabase
    private void saveStories() {
        final DatabaseReference db_userStory = FirebaseDatabase.getInstance().getReference().child("users").child(Uid).child("story");
        final String key = db_userStory.push().getKey();
        final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("captures").child(key);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20, baos);

        byte[] dataToUpload = baos.toByteArray();
        //this will upload the image into the firebase
        UploadTask uploadTask = filepath.putBytes(dataToUpload);

        //for the task to be successful
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Long curr_TimeStamp = System.currentTimeMillis();
                        Long end_TimeStamp = System.currentTimeMillis() + (24*60*60*1000);

                        // connecting the checkbox here from show_reciever_story
                        mStory = (CheckBox) findViewById(R.id.chk_story);
                        if (mStory.isChecked()) {
                            Map newImage = new HashMap();
                            newImage.put("profileImageUrl", uri.toString());
                            newImage.put("timestampBeg", curr_TimeStamp);
                            newImage.put("timestampEnd", end_TimeStamp);
                            db_userStory.child(key).setValue(newImage);
                        }
                        for (int i =0; i < results.size(); i ++){
                            if (results.get(i).getRecieve()){
                                DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("users").child(results.get(i).getUid())
                                        .child("received").child(Uid);
                                Map newImage = new HashMap();
                                newImage.put("profileImageUrl", uri.toString());
                                newImage.put("timestampBeg", curr_TimeStamp);
                                newImage.put("timestampEnd", end_TimeStamp);
                                db_user.child(key).setValue(newImage);
                            }
                        }
                        // we are calling the intent so that we can get back to MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        return;
                    }
                    //if any error occur while saving the userstory in the database userCan recaptured the picture again !!!!!
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        finish();
                        return;
                    }
                });

            }

        });
    }
}