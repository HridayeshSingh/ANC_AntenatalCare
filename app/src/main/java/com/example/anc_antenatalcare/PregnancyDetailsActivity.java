package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PregnancyDetailsActivity extends AppCompatActivity {

    EditText gravidaEditText;
    EditText parityEditText;
    EditText liveIssuesEditText;
    EditText abortionEditText;
    EditText lmpEditText;
    EditText pogEditText;
    EditText eddEditText;
    EditText diagnosisEditText;

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
    }

    public void submitDetails(View view) {
        String detail, currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Upload gravida:
        if(gravidaEditText.getText() != null)
            detail = gravidaEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("gravida").setValue(detail);

        //Upload parity:
        if(parityEditText.getText() != null)
            detail = parityEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("parity").setValue(detail);

        //Upload live issues:
        if(liveIssuesEditText.getText() != null)
            detail = liveIssuesEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("liveIssues").setValue(detail);

        //Upload abortion:
        if(abortionEditText.getText() != null)
            detail = abortionEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("abortion").setValue(detail);

        //Upload LMP:
        if(lmpEditText.getText() != null)
            detail = lmpEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("LMP").setValue(detail);

        //upload POG:
        if(pogEditText.getText() != null)
            detail = pogEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("POG").setValue(detail);

        //upload EDD:
        if(eddEditText.getText() != null)
            detail = eddEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("EDD").setValue(detail);

        //upload Diagnosis:
        if(diagnosisEditText.getText() != null)
            detail = diagnosisEditText.getText().toString();
        else
            detail = "NIL";
        FirebaseDatabase.getInstance().getReference().child("pregnancyDetails").child(currentUser).child("diagnosis").setValue(detail);

        Toast.makeText(this, "All details have been submitted successfully!! \nसभी जानकारियां जमा हो चुकी हैं।", Toast.LENGTH_LONG).show();
        finish();
    }
}
