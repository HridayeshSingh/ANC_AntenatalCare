package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    private String[] choice = {"Strongly Disagree", "Disagree", "Neither agree Nor Disagree", "Agree", "Strongly Agree"};

    private String[] textFields = new String[12];
    private RadioGroup[] radioGroups = new RadioGroup[12];

    private HashMap<String, Object> profileMap = new HashMap<>();

    private String currentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;

    private Button submit, again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();

        setTitle("Feedback Of patient");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializeFields();

        initializeRadioGroups();

        again.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void initializeRadioGroups() {

        radioGroups[0] = findViewById(R.id.radioGroup1);
        radioGroups[1] = findViewById(R.id.radioGroup2);
        radioGroups[2] = findViewById(R.id.radioGroup3);
        radioGroups[3] = findViewById(R.id.radioGroup4);
        radioGroups[4] = findViewById(R.id.radioGroup5);
        radioGroups[5] = findViewById(R.id.radioGroup6);
        radioGroups[6] = findViewById(R.id.radioGroup7);
        radioGroups[7] = findViewById(R.id.radioGroup8);
        radioGroups[8] = findViewById(R.id.radioGroup9);
        radioGroups[9] = findViewById(R.id.radioGroup10);
        radioGroups[10] = findViewById(R.id.radioGroup11);
        radioGroups[11] = findViewById(R.id.radioGroup12);

    }

    private void initializeFields() {
        submit = findViewById(R.id.button_feedback_submit);
        again = findViewById(R.id.button_feedback_again);
        textFields[0] = "I am satisfied with the antenatal care I have received";
        textFields[1] = "I feel connected to my healthcare provider";
        textFields[2] = "I feel knowledgeable about my pregnancy";
        textFields[3] = "I am getting the care that I need";
        textFields[4] = "I feel my healthcare provider knows how I am doing in between my appointments";
        textFields[5] = "I feel knowledgeable about the safe exercises I need to do during my pregnancy";
        textFields[6] = "I feel knowledgeable about my health and dietary habits during my pregnancy";
        textFields[7] = "I feel that I know what to expect during my each trimesters";
        textFields[8] = "It is convenient for me to weigh myself at least once weekly";
        textFields[9] = "It is convenient for me to measure my blood pressure at least once weekly";
        textFields[10] = "I feel comfortable sharing my data with my healthcare provider during virtual visits";
        textFields[11] = "I am able to contact my healthcare provider in case of need and emergency";
    }


    public void onSubmit(View view) {

        for (int i = 0; i <= 11; i++){
            storeUserResponse(textFields[i], radioGroups[i]);
        }
        again.setVisibility(View.VISIBLE);
        submit.setVisibility(View.INVISIBLE);
    }

    private void storeUserResponse(String txtView, RadioGroup radioGroup) {
        //get the selected radioButton id
        int selectedId = radioGroup.getCheckedRadioButtonId();

        //findViewById -> rb
        RadioButton rb = findViewById(selectedId);

        // get the tag of rb and it's mod with 5 -> 0..4
        int tag = Integer.parseInt((String) rb.getTag());
        tag %= 5;

        // store txtView -> string value( choice[tag%5])
        profileMap.put(txtView, choice[tag]);

        rootRef.child("feedback").child(currentUserID).updateChildren(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FeedbackActivity.this, "Feedback Submitted Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onAgain(View view) {
        Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}