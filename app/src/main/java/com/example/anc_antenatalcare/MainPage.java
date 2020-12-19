package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {

    private TextView patientDetails, pregnancyDetails, investigation, treatmentDetails, complaints, appointmentDates, imageSample;
    private String mode, selectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        InitializeFields();

        patientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientDetails.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
        pregnancyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PregnancyDetailsActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
        investigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InvestigationsActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
        appointmentDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppointmentDatesActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
        imageSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageSamplesActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComplaintsActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
            }
        });
        treatmentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TreatmentActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("selectedPatient", selectedPatient);
                startActivity(intent);
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
                Toast.makeText(this, "You are in Feedback now!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainPage.this, FeedbackActivity.class));
                return true;
            case R.id.help:
                Toast.makeText(this, "Item selected: help",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainPage.this, Help.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "You are Logged out!",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainPage.this, SignUpInDecisionActivity.class));
                return true;
            default:
                return false;
        }

    }

}
