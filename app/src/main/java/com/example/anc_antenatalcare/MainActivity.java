package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logo = findViewById(R.id.logo);
        logo.animate().alpha(1).setDuration(5000);
        TextView name = findViewById(R.id.name);
        name.animate().alpha(1).setDuration(8000);

        final Intent intent = new Intent(getApplicationContext(), DecisionPage.class);
        CountDownTimer countDownTimer = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(intent);
            }
        };
        countDownTimer.start();
    }
}
