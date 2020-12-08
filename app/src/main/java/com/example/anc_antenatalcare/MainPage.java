package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {

    TextView patientDetails, pregnancyDetails, investigation, treatmentDetails, complaints, appointmentDates, imageSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        InitializeFields();

        patientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PatientDetails.class));
            }
        });
        pregnancyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PregnancyDetailsActivity.class));
            }
        });
        investigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InvestigationsActivity.class));
            }
        });
        appointmentDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AppointmentDatesActivity.class));
            }
        });
        imageSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ImageSamplesActivity.class));
            }
        });
        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ComplaintsActivity.class));
            }
        });
    }

    private void InitializeFields() {
        patientDetails = findViewById(R.id.textView_patientDetails);
        pregnancyDetails = findViewById(R.id.textView_pregnancyDetails);
        investigation = findViewById(R.id.textView_investigation);
        treatmentDetails = findViewById(R.id.textView_treatmentDetails);
        complaints = findViewById(R.id.textView_complaints);
        appointmentDates = findViewById(R.id.textView_appointmentDates);
        imageSample = findViewById(R.id.textView_imageSample);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.feedback:
                Toast.makeText(this, "You are in Feedback now",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainPage.this, Feedback.class));
                return true;
            case R.id.help:
                Toast.makeText(this, "Item selected: help",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainPage.this, Help.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "You are Logged out",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainPage.this, SignUpInDecisionActivity.class));
                return true;
            default:
                return false;
        }

    }

}
