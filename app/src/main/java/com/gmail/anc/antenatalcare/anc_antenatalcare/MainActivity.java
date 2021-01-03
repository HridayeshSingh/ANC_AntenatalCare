package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private Intent intent;
    private ArrayList<String> doctorsList = new ArrayList<>();

    private boolean hasTimeElapsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        retrieve();

        ImageView logo = findViewById(R.id.logo);
        logo.animate().alpha(1).setDuration(5000);
        TextView name = findViewById(R.id.name);
        name.animate().alpha(1).setDuration(8000);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                TextView tapAnywhere = findViewById(R.id.tapAnywhere);
                tapAnywhere.animate().alpha(1).setDuration(3000);
                hasTimeElapsed = true;
            }
        };
        countDownTimer.start();
    }

    private void retrieve() {
        FirebaseDatabase.getInstance().getReference().child("doctors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                doctorsList.add(snapshot.getKey());
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

    public void screenTapped(View view) {
        if(hasTimeElapsed) {
            if (currentUser != null) {
                if (doctorsList.contains(currentUser.getUid())) {
                    intent = new Intent(getApplicationContext(), SelectPatientActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainPage.class);
                    intent.putExtra("mode", "patient");
                }
            } else {
                intent = new Intent(getApplicationContext(), SignUpInDecisionActivity.class);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
