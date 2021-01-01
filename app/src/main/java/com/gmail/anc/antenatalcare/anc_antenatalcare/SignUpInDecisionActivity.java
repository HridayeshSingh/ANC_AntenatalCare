package com.gmail.anc.antenatalcare.anc_antenatalcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpInDecisionActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference rootRef;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_in_decision);

        setTitle("Sign Up / Login");

        intent = new Intent(getApplicationContext(), DocPatDecisionActivity.class);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
    }

    public void signUp(View view){
        intent.putExtra("signUpInKey", 0);
        startActivity(intent);
    }
    public void login(View view){
        intent.putExtra("signUpInKey", 1);
        startActivity(intent);
    }
}
