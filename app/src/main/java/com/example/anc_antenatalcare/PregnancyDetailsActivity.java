package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PregnancyDetailsActivity extends AppCompatActivity {

    EditText gravidaEditText;
    EditText parityEditText;
    EditText liveIssuesEditText;
    EditText abortionEditText;
    EditText lmpEditText;
    EditText pogEditText;
    EditText eddEditText;
    EditText diagnosisEditText;

    TextView gravidaTextView;
    TextView parityTextView;
    TextView liveIssuesTextView;
    TextView abortionTextView;
    TextView lmpTextView;
    TextView pogTextView;
    TextView eddTextView;
    TextView diagnosisTextView;

    Button submitButton;
    ScrollView scrollView;

    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_details);

        setTitle("Pregnancy Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gravidaEditText = findViewById(R.id.gravidaEditText);
        parityEditText = findViewById(R.id.parityEditText);
        liveIssuesEditText = findViewById(R.id.liveIssuesEditText);
        abortionEditText = findViewById(R.id.abortionEditText);
        lmpEditText = findViewById(R.id.lmpEditText);
        pogEditText = findViewById(R.id.pogEditText);
        eddEditText = findViewById(R.id.eddEditText);
        diagnosisEditText = findViewById(R.id.diagnosisEditText);

        gravidaTextView = findViewById(R.id.gravidaTextView);
        parityTextView = findViewById(R.id.parityTextView);
        liveIssuesTextView = findViewById(R.id.liveIssuesTextView);
        abortionTextView = findViewById(R.id.abortionTextView);
        lmpTextView = findViewById(R.id.lmpTextView);
        pogTextView = findViewById(R.id.pogTextView);
        eddTextView = findViewById(R.id.eddTextView);
        diagnosisTextView = findViewById(R.id.diagnosisTextView);

        submitButton = findViewById(R.id.submitButton);

        scrollView = findViewById(R.id.scrollView);

        disableAll();

        final String date = getIntent().getStringExtra("date");
        if(date == null) {
            Toast.makeText(getApplicationContext(), "Add pregnancy details", Toast.LENGTH_SHORT).show();
        } else {
            enableAllTextViews();
            FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.getKey() != null && snapshot.getKey().equals(date)) {
                        gravidaEditText.setText(snapshot.child("gravida").getValue().toString());
                        parityEditText.setText(snapshot.child("parity").getValue().toString());
                        liveIssuesEditText.setText(snapshot.child("liveIssues").getValue().toString());
                        abortionEditText.setText(snapshot.child("abortion").getValue().toString());
                        lmpEditText.setText(snapshot.child("LMP").getValue().toString());
                        pogEditText.setText(snapshot.child("POG").getValue().toString());
                        eddEditText.setText(snapshot.child("EDD").getValue().toString());
                        diagnosisEditText.setText(snapshot.child("diagnosis").getValue().toString());
                    }
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
    }

    public void disableAll() {
        gravidaEditText.setEnabled(false);
        parityEditText.setEnabled(false);
        liveIssuesEditText.setEnabled(false);
        abortionEditText.setEnabled(false);
        lmpEditText.setEnabled(false);
        pogEditText.setEnabled(false);
        eddEditText.setEnabled(false);
        diagnosisEditText.setEnabled(false);

        gravidaTextView.setEnabled(false);
        parityTextView.setEnabled(false);
        liveIssuesTextView.setEnabled(false);
        abortionTextView.setEnabled(false);
        lmpTextView.setEnabled(false);
        pogTextView.setEnabled(false);
        eddTextView.setEnabled(false);
        diagnosisTextView.setEnabled(false);

        submitButton.setEnabled(false);
    }

    public void enableAll() {
        gravidaEditText.setEnabled(true);
        parityEditText.setEnabled(true);
        liveIssuesEditText.setEnabled(true);
        abortionEditText.setEnabled(true);
        lmpEditText.setEnabled(true);
        pogEditText.setEnabled(true);
        eddEditText.setEnabled(true);
        diagnosisEditText.setEnabled(true);

        gravidaTextView.setEnabled(true);
        parityTextView.setEnabled(true);
        liveIssuesTextView.setEnabled(true);
        abortionTextView.setEnabled(true);
        lmpTextView.setEnabled(true);
        pogTextView.setEnabled(true);
        eddTextView.setEnabled(true);
        diagnosisTextView.setEnabled(true);

        submitButton.setEnabled(true);
    }

    public void enableAllTextViews() {
        gravidaTextView.setEnabled(true);
        parityTextView.setEnabled(true);
        liveIssuesTextView.setEnabled(true);
        abortionTextView.setEnabled(true);
        lmpTextView.setEnabled(true);
        pogTextView.setEnabled(true);
        eddTextView.setEnabled(true);
        diagnosisTextView.setEnabled(true);
    }

    public void clearAllEditTexts() {
        gravidaEditText.setText("");
        parityEditText.setText("");
        liveIssuesEditText.setText("");
        abortionEditText.setText("");
        lmpEditText.setText("");
        pogEditText.setText("");
        eddEditText.setText("");
        diagnosisEditText.setText("");
    }

    public void submitDetails(View view) {
        String detail;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDate = formatter.format(new Date());

        //Upload gravida:
        if(gravidaEditText.getText() != null)
            detail = gravidaEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("gravida").setValue(detail);

        //Upload parity:
        if(parityEditText.getText() != null)
            detail = parityEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("parity").setValue(detail);

        //Upload live issues:
        if(liveIssuesEditText.getText() != null)
            detail = liveIssuesEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("liveIssues").setValue(detail);

        //Upload abortion:
        if(abortionEditText.getText() != null)
            detail = abortionEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("abortion").setValue(detail);

        //Upload LMP:
        if(lmpEditText.getText() != null)
            detail = lmpEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("LMP").setValue(detail);

        //upload POG:
        if(pogEditText.getText() != null)
            detail = pogEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("POG").setValue(detail);

        //upload EDD:
        if(eddEditText.getText() != null)
            detail = eddEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("EDD").setValue(detail);

        //upload Diagnosis:
        if(diagnosisEditText.getText() != null)
            detail = diagnosisEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child(currentDate).child("diagnosis").setValue(detail);

        Toast.makeText(this, "All details have been submitted !! \nसभी जानकारियां जमा हो चुकी हैं।", Toast.LENGTH_LONG).show();
        closeActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pregnancy_details_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.addDetails:
                enableAll();
                clearAllEditTexts();
                break;
            case R.id.prevDetails:
                startActivity(new Intent(getApplicationContext(), DatesPregnancyActivity.class));
                break;
            default:
                return false;
        }
        return true;
    }

    public void closeActivity() {
        Intent intent = new Intent(this, MainPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        closeActivity();
    }
}
