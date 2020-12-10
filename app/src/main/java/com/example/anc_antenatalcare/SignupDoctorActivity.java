package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupDoctorActivity extends AppCompatActivity {
    EditText doctorNameEditText, emailEditText, smcEditText, hospitalNameEditText, areaEditText, cityEditText, stateEditText, postalCodeEditText;
    TextView signUpTextView;

    ArrayList<String> hospitals = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);

        setTitle("Sign Up");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        doctorNameEditText = findViewById(R.id.doctorNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        smcEditText = findViewById(R.id.smcEditText);
        hospitalNameEditText = findViewById(R.id.hospitalNameEditText);
        areaEditText = findViewById(R.id.areaEditText);
        cityEditText = findViewById(R.id.cityEditText);
        stateEditText = findViewById(R.id.stateEditText);
        postalCodeEditText = findViewById(R.id.postalCodeEditText);

        signUpTextView = findViewById(R.id.signUpTextView);

        retrieveHospital();

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = smcEditText.getText().toString();
                final String hospitalKey = hospitalNameEditText.getText().toString().toUpperCase().concat(" " + postalCodeEditText.getText().toString());

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupDoctorActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // Send Verification Link
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignupDoctorActivity.this, "Verification link has been sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignupDoctorActivity.this, "Error in sending the Verification link.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    boolean isHospitalNew = true;

                                    for(int i = 0; i < hospitals.size(); i++) {
                                        String name = hospitals.get(i);
                                        if(name.equals(hospitalKey)) {
                                            isHospitalNew = false;
                                            submitDoctorDetails(hospitalKey);
                                            break;
                                        }
                                    }
                                    if(isHospitalNew) {
                                        submitHospitalDetails(hospitalKey);
                                        submitDoctorDetails(hospitalKey);
                                    }
                                    Toast.makeText(getApplicationContext(), "Doctor Registered Successfully", Toast.LENGTH_SHORT).show();
                                    sendUserToDoctorEmailVerifyActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void retrieveHospital() {
        databaseReference.child("hospitals").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                hospitals.add(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void submitHospitalDetails(String hospitalKey) {
        databaseReference.child("hospitals").child(hospitalKey).child("hospitalName").setValue(hospitalNameEditText.getText().toString().toUpperCase());
        databaseReference.child("hospitals").child(hospitalKey).child("area").setValue(areaEditText.getText().toString());
        databaseReference.child("hospitals").child(hospitalKey).child("city").setValue(cityEditText.getText().toString());
        databaseReference.child("hospitals").child(hospitalKey).child("state").setValue(stateEditText.getText().toString());
        databaseReference.child("hospitals").child(hospitalKey).child("postalCode").setValue(postalCodeEditText.getText().toString());
    }

    public void submitDoctorDetails(String hospitalKey) {
        databaseReference.child("doctors").child(auth.getCurrentUser().getUid()).child("doctorName").setValue(doctorNameEditText.getText().toString());
        databaseReference.child("doctors").child(auth.getCurrentUser().getUid()).child("email").setValue(emailEditText.getText().toString());
        databaseReference.child("doctors").child(auth.getCurrentUser().getUid()).child("hospital").setValue(hospitalKey);

    }

    public void sendUserToDoctorEmailVerifyActivity() {
        Intent i = new Intent(getApplicationContext(), DoctorEmailVerifyActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
