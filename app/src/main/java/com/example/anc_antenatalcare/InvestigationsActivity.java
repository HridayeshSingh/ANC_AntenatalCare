package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InvestigationsActivity extends AppCompatActivity {
    TextView routineTextView, ultrasoundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigations);

        setTitle("Investigations");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        routineTextView = findViewById(R.id.routineTextView);
        ultrasoundTextView = findViewById(R.id.ultrasoundTextView);

        routineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoutineInvestigationsActivity.class));
            }
        });

        ultrasoundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UltrasoundActivity.class));
            }
        });
    }
}
