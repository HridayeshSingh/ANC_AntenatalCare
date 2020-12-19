package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class InvestigationsActivity extends AppCompatActivity {
    TextView routineTextView, ultrasoundTextView;

    private String mode;
    private String selectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigations);

        setTitle("Investigations");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        routineTextView = findViewById(R.id.routineTextView);
        ultrasoundTextView = findViewById(R.id.ultrasoundTextView);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        routineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RoutineInvestigationsActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });

        ultrasoundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UltrasoundActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
    }
}
