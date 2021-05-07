package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class SignupDoctorActivity extends AppCompatActivity {
    private EditText doctorNameEditText, emailEditText, smcEditText, hospitalNameEditText, areaEditText, cityEditText, stateEditText, postalCodeEditText;

    private ArrayList<String> hospitals = new ArrayList<>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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

        retrieveHospital();
    }

    public void signUpDoctor(View view){
        String docName = doctorNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = smcEditText.getText().toString();
        final String hospitalKey = hospitalNameEditText.getText().toString().toUpperCase().concat(" " + postalCodeEditText.getText().toString());
        String area = areaEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = stateEditText.getText().toString();
        String postalCode = postalCodeEditText.getText().toString();

        if (TextUtils.isEmpty(docName)) {
            doctorNameEditText.requestFocus();
            doctorNameEditText.setError("Please Enter Doctor's Name");
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.requestFocus();
            emailEditText.setError("Please Enter Email Address");
        }
        if (TextUtils.isEmpty(password)) {
            smcEditText.requestFocus();
            smcEditText.setError("Please Enter SMC no. as your Password");
        }
        if (TextUtils.isEmpty(hospitalNameEditText.getText().toString())) {
            hospitalNameEditText.requestFocus();
            hospitalNameEditText.setError("Please Enter hospital Name");
        }
        if (TextUtils.isEmpty(area)) {
            areaEditText.requestFocus();
            areaEditText.setError("Please Enter hospital area");
        }
        if (TextUtils.isEmpty(city)) {
            cityEditText.requestFocus();
            cityEditText.setError("Please Enter hospital city");
        }
        if (TextUtils.isEmpty(state)) {
            stateEditText.requestFocus();
            stateEditText.setError("Please Enter hospital state");
        }
        if (TextUtils.isEmpty(postalCode)) {
            postalCodeEditText.requestFocus();
            postalCodeEditText.setError("Please Enter hospital postal code");
        }
        if (TextUtils.isEmpty(docName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(hospitalNameEditText.getText().toString()) ||
                TextUtils.isEmpty(area) || TextUtils.isEmpty(city) || TextUtils.isEmpty(state) || TextUtils.isEmpty(postalCode)) {
            new AlertDialog.Builder(SignupDoctorActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Fields not filled!")
                    .setMessage("Please fill all the mandatory details to signUp...")
                    .setNeutralButton("Ok", null)
                    .show();
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignupDoctorActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Send Verification Link
                            /*FirebaseUser firebaseUser = auth.getCurrentUser();
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
                            }); */

                                boolean isHospitalNew = true;

                                for (int i = 0; i < hospitals.size(); i++) {
                                    String name = hospitals.get(i);
                                    if (name.equals(hospitalKey)) {
                                        isHospitalNew = false;
                                        submitDoctorDetails(hospitalKey);
                                        break;
                                    }
                                }
                                if (isHospitalNew) {
                                    submitHospitalDetails(hospitalKey);
                                    submitDoctorDetails(hospitalKey);
                                }
                                Toast.makeText(getApplicationContext(), "Doctor Registered Successfully", Toast.LENGTH_SHORT).show();
                                sendUserToSelectPatientActivity();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void retrieveHospital() {
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

    private void submitHospitalDetails(String hospitalKey) {
        databaseReference.child("hospitals").child(hospitalKey).child("hospitalName").setValue(hospitalNameEditText.getText().toString().toUpperCase());
        databaseReference.child("hospitals").child(hospitalKey).child("area").setValue(areaEditText.getText().toString());
        databaseReference.child("hospitals").child(hospitalKey).child("city").setValue(cityEditText.getText().toString());
        databaseReference.child("hospitals").child(hospitalKey).child("state").setValue(stateEditText.getText().toString());
        databaseReference.child("hospitals").child(hospitalKey).child("postalCode").setValue(postalCodeEditText.getText().toString());
    }

    private void submitDoctorDetails(String hospitalKey) {
        databaseReference.child("doctors").child(auth.getCurrentUser().getUid()).child("doctorName").setValue(doctorNameEditText.getText().toString());
        databaseReference.child("doctors").child(auth.getCurrentUser().getUid()).child("email").setValue(emailEditText.getText().toString());
        databaseReference.child("doctors").child(auth.getCurrentUser().getUid()).child("hospital").setValue(hospitalKey);

    }

    private void sendUserToSelectPatientActivity() {
        Intent i = new Intent(getApplicationContext(), SelectPatientActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
