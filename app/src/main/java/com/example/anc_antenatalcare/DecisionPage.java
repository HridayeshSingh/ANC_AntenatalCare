package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DecisionPage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public void signUp(View view){
        startActivity(new Intent(getApplicationContext(),SignupActivity.class));
    }
    public void Login(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_page);

        setTitle("Sign Up / Login");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser != null) {
            SendUserToMainPageActivity();
        }
    }

    private void SendUserToMainPageActivity() {
        Intent mainPageIntent = new Intent(DecisionPage.this, MainPage.class);
        mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainPageIntent);
        finish();
    }
}
