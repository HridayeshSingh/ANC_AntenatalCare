package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

public class RoutineInvestigationsActivity extends AppCompatActivity {
    TextView newInvestigationTextView, previousInvestigationTextView, submitTextView, clearAllTextView;
    EditText dateEditText, bloodGroupEditText, hemoglobinEditText, thalassemiaEditText, vdrlEditText, ogttEditText, hivEditText, hbsAgEditText, urineRMEditText, serumEditText, otherEditText;

    ArrayList<String> datesList = new ArrayList<>();
    ArrayList<DataSnapshot> datesSnapshots = new ArrayList<>();
    String currentUser;
    int entriesCount = 0;
    boolean isPreviousButtonSelectedBefore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_investigations);

        setTitle("Routine Investigations");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newInvestigationTextView = findViewById(R.id.newInvestigationTextView);
        previousInvestigationTextView = findViewById(R.id.previousInvestigationTextView);
        submitTextView = findViewById(R.id.submitTextView);
        clearAllTextView = findViewById(R.id.clearAllTextView);

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

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reset();
        retrieve();

        newInvestigationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newInvestigationTextView.setEnabled(false);
                enableAll();
                clearAll();
            }
        });

        previousInvestigationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reflect db child removal action for "routine investigations" in the lists
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").addChildEventListener(new ChildEventListener() {
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
        });

        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = "", info = "";
                date = dateEditText.getText().toString().replace('/', '-').replace('.', '-');
                entriesCount++;
                if(entriesCount < 10)
                    date = "0"+entriesCount+">"+date;
                else
                    date = entriesCount+">"+date;

                info = bloodGroupEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("Blood Group").setValue(info);

                info = hemoglobinEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("Hemoglobin").setValue(info);

                info = thalassemiaEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("Thalassemia Screen").setValue(info);

                info = vdrlEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("VDRL").setValue(info);

                info = ogttEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("OGTT").setValue(info);

                info = hivEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("HIV 1&2").setValue(info);

                info = hbsAgEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("HbsAg").setValue(info);

                info = urineRMEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("Urine R-M").setValue(info);

                info = serumEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("Serum TSH").setValue(info);

                info = otherEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").child(date).child("Any Other").setValue(info);

                Toast.makeText(getApplicationContext(), "All details have been submitted !! \nसभी जानकारियां जमा हो चुकी हैं।", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        clearAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    public void disableAll() {
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

        submitTextView.setEnabled(false);
        clearAllTextView.setEnabled(false);
    }

    public void enableAll() {
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

        submitTextView.setEnabled(true);
        clearAllTextView.setEnabled(true);
    }

    public void clearAll() {
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

    public void reset() {
        clearAll();
        disableAll();
        newInvestigationTextView.setEnabled(true);
    }

    public void retrieve() {
        datesList.clear();
        datesSnapshots.clear();

        FirebaseDatabase.getInstance().getReference().child("investigations").child(currentUser).child("routine investigations").addChildEventListener(new ChildEventListener() {
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

    public void reverseArrayLists() {
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
