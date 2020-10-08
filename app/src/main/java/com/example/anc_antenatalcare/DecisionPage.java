package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DecisionPage extends AppCompatActivity {

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
    }
}
