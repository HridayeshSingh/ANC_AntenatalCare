package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
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

public class TreatmentActivity extends AppCompatActivity {

    private EditText editText_treatment;
    private Button button_treatment, button_delete;

    private String mode;
    private String selectedPatient;
    private String currentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        setTitle("Treatment Details Of patient");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");
        if(mode.equals("patient"))
            currentUserID = firebaseAuth.getCurrentUser().getUid();
        else
            currentUserID = selectedPatient;
        rootRef = FirebaseDatabase.getInstance().getReference();

        editText_treatment = findViewById(R.id.editText_complaint);
        button_treatment = findViewById(R.id.button_complaint);
        button_delete = findViewById(R.id.button_delete);

        retrieveUserInfo();

        if(mode.equals("doctor")) {
            editText_treatment.setEnabled(false);
            button_treatment.setVisibility(View.INVISIBLE);
            button_delete.setVisibility(View.INVISIBLE);
        } else {
            editText_treatment.setEnabled(true);
            button_treatment.setVisibility(View.VISIBLE);
            button_delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    public void fileTreatment(View view) {
        String set_treatment = editText_treatment.getText().toString();

        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("Details", set_treatment);
        rootRef.child("treatments").child(currentUserID).setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TreatmentActivity.this, "Treatment details filed successfully...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void retrieveUserInfo() {
        rootRef.child("treatments").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.exists())) {
                            String retrieveComplaint = snapshot.child("Details").getValue().toString();
                            editText_treatment.setText(retrieveComplaint);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void deleteTreatment(View view) {

        new AlertDialog.Builder(TreatmentActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure!?")
                .setMessage("Do you want to delete this Detail?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        rootRef.child("treatments").child(currentUserID)
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        editText_treatment.setText("");
                                        Toast.makeText(TreatmentActivity.this, "Treatment details deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }
}
