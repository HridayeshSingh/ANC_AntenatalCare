package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TreatmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        setTitle("Treatment Details Of patient");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
