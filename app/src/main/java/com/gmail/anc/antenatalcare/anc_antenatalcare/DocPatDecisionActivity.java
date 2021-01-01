package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class DocPatDecisionActivity extends AppCompatActivity {

    private int signUpInKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_pat_decision);

        setTitle("Doctor / Patient");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        signUpInKey = getIntent().getIntExtra("signUpInKey", 1);

    }

    public void doctorClicked(View view){
        if(signUpInKey == 0) {
            startActivity(new Intent(getApplicationContext(), SignupDoctorActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginDoctorActivity.class));
        }
    }

    public void patientClicked(View view){
        if(signUpInKey == 0) {
            startActivity(new Intent(getApplicationContext(), SignupPatientActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginPatientActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
