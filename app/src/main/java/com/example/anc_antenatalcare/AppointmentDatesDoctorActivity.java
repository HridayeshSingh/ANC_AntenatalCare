package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;

public class AppointmentDatesDoctorActivity extends AppCompatActivity {

    private EditText editText_D1, editText_D2, editText_D3, editText_D4, editText_D5, editText_D6, editText_D7, editText_D8, editText_D9, editText_D10;
    private Button updateButton;

    private String CurrentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_dates_doctor);

        setTitle("Appointment Dates");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserID = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        RetrieveUserInfo();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDates();
            }
        });

    }

    private void InitializeFields() {
        editText_D1 = findViewById(R.id.editText_D1);
        editText_D2 = findViewById(R.id.editText_D2);
        editText_D3 = findViewById(R.id.editText_D3);
        editText_D4 = findViewById(R.id.editText_D4);
        editText_D5 = findViewById(R.id.editText_D5);
        editText_D6 = findViewById(R.id.editText_D6);
        editText_D7 = findViewById(R.id.editText_D7);
        editText_D8 = findViewById(R.id.editText_D8);
        editText_D9 = findViewById(R.id.editText_D9);
        editText_D10 = findViewById(R.id.editText_D10);
        updateButton = findViewById(R.id.updateButton);
    }

    private void RetrieveUserInfo(){
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

                            editText_D1.setText(retrieveD1);
                            editText_D2.setText(retrieveD2);
                            editText_D3.setText(retrieveD3);
                            editText_D4.setText(retrieveD4);
                            editText_D5.setText(retrieveD5);
                            editText_D6.setText(retrieveD6);
                            editText_D7.setText(retrieveD7);
                            editText_D8.setText(retrieveD8);
                            editText_D9.setText(retrieveD9);
                            editText_D10.setText(retrieveD10);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void UpdateDates() {
        String setD1 = editText_D1.getText().toString();
        String setD2 = editText_D2.getText().toString();
        String setD3 = editText_D3.getText().toString();
        String setD4 = editText_D4.getText().toString();
        String setD5 = editText_D5.getText().toString();
        String setD6 = editText_D6.getText().toString();
        String setD7 = editText_D7.getText().toString();
        String setD8 = editText_D8.getText().toString();
        String setD9 = editText_D9.getText().toString();
        String setD10 = editText_D10.getText().toString();

        HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("Date-1", setD1);
            profileMap.put("Date-2", setD2);
            profileMap.put("Date-3", setD3);
            profileMap.put("Date-4", setD4);
            profileMap.put("Date-5", setD5);
            profileMap.put("Date-6", setD6);
            profileMap.put("Date-7", setD7);
            profileMap.put("Date-8", setD8);
            profileMap.put("Date-9", setD9);
            profileMap.put("Date-10", setD10);
        RootRef.child("appointments").child(CurrentUserID).child("Dates").setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SendUserToAppointmentDatesActivity();
                            Toast.makeText(AppointmentDatesDoctorActivity.this, "Dates Updated Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SendUserToAppointmentDatesActivity() {
        Intent appointmentDatesIntent = new Intent(AppointmentDatesDoctorActivity.this, AppointmentDatesActivity.class);
        appointmentDatesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(appointmentDatesIntent);
        finish();
    }
}
