package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AppointmentDatesActivity extends AppCompatActivity {

    private EditText editText_D1, editText_D2, editText_D3, editText_D4, editText_D5, editText_D6, editText_D7, editText_D8, editText_D9, editText_D10;
    private Button updateButton;

    private String mode;
    private String selectedPatient;
    private String currentUserID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_dates);

        setTitle("Appointment Dates");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializeFields();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        if(mode.equals("patient")) {
            currentUserID = firebaseAuth.getCurrentUser().getUid();
            updateButton.setVisibility(View.INVISIBLE);
            enableFields(false);
        } else {
            currentUserID = selectedPatient;
            updateButton.setVisibility(View.VISIBLE);
            enableFields(true);
        }

        retrieveUserInfo();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void enableFields(boolean choice) {
        editText_D1.setEnabled(choice);
        editText_D2.setEnabled(choice);
        editText_D3.setEnabled(choice);
        editText_D4.setEnabled(choice);
        editText_D5.setEnabled(choice);
        editText_D6.setEnabled(choice);
        editText_D7.setEnabled(choice);
        editText_D8.setEnabled(choice);
        editText_D9.setEnabled(choice);
        editText_D10.setEnabled(choice);

    }

    private void initializeFields() {
        editText_D1 = findViewById(R.id.editText_D1);
        editText_D2 = findViewById(R.id.editText_D2);
        editText_D3 = findViewById(R.id.editText_D3);
        editText_D4 = findViewById(R.id.editText_D4);
        editText_D5 = findViewById(R.id.editText_D5);
        editText_D6 = findViewById(R.id.editText_D6);
        editText_D7 = findViewById(R.id.editText_D7);
        editText_D8 = findViewById(R.id.editText_D8);
        editText_D9 = findViewById(R.id.editText_D9);
        editText_D10 = findViewById(R.id.editText_D10);
        updateButton = findViewById(R.id.updateButton);
    }

    private void retrieveUserInfo(){

        rootRef.child("appointments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if ((snapshot.exists()) && snapshot.getKey().equals(currentUserID)) {

                    String retrieveD1 = snapshot.child("Date-01").getValue().toString();
                    String retrieveD2 = snapshot.child("Date-02").getValue().toString();
                    String retrieveD3 = snapshot.child("Date-03").getValue().toString();
                    String retrieveD4 = snapshot.child("Date-04").getValue().toString();
                    String retrieveD5 = snapshot.child("Date-05").getValue().toString();
                    String retrieveD6 = snapshot.child("Date-06").getValue().toString();
                    String retrieveD7 = snapshot.child("Date-07").getValue().toString();
                    String retrieveD8 = snapshot.child("Date-08").getValue().toString();
                    String retrieveD9 = snapshot.child("Date-09").getValue().toString();
                    String retrieveD10 = snapshot.child("Date-10").getValue().toString();

                    editText_D1.setText(retrieveD1);
                    editText_D2.setText(retrieveD2);
                    editText_D3.setText(retrieveD3);
                    editText_D4.setText(retrieveD4);
                    editText_D5.setText(retrieveD5);
                    editText_D6.setText(retrieveD6);
                    editText_D7.setText(retrieveD7);
                    editText_D8.setText(retrieveD8);
                    editText_D9.setText(retrieveD9);
                    editText_D10.setText(retrieveD10);
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

    public void updateDates(View view) {

        String setD1 = editText_D1.getText().toString();
        String setD2 = editText_D2.getText().toString();
        String setD3 = editText_D3.getText().toString();
        String setD4 = editText_D4.getText().toString();
        String setD5 = editText_D5.getText().toString();
        String setD6 = editText_D6.getText().toString();
        String setD7 = editText_D7.getText().toString();
        String setD8 = editText_D8.getText().toString();
        String setD9 = editText_D9.getText().toString();
        String setD10 = editText_D10.getText().toString();

        HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("Date-01", setD1);
            profileMap.put("Date-02", setD2);
            profileMap.put("Date-03", setD3);
            profileMap.put("Date-04", setD4);
            profileMap.put("Date-05", setD5);
            profileMap.put("Date-06", setD6);
            profileMap.put("Date-07", setD7);
            profileMap.put("Date-08", setD8);
            profileMap.put("Date-09", setD9);
            profileMap.put("Date-10", setD10);
        rootRef.child("appointments").child(currentUserID).setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AppointmentDatesActivity.this, "Dates Updated Successfully...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    /*public void NotificationUpdate(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AppointmentDatesActivity.this, "My Notification")
            .setContentTitle("New Notification")
            .setContentText("Appointment Dates added")
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true);

        //Intent notificationIntent = new Intent(AppointmentDatesActivity.this, AppointmentDatesActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(AppointmentDatesActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AppointmentDatesActivity.this);
        managerCompat.notify(1, builder.build());

     */
}

    /* private void sendNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("New Notification");
        String content = data.get("Appointment Dates added");

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "ANC_app";

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "ANC_app Notification",
                    NotificationManager.IMPORTANCE_MAX);

            channel.setDescription("ANC_app channel for app test ANC");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{0,1000,500,1000});
            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setTicker("Hearty365")
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo("Info")
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }

     */