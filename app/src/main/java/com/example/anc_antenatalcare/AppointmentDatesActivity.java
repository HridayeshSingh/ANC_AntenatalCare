package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class AppointmentDatesActivity extends AppCompatActivity {

    private EditText editText_D1, editText_D2, editText_D3, editText_D4, editText_D5, editText_D6, editText_D7, editText_D8, editText_D9, editText_D10;
    private Button updateButton;

    private String mode;
    private String selectedPatient;
    private String CurrentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_dates);

        setTitle("Appointment Dates");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        InitializeFields();

        firebaseAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        if(mode.equals("patient")) {
            CurrentUserID = firebaseAuth.getCurrentUser().getUid();
            updateButton.setVisibility(View.INVISIBLE);
            enableFields(false);
        } else {
            CurrentUserID = selectedPatient;
            updateButton.setVisibility(View.VISIBLE);
            enableFields(true);
        }

        RetrieveUserInfo();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDates();
            }
        });

    }

    private void enableFields(boolean choice) {
        editText_D1.setEnabled(choice);
        editText_D2.setEnabled(choice);
        editText_D3.setEnabled(choice);
        editText_D4.setEnabled(choice);
        editText_D5.setEnabled(choice);
        editText_D6.setEnabled(choice);
        editText_D7.setEnabled(choice);
        editText_D8.setEnabled(choice);
        editText_D9.setEnabled(choice);
        editText_D10.setEnabled(choice);

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
        RootRef.child("appointments").child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.exists())) {
                            String retrieveD1 = snapshot.child("Date-01").getValue().toString();
                            String retrieveD2 = snapshot.child("Date-02").getValue().toString();
                            String retrieveD3 = snapshot.child("Date-03").getValue().toString();
                            String retrieveD4 = snapshot.child("Date-04").getValue().toString();
                            String retrieveD5 = snapshot.child("Date-05").getValue().toString();
                            String retrieveD6 = snapshot.child("Date-06").getValue().toString();
                            String retrieveD7 = snapshot.child("Date-07").getValue().toString();
                            String retrieveD8 = snapshot.child("Date-08").getValue().toString();
                            String retrieveD9 = snapshot.child("Date-09").getValue().toString();
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
            profileMap.put("Date-01", setD1);
            profileMap.put("Date-02", setD2);
            profileMap.put("Date-03", setD3);
            profileMap.put("Date-04", setD4);
            profileMap.put("Date-05", setD5);
            profileMap.put("Date-06", setD6);
            profileMap.put("Date-07", setD7);
            profileMap.put("Date-08", setD8);
            profileMap.put("Date-09", setD9);
            profileMap.put("Date-10", setD10);
        RootRef.child("appointments").child(CurrentUserID).setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //sendUserToMainPageActivity();
                            finish();
                            Toast.makeText(AppointmentDatesActivity.this, "Dates Updated Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendUserToMainPageActivity() {
        Intent appointmentDatesIntent = new Intent(AppointmentDatesActivity.this, MainPage.class);
        appointmentDatesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(appointmentDatesIntent);
        finish();
    }
}
