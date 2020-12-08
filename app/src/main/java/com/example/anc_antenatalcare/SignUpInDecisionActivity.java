package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpInDecisionActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference RootRef;
    Intent intent;

    public void signUp(View view){
        intent.putExtra("signUpInKey", 0);
        startActivity(intent);
    }
    public void login(View view){
        intent.putExtra("signUpInKey", 1);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_in_decision);

        setTitle("Sign Up / Login");

        intent = new Intent(getApplicationContext(), DocPatDecisionActivity.class);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser != null) {
            SendUserToMainPageActivity();
        }
    }

    private void SendUserToMainPageActivity() {
        Intent mainPageIntent = new Intent(SignUpInDecisionActivity.this, MainPage.class);
        mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainPageIntent);
        finish();
    }

}
