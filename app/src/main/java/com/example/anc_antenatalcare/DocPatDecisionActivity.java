package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DocPatDecisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_pat_decision);

        setTitle("Doctor / Patient");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView doctorTextView = findViewById(R.id.doctorTextView);
        TextView patientTextView = findViewById(R.id.patientTextView);

        final int signUpInKey = getIntent().getIntExtra("signUpInKey", 1);

        doctorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signUpInKey == 0) {
                    startActivity(new Intent(getApplicationContext(), SignupDoctorActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginDoctorActivity.class));
                }
            }
        });

        patientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signUpInKey == 0) {
                    startActivity(new Intent(getApplicationContext(), SignupPatientActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginPatientActivity.class));
                }
            }
        });
    }
}
