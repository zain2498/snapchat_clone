package com.example.snapchat_clone.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snapchat_clone.MainActivity;
import com.example.snapchat_clone.LoginRegistration.Main_selection;
import com.google.firebase.auth.FirebaseAuth;

public class Splash_Screen extends AppCompatActivity {

    public static boolean started = false;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //contains all info associated with the user !!!
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            //if the user is login it will directly move onto the mainactivity means main page !
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            //user has enter previous credential will be removed !!!
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            return;
        }else
        {
            // if user is not login it move on to the choose login/reg activity
            Intent i = new Intent(getApplicationContext(), Main_selection.class);
            //user has enter previous credential will be removed !!!
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            return;
        }
    }
}
