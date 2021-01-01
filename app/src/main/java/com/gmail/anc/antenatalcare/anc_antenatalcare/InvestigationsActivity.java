package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InvestigationsActivity extends AppCompatActivity {

    private Button routineButton, ultrasoundButton;

    private String mode;
    private String selectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigations);

        setTitle("Investigations");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        routineButton = findViewById(R.id.routineButton);
        ultrasoundButton = findViewById(R.id.ultrasoundButton);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");
    }

    public void routine(View view){
        Intent intent = new Intent(getApplicationContext(), RoutineInvestigationsActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void ultrasound(View view){
        Intent intent = new Intent(getApplicationContext(), UltrasoundActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
