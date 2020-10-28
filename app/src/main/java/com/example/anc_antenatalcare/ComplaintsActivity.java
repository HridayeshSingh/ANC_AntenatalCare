package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import java.util.HashSet;

public class ComplaintsActivity extends AppCompatActivity {

    private EditText editText_complaint;
    private Button button_complaint, button_delete;

    private String CurrentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        setTitle("Complaints of Patient");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserID = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        editText_complaint = findViewById(R.id.editText_complaint);
        button_complaint = findViewById(R.id.button_complaint);
        button_delete = findViewById(R.id.button_delete);

        RetrieveUserInfo();

        button_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileComplaint();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ComplaintsActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure!?")
                        .setMessage("Do you want to delete this Complaint?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                DeleteComplaint();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private void FileComplaint() {
        String set_complaint = editText_complaint.getText().toString();

        HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("Complaint", set_complaint);
        RootRef.child("complaints").child(CurrentUserID).setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SendUserToMainPageActivity();
                            Toast.makeText(ComplaintsActivity.this, "Complaint Filed Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SendUserToMainPageActivity() {
        Intent mainPageIntent = new Intent(ComplaintsActivity.this, MainPage.class);
        mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainPageIntent);
        finish();
    }

    private void RetrieveUserInfo() {
        RootRef.child("complaints").child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.exists())) {
                            String retrieveComplaint = snapshot.child("Complaint").getValue().toString();
                            editText_complaint.setText(retrieveComplaint);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void DeleteComplaint() {
        RootRef.child("complaints").child(CurrentUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SendUserToMainPageActivity();
                        Toast.makeText(ComplaintsActivity.this, "Complaint Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
