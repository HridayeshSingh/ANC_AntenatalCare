package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class AppointmentDatesActivity extends AppCompatActivity {

    private TextView textView_D1, textView_D2, textView_D3, textView_D4, textView_D5, textView_D6, textView_D7, textView_D8, textView_D9, textView_D10;

    private String CurrentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_dates);

        setTitle("Appointment Dates");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        CurrentUserID = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        RetrieveUserInfo();

    }

    private void InitializeFields() {
        textView_D1 = findViewById(R.id.textView_D1);
        textView_D2 = findViewById(R.id.textView_D2);
        textView_D3 = findViewById(R.id.textView_D3);
        textView_D4 = findViewById(R.id.textView_D4);
        textView_D5 = findViewById(R.id.textView_D5);
        textView_D6 = findViewById(R.id.textView_D6);
        textView_D7 = findViewById(R.id.textView_D7);
        textView_D8 = findViewById(R.id.textView_D8);
        textView_D9 = findViewById(R.id.textView_D9);
        textView_D10 = findViewById(R.id.textView_D10);
    }

    private void RetrieveUserInfo() {
        RootRef.child("appointments").child(CurrentUserID).child("Dates")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.exists())) {
                            String retrieveD1 = snapshot.child("Date-1").getValue().toString();
                            String retrieveD2 = snapshot.child("Date-2").getValue().toString();
                            String retrieveD3 = snapshot.child("Date-3").getValue().toString();
                            String retrieveD4 = snapshot.child("Date-4").getValue().toString();
                            String retrieveD5 = snapshot.child("Date-5").getValue().toString();
                            String retrieveD6 = snapshot.child("Date-6").getValue().toString();
                            String retrieveD7 = snapshot.child("Date-7").getValue().toString();
                            String retrieveD8 = snapshot.child("Date-8").getValue().toString();
                            String retrieveD9 = snapshot.child("Date-9").getValue().toString();
                            String retrieveD10 = snapshot.child("Date-10").getValue().toString();

                            textView_D1.setText(retrieveD1);
                            textView_D2.setText(retrieveD2);
                            textView_D3.setText(retrieveD3);
                            textView_D4.setText(retrieveD4);
                            textView_D5.setText(retrieveD5);
                            textView_D6.setText(retrieveD6);
                            textView_D7.setText(retrieveD7);
                            textView_D8.setText(retrieveD8);
                            textView_D9.setText(retrieveD9);
                            textView_D10.setText(retrieveD10);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            RootRef.child("appointments").child(CurrentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if ((snapshot.child("Dates").exists())) {

                    }
                    else {
                        SendUserToAppointmentDatesDoctorActivity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void SendUserToAppointmentDatesDoctorActivity() {
        Intent appointmentDatesDoctorIntent = new Intent(AppointmentDatesActivity.this, AppointmentDatesDoctorActivity.class);
        appointmentDatesDoctorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(appointmentDatesDoctorIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appointment_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_date){
            Intent intent = new Intent(getApplicationContext(), AppointmentDatesDoctorActivity.class);
            startActivity(intent);

            return true;
        }
        return false;

    }
}
