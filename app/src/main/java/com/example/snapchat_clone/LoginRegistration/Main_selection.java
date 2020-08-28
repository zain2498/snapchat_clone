package com.example.snapchat_clone.LoginRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.snapchat_clone.LoginRegistration.LoginScreen;
import com.example.snapchat_clone.LoginRegistration.RegScreen;
import com.example.snapchat_clone.R;

public class Main_selection extends AppCompatActivity {

    Button login, registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_selection);

        login = (Button) findViewById(R.id.btn_login);
        registration = (Button) findViewById(R.id.btn_reg);

        // on click listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move onto the login screen  !!!!
                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(i);
                return;
            }
        });

        // reg click
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move onto the reg screen !!!!
                Intent i = new Intent(getApplicationContext(), RegScreen.class);
                startActivity(i);
                return;
            }
        });

    }
}