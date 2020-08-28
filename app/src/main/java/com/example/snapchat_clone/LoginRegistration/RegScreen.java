package com.example.snapchat_clone.LoginRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snapchat_clone.MainActivity;
import com.example.snapchat_clone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegScreen extends AppCompatActivity {
    private EditText edt_username,edt_email, edt_pass;
    private Button btn_regis;
    private FirebaseAuth mAuth;
    //it indicates us whenever the user login !!!
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_screen);

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // this will called upon whenever the user logIn or logout
                FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                if ( user != null){
                    //if the user is login it will directly move onto the mainActivity means main page !
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //user has enter previous credential will be removed !!!
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
        //handlers
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_regis = (Button) findViewById(R.id.btn_reg);
        // set on click on reg button
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edt_username.getText().toString().trim();
                final String email = edt_email.getText().toString().trim();
                final String pass = edt_pass.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegScreen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // gives the notice to user if the login is not successful
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplication(),"Sorry Login is not Successful ! ",Toast.LENGTH_SHORT).show();
                        }else {
                            String userID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserData = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

                            Map userInfo = new HashMap<>();
                            userInfo.put("username", username);
                            userInfo.put("email", email);
                            userInfo.put("profileImageUrl","default");

                            currentUserData.updateChildren(userInfo);
                        }
                    }
                });

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}