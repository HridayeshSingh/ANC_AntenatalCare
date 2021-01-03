package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UltrasoundActivity extends AppCompatActivity {
    private TextView newObstetricButton, submitObstetricButton, clearAllObstetricButton;
    private EditText dateEditText, gaEditText, crlEditText, bpdEditText, hcEditText, acEditText, flEditText, efwEditText;

    private ArrayList<String> datesList = new ArrayList<>();
    private ArrayList<DataSnapshot> datesSnapshots = new ArrayList<>();
    private String mode, selectedPatient;
    private String currentUserId;
    private int entriesCount = 0;
    private boolean isPreviousButtonSelectedBefore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultrasound);

        setTitle("Obstetric Ultrasound");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newObstetricButton = findViewById(R.id.newObstetricButton);
        submitObstetricButton = findViewById(R.id.submitObstetricButton);
        clearAllObstetricButton = findViewById(R.id.clearAllObstetricButton);

        dateEditText = findViewById(R.id.dateEditText);
        gaEditText = findViewById(R.id.gaEditText);
        crlEditText = findViewById(R.id.crlEditText);
        bpdEditText = findViewById(R.id.bpdEditText);
        hcEditText = findViewById(R.id.hcEditText);
        acEditText = findViewById(R.id.acEditText);
        flEditText = findViewById(R.id.flEditText);
        efwEditText = findViewById(R.id.efwEditText);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        if(mode.equals("patient"))
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        else
            currentUserId = selectedPatient;

        reset();
        retrieve();
    }

    public void newObstetric(View view){
        newObstetricButton.setEnabled(false);
        enableAll();
        clearAll();
    }

    public void previousObstetric(View view){
        //Reflect db child removal action for "routine investigations" in the lists
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                datesList.remove(snapshot.getKey());
                datesSnapshots.remove(snapshot);
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        if(datesList.isEmpty()) {
            new AlertDialog.Builder(UltrasoundActivity.this)
                    .setMessage("You have no previous record! \n" +
                            "आपका कोई पिछला रिकॉर्ड नहीं है!")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            //display investigation dates in AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(UltrasoundActivity.this);
            builder.setTitle("Choose A Date");

            if (!isPreviousButtonSelectedBefore)
                reverseArrayLists();
            isPreviousButtonSelectedBefore = true;
            String[] dates = new String[datesList.size()];
            dates = datesList.toArray(dates);
            builder.setItems(dates, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    reset();
                    String info = "";

                    info = datesList.get(i);
                    dateEditText.setText(info);

                    info = datesSnapshots.get(i).child("GA").getValue().toString();
                    gaEditText.setText(info);

                    info = datesSnapshots.get(i).child("CRL").getValue().toString();
                    crlEditText.setText(info);

                    info = datesSnapshots.get(i).child("BPD").getValue().toString();
                    bpdEditText.setText(info);

                    info = datesSnapshots.get(i).child("HC").getValue().toString();
                    hcEditText.setText(info);

                    info = datesSnapshots.get(i).child("AC").getValue().toString();
                    acEditText.setText(info);

                    info = datesSnapshots.get(i).child("FL").getValue().toString();
                    flEditText.setText(info);

                    info = datesSnapshots.get(i).child("EFW").getValue().toString();
                    efwEditText.setText(info);
                }
            });
            builder.create().show();
        }
    }

    public void submitObstetric(View view){
        String date = "", info = "";
        date = dateEditText.getText().toString().replace('/', '-').replace('.', '-');
        entriesCount++;
        if(entriesCount < 10)
            date = "0"+entriesCount+">"+date;
        else
            date = entriesCount+">"+date;

        info = gaEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("GA").setValue(info);

        info = crlEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("CRL").setValue(info);

        info = bpdEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("BPD").setValue(info);

        info = hcEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("HC").setValue(info);

        info = acEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("AC").setValue(info);

        info = flEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("FL").setValue(info);

        info = efwEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").child(date).child("EFW").setValue(info);

        Toast.makeText(getApplicationContext(), "All details have been submitted !! \nसभी जानकारियां जमा हो चुकी हैं।", Toast.LENGTH_LONG).show();
        finish();
    }

    public void clearAllObstetric(View view){
        clearAll();
        disableAll();
        newObstetricButton.setEnabled(true);
    }

    private void reset() {
        clearAll();
        disableAll();
        newObstetricButton.setEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void disableAll() {
        dateEditText.setEnabled(false);
        gaEditText.setEnabled(false);
        crlEditText.setEnabled(false);
        bpdEditText.setEnabled(false);
        hcEditText.setEnabled(false);
        acEditText.setEnabled(false);
        flEditText.setEnabled(false);
        efwEditText.setEnabled(false);

        submitObstetricButton.setEnabled(false);
        clearAllObstetricButton.setEnabled(false);
    }

    private void enableAll() {
        dateEditText.setEnabled(true);
        gaEditText.setEnabled(true);
        crlEditText.setEnabled(true);
        bpdEditText.setEnabled(true);
        hcEditText.setEnabled(true);
        acEditText.setEnabled(true);
        flEditText.setEnabled(true);
        efwEditText.setEnabled(true);

        submitObstetricButton.setEnabled(true);
        clearAllObstetricButton.setEnabled(true);
    }

    private void clearAll() {
        dateEditText.setText("");
        gaEditText.setText("");
        crlEditText.setText("");
        bpdEditText.setText("");
        hcEditText.setText("");
        acEditText.setText("");
        flEditText.setText("");
        efwEditText.setText("");
    }

    private void retrieve() {
        datesList.clear();
        datesSnapshots.clear();

        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("obstetric ultrasound").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                datesList.add(snapshot.getKey().substring(3).replace('-', '/'));
                datesSnapshots.add(snapshot);
                entriesCount++;
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

    private void reverseArrayLists() {
        int left = 0, right = datesList.size()-1;
        while(left < right) {
            String temp = datesList.get(left);
            datesList.remove(left);
            datesList.add(left, datesList.get(right-1));
            datesList.remove(right);
            datesList.add(right, temp);
            left++;
            right--;
        }

        left = 0;
        right = datesSnapshots.size()-1;
        while(left < right) {
            DataSnapshot temp = datesSnapshots.get(left);
            datesSnapshots.remove(left);
            datesSnapshots.add(left, datesSnapshots.get(right-1));
            datesSnapshots.remove(right);
            datesSnapshots.add(right, temp);
            left++;
            right--;
        }
    }
}
