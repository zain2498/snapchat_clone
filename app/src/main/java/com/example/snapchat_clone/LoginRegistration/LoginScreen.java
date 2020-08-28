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

public class LoginScreen extends AppCompatActivity {
    private EditText edt_email, edt_pass;
    private Button btn_login;
    private FirebaseAuth mAuth;
    //it indicates us whenever the user login !!!
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
           // this will called upon whenever the user logedIn or logout
                FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                 if ( user != null){
                     //if the user is login it will directly move onto the mainactivity means main page !
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

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edt_email.getText().toString();
                final String pass = edt_pass.getText().toString();
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // gives the notice to user if the login is not successful
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginScreen.this,"Sorry Login is not Successful ! ",Toast.LENGTH_SHORT).show();
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