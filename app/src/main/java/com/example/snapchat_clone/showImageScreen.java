package com.example.snapchat_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.snapchat_clone.RecyclerViewStories.StoryObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showImageScreen extends AppCompatActivity {
    String UserId, chatOrStory;

    ImageView img_Userstory;

    private  boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_screen);

        // getting the bundle information which we have created in the StoryVWholder classs;
        Bundle b = getIntent().getExtras();
        UserId = b.getString("userId");

        //we are getting the image which is sent to the user from another user
        chatOrStory= b.getString("chatOrStory");

        img_Userstory = (ImageView) findViewById(R.id.img_story);

        switch (chatOrStory){
            case "chat":
                listenForChat();
                break;
            case "story":
                listenForStory();
        }

    }

    private void listenForChat() {
        final DatabaseReference db_chat = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("received").child(UserId);
        db_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = "";

                for (DataSnapshot chatSnapshot : snapshot.child("story").getChildren()){
                    if (chatSnapshot.child("profileImageUrl").getValue() != null){
                        imageUrl = chatSnapshot.child("profileImageUrl").getValue().toString();
                    }
                        if (!started){
                            started = true ;
                            InitializeDisplay();
                        }
                        //once user watch the story it will remove automatically !!!!
                        db_chat.child(chatSnapshot.getKey()).removeValue();
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    ArrayList<String> imageUrl_list = new ArrayList<>();
    private void listenForStory() {
            DatabaseReference db_followingStory = FirebaseDatabase.getInstance().getReference().child("users").child(UserId);
            db_followingStory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String imageUrl = "";
                    long timestampBeg = 0;
                    long timestampEnd = 0;
                    for (DataSnapshot storySnapshot : snapshot.child("story").getChildren()){
                        if (storySnapshot.child("timestampBeg").getValue() != null){
                            timestampBeg = Long.parseLong(storySnapshot.child("timestampBeg").getValue().toString());
                        }
                        if (storySnapshot.child("timestampEnd").getValue() != null){
                            timestampEnd = Long.parseLong(storySnapshot.child("timestampEnd").getValue().toString());
                        }
                        if (storySnapshot.child("profileImageUrl").getValue() != null){
                            imageUrl = storySnapshot.child("profileImageUrl").getValue().toString();
                        }
                        // to get the current time
                        long timestampCurrent = System.currentTimeMillis();
                        if (timestampCurrent >= timestampBeg && timestampCurrent <= timestampEnd){
                            imageUrl_list.add(imageUrl);
                            if (!started){
                                started = true ;
                                InitializeDisplay();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

    }
    private  int imageIterator = 0;
    // automatically loaded all the images that the user posted in its story and here we used the glide library for that !!!!!
    private void InitializeDisplay() {
        Glide.with(getApplication()).load(imageUrl_list.get(imageIterator)).into(img_Userstory);

        img_Userstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });
        final Handler handler = new Handler();
        final  int delay = 5000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            // function call here CHANGE_IMAGE ---
                changeImage();
            // HANDLER CALL AGAIN SO THAT PIC ITERATES IN TIME UNTIL IT REACHES TILL THE END ---
                handler.postDelayed(this, delay);
            }
        },delay);
    }
    // method for changing the image
    private void changeImage() {
        if (imageIterator == imageUrl_list.size() -1 ){
            finish();
            return;
        }
        imageIterator ++ ;
        // this will load the next image automatically
        Glide.with(getApplication()).load(imageUrl_list.get(imageIterator)).into(img_Userstory);

    }
}