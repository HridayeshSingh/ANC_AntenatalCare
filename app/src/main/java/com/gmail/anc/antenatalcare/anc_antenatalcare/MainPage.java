package com.gmail.anc.antenatalcare.anc_antenatalcare;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainPage extends AppCompatActivity {

    private String mode, selectedPatient;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference rootRef;

    private int requestType = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();

        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainPage.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainPage.this, requestType);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void patientDetails(View view){
        Intent intent = new Intent(getApplicationContext(), PatientDetails.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void pregnancyDetails(View view){
        Intent intent = new Intent(getApplicationContext(), PregnancyDetailsActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void investigation(View view){
        Intent intent = new Intent(getApplicationContext(), InvestigationsActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void appointmentDates(View view){
        Intent intent = new Intent(getApplicationContext(), AppointmentDatesActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void imageSample(View view){
        Intent intent = new Intent(getApplicationContext(), ImageSamplesActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void complaints(View view){
        Intent intent = new Intent(getApplicationContext(), ComplaintsActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    public void treatmentDetails(View view){
        Intent intent = new Intent(getApplicationContext(), TreatmentActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("selectedPatient", selectedPatient);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestType) {
            Toast.makeText(getApplicationContext(), "Start Download", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar, menu);

        if(mode.equals("doctor"))
            menu.removeItem(R.id.feedback);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.feedback:
                startActivity(new Intent(MainPage.this, FeedbackActivity.class));
                return true;
            case R.id.help:
                startActivity(new Intent(MainPage.this, HelpActivity.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "You are Logged out!",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intentSignUpInDecisionActivity = new Intent(getApplicationContext(), SignUpInDecisionActivity.class);
                intentSignUpInDecisionActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignUpInDecisionActivity);
                finish();
                return true;
            default:
                return false;
        }

    }


}
