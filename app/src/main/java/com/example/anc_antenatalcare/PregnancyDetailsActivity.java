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

public class PregnancyDetailsActivity extends AppCompatActivity {

    TextView newDetailsTextView, previousDetailsTextView, submitTextView, clearAllTextView;
    EditText dateEditText, gravidaEditText, parityEditText, liveIssuesEditText, abortionEditText, lmpEditText, pogEditText, eddEditText, diagnosisEditText;

    ArrayList<String> datesList = new ArrayList<>();
    ArrayList<DataSnapshot> datesSnapshots = new ArrayList<>();
    String currentUser;
    int entriesCount = 0;
    boolean isPreviousButtonSelectedBefore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_details);

        setTitle("Pregnancy Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newDetailsTextView = findViewById(R.id.newDetailsTextView);
        previousDetailsTextView = findViewById(R.id.previousDetailsTextView);
        submitTextView = findViewById(R.id.submitTextView);
        clearAllTextView = findViewById(R.id.clearAllTextView);

        dateEditText = findViewById(R.id.dateEditText);
        gravidaEditText = findViewById(R.id.gravidaEditText);
        parityEditText = findViewById(R.id.parityEditText);
        liveIssuesEditText = findViewById(R.id.liveIssuesEditText);
        abortionEditText = findViewById(R.id.abortionEditText);
        lmpEditText = findViewById(R.id.lmpEditText);
        pogEditText = findViewById(R.id.pogEditText);
        eddEditText = findViewById(R.id.eddEditText);
        diagnosisEditText = findViewById(R.id.diagnosisEditText);

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reset();
        retrieve();

        newDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDetailsTextView.setEnabled(false);
                enableAll();
                clearAll();
            }
        });

        previousDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reflect db child removal action for "pregnancy details" in the lists
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).addChildEventListener(new ChildEventListener() {
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

                //display dates in AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyDetailsActivity.this);
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

                        info = datesSnapshots.get(i).child("Gravida").getValue().toString();
                        gravidaEditText.setText(info);

                        info = datesSnapshots.get(i).child("Parity").getValue().toString();
                        parityEditText.setText(info);

                        info = datesSnapshots.get(i).child("Live Issues").getValue().toString();
                        liveIssuesEditText.setText(info);

                        info = datesSnapshots.get(i).child("Abortion").getValue().toString();
                        abortionEditText.setText(info);

                        info = datesSnapshots.get(i).child("LMP").getValue().toString();
                        lmpEditText.setText(info);

                        info = datesSnapshots.get(i).child("POG").getValue().toString();
                        pogEditText.setText(info);

                        info = datesSnapshots.get(i).child("EDD").getValue().toString();
                        eddEditText.setText(info);

                        info = datesSnapshots.get(i).child("Diagnosis").getValue().toString();
                        diagnosisEditText.setText(info);
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

                info = gravidaEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("Gravida").setValue(info);

                info = parityEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("Parity").setValue(info);

                info = liveIssuesEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("Live Issues").setValue(info);

                info = abortionEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("Abortion").setValue(info);

                info = lmpEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("LMP").setValue(info);

                info = pogEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("POG").setValue(info);

                info = eddEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("EDD").setValue(info);

                info = diagnosisEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).child(date).child("Diagnosis").setValue(info);

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
        gravidaEditText.setEnabled(false);
        parityEditText.setEnabled(false);
        liveIssuesEditText.setEnabled(false);
        abortionEditText.setEnabled(false);
        lmpEditText.setEnabled(false);
        pogEditText.setEnabled(false);
        eddEditText.setEnabled(false);
        diagnosisEditText.setEnabled(false);

        submitTextView.setEnabled(false);
        clearAllTextView.setEnabled(false);
    }

    public void enableAll() {
        dateEditText.setEnabled(true);
        gravidaEditText.setEnabled(true);
        parityEditText.setEnabled(true);
        liveIssuesEditText.setEnabled(true);
        abortionEditText.setEnabled(true);
        lmpEditText.setEnabled(true);
        pogEditText.setEnabled(true);
        eddEditText.setEnabled(true);
        diagnosisEditText.setEnabled(true);

        submitTextView.setEnabled(true);
        clearAllTextView.setEnabled(true);
    }

    public void clearAll() {
        dateEditText.setText("");
        gravidaEditText.setText("");
        parityEditText.setText("");
        liveIssuesEditText.setText("");
        abortionEditText.setText("");
        lmpEditText.setText("");
        pogEditText.setText("");
        eddEditText.setText("");
        diagnosisEditText.setText("");
    }

    public void reset() {
        clearAll();
        disableAll();
        newDetailsTextView.setEnabled(true);
    }

    public void retrieve() {
        datesList.clear();
        datesSnapshots.clear();

        FirebaseDatabase.getInstance().getReference().child("pregnancy details").child(currentUser).addChildEventListener(new ChildEventListener() {
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
