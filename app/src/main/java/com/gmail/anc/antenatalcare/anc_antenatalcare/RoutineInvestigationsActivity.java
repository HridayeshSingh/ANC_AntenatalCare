package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RoutineInvestigationsActivity extends AppCompatActivity {
    private Button newInvestigationButton, submitButton, clearAllButton;
    private EditText dateEditText, bloodGroupEditText, hemoglobinEditText, thalassemiaEditText, vdrlEditText, ogttEditText, hivEditText, hbsAgEditText, urineRMEditText, serumEditText, otherEditText;

    private ArrayList<String> datesList = new ArrayList<>();
    private ArrayList<DataSnapshot> datesSnapshots = new ArrayList<>();
    private String mode;
    private String selectedPatient;
    private String currentUserId;
    private int entriesCount = 0;
    private boolean isPreviousButtonSelectedBefore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_investigations);

        setTitle("Routine Investigations");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newInvestigationButton = findViewById(R.id.newInvestigationButton);
        submitButton = findViewById(R.id.submitButton);
        clearAllButton = findViewById(R.id.clearAllButton);

        dateEditText = findViewById(R.id.dateEditText);
        bloodGroupEditText = findViewById(R.id.bloodGroupEditText);
        hemoglobinEditText = findViewById(R.id.hemoglobinEditText);
        thalassemiaEditText = findViewById(R.id.thalassemiaEditText);
        vdrlEditText = findViewById(R.id.vdrlEditText);
        ogttEditText = findViewById(R.id.ogttEditText);
        hivEditText = findViewById(R.id.hivEditText);
        hbsAgEditText = findViewById(R.id.hbsAgEditText);
        urineRMEditText = findViewById(R.id.urineRMEditText);
        serumEditText = findViewById(R.id.serumEditText);
        otherEditText = findViewById(R.id.otherEditText);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        if(mode.equals("patient"))
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        else
            currentUserId = selectedPatient;

        reset();
        retrieve();
    }

    public void newInvestigation(View view){
        newInvestigationButton.setEnabled(false);
        enableAll();
        clearAll();
    }

    public void previousInvestigation(View view){
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

        //display investigation dates in AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RoutineInvestigationsActivity.this);
        builder.setTitle("Choose A Date");

        if(!isPreviousButtonSelectedBefore)
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

                info = datesSnapshots.get(i).child("Blood Group").getValue().toString();
                bloodGroupEditText.setText(info);

                info = datesSnapshots.get(i).child("Hemoglobin").getValue().toString();
                hemoglobinEditText.setText(info);

                info = datesSnapshots.get(i).child("Thalassemia Screen").getValue().toString();
                thalassemiaEditText.setText(info);

                info = datesSnapshots.get(i).child("VDRL").getValue().toString();
                vdrlEditText.setText(info);

                info = datesSnapshots.get(i).child("OGTT").getValue().toString();
                ogttEditText.setText(info);

                info = datesSnapshots.get(i).child("HIV 1&2").getValue().toString();
                hivEditText.setText(info);

                info = datesSnapshots.get(i).child("HbsAg").getValue().toString();
                hbsAgEditText.setText(info);

                info = datesSnapshots.get(i).child("Urine R-M").getValue().toString();
                urineRMEditText.setText(info);

                info = datesSnapshots.get(i).child("Serum TSH").getValue().toString();
                serumEditText.setText(info);

                info = datesSnapshots.get(i).child("Any Other").getValue().toString();
                otherEditText.setText(info);
            }
        });
        builder.create().show();
    }

    public void submitInvestigationDetails(View view){
        String date = "", info = "";
        date = dateEditText.getText().toString().replace('/', '-').replace('.', '-');
        entriesCount++;
        if(entriesCount < 10)
            date = "0"+entriesCount+">"+date;
        else
            date = entriesCount+">"+date;

        info = bloodGroupEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("Blood Group").setValue(info);

        info = hemoglobinEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("Hemoglobin").setValue(info);

        info = thalassemiaEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("Thalassemia Screen").setValue(info);

        info = vdrlEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("VDRL").setValue(info);

        info = ogttEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("OGTT").setValue(info);

        info = hivEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("HIV 1&2").setValue(info);

        info = hbsAgEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("HbsAg").setValue(info);

        info = urineRMEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("Urine R-M").setValue(info);

        info = serumEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("Serum TSH").setValue(info);

        info = otherEditText.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").child(date).child("Any Other").setValue(info);

        Toast.makeText(getApplicationContext(), "All details have been submitted !! \nसभी जानकारियां जमा हो चुकी हैं।", Toast.LENGTH_LONG).show();
        finish();
    }

    public void clearAllInvestigationDetails(View view){
        clearAll();
        disableAll();
        newInvestigationButton.setEnabled(true);
    }

    private void reset() {
        clearAll();
        disableAll();
        newInvestigationButton.setEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void disableAll() {
        dateEditText.setEnabled(false);
        bloodGroupEditText.setEnabled(false);
        hemoglobinEditText.setEnabled(false);
        thalassemiaEditText.setEnabled(false);
        vdrlEditText.setEnabled(false);
        ogttEditText.setEnabled(false);
        hivEditText.setEnabled(false);
        hbsAgEditText.setEnabled(false);
        urineRMEditText.setEnabled(false);
        serumEditText.setEnabled(false);
        otherEditText.setEnabled(false);

        submitButton.setEnabled(false);
        clearAllButton.setEnabled(false);
    }

    private void enableAll() {
        dateEditText.setEnabled(true);
        bloodGroupEditText.setEnabled(true);
        hemoglobinEditText.setEnabled(true);
        thalassemiaEditText.setEnabled(true);
        vdrlEditText.setEnabled(true);
        ogttEditText.setEnabled(true);
        hivEditText.setEnabled(true);
        hbsAgEditText.setEnabled(true);
        urineRMEditText.setEnabled(true);
        serumEditText.setEnabled(true);
        otherEditText.setEnabled(true);

        submitButton.setEnabled(true);
        clearAllButton.setEnabled(true);
    }

    private void clearAll() {
        dateEditText.setText("");
        bloodGroupEditText.setText("");
        hemoglobinEditText.setText("");
        thalassemiaEditText.setText("");
        vdrlEditText.setText("");
        ogttEditText.setText("");
        hivEditText.setText("");
        hbsAgEditText.setText("");
        urineRMEditText.setText("");
        serumEditText.setText("");
        otherEditText.setText("");
    }

    private void retrieve() {
        datesList.clear();
        datesSnapshots.clear();

        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUserId).child("routine investigations").addChildEventListener(new ChildEventListener() {
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
