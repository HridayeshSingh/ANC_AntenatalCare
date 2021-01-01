package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientDetails extends AppCompatActivity {

    private TextView patName, patAge, husName, address, phn, opd, hosName;

    private String mode;
    private String selectedPatient;
    private String currentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        setTitle("Patient's Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");
        firebaseAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        if(mode.equals("patient"))
            currentUserID = firebaseAuth.getCurrentUser().getUid();
        else
            currentUserID = selectedPatient;

        initializeFields();

        retrieveUserInfo();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void initializeFields() {
        patName = findViewById(R.id.textView_patName);
        patAge = findViewById(R.id.textView_patAge);
        husName = findViewById(R.id.textView_husName);
        address = findViewById(R.id.textView_address);
        phn = findViewById(R.id.textView_phn);
        opd = findViewById(R.id.textView_opd);
        hosName = findViewById(R.id.textView_hosName);
    }

    private void retrieveUserInfo() {
        rootRef.child("users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.exists())) {
                            String retrievePatName = snapshot.child("patName").getValue().toString();
                            String retrievePatAge = snapshot.child("age").getValue().toString();
                            String retrieveAddress = snapshot.child("address").getValue().toString();
                            String retrieveHusName = snapshot.child("husName").getValue().toString();
                            String retrieveHosName = snapshot.child("hosName").getValue().toString();
                            String retrieveOpd = snapshot.child("opd").getValue().toString();
                            String retrievePhn = snapshot.child("phn").getValue().toString();

                            patName.setText(retrievePatName);
                            patAge.setText(retrievePatAge);
                            husName.setText(retrieveHusName);
                            address.setText(retrieveAddress);
                            phn.setText(retrievePhn);
                            opd.setText(retrieveOpd);
                            hosName.setText(retrieveHosName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}
