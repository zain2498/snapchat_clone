package com.example.snapchat_clone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class ShowCaptureImage extends AppCompatActivity {

    String Uid;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture_image);


        try {
            bitmap = BitmapFactory.decodeStream(getApplication().openFileInput("imageToSend"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finish();
            return;
        }

        ImageView mImage = findViewById(R.id.ImageCaptured);
        mImage.setImageBitmap(bitmap);

        Uid = FirebaseAuth.getInstance().getUid();

        Button mSend = findViewById(R.id.btn_sendImage);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), showReciever_screen.class);
                startActivity(intent);
                return;
            }
        });


    }
}