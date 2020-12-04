package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity {

    EditText editText_patName, editText_age, editText_husName, editText_address, editText_phn, editText_opd, editText_hosName;
    Button signUp;

    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;

    private String verificationCodeBySystem;
    private PhoneAuthProvider.ForceResendingToken mResendCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ProgressDialog loadingBar;

    Button verify_btn;
    EditText phoneNoEnteredByTheUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("Sign Up");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        InitializeFields();

        //String phn = editText_phn.getText().toString();
        //sendVerificationCodeToUser(phn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String patName = editText_patName.getText().toString();
                final String age = editText_age.getText().toString();
                final String husName = editText_husName.getText().toString();
                final String address = editText_address.getText().toString();
                final String phn = editText_phn.getText().toString();
                final String opd = editText_opd.getText().toString();
                final String hosName = editText_hosName.getText().toString();

                loadingBar.setTitle("Phone Verification");
                loadingBar.setMessage("please wait, we are authenticating your phone...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                if (TextUtils.isEmpty(patName)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Patient's Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(age)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Patient's Age", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(husName)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Husband's Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Patient's Address", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(phn)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Patient's Phone No.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(opd)) {
                    Toast.makeText(SignupActivity.this, "Please Enter OPD No.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(hosName)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Hospital Name", Toast.LENGTH_SHORT).show();
                }
                else {

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phn,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            SignupActivity.this,               // Activity (for callback binding)
                            mCallbacks);
                }

            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = phoneNoEnteredByTheUser.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    phoneNoEnteredByTheUser.setError("Wrong OTP...");
                    phoneNoEnteredByTheUser.requestFocus();
                    return;
                }

                loadingBar.setTitle("Verification code");
                loadingBar.setMessage("please wait, we are verifying code...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
                signInUserByCredentials(credential);

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {
                signInUserByCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {

                loadingBar.dismiss();
                Toast.makeText(SignupActivity.this, e.getMessage() ,Toast.LENGTH_SHORT).show();

                signUp.setVisibility(View.VISIBLE);
                phoneNoEnteredByTheUser.setVisibility(View.INVISIBLE);
                verify_btn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                loadingBar.dismiss();

                verificationCodeBySystem = s;
                mResendCode = forceResendingToken;
                Toast.makeText(SignupActivity.this, "Verification Code Sent!" ,Toast.LENGTH_SHORT).show();

                signUp.setVisibility(View.INVISIBLE);
                phoneNoEnteredByTheUser.setVisibility(View.VISIBLE);
                verify_btn.setVisibility(View.VISIBLE);
            }
        };
    }

    public void InitializeFields(){
        editText_patName = findViewById(R.id.editText_patNameL);
        editText_age = findViewById(R.id.editText_age);
        editText_husName = findViewById(R.id.editText_husName);
        editText_address = findViewById(R.id.editText_address);
        editText_phn = findViewById(R.id.editText_phnL);
        editText_opd = findViewById(R.id.editText_opdL);
        editText_hosName = findViewById(R.id.editText_hosName);
        verify_btn = findViewById(R.id.verify_btn);
        phoneNoEnteredByTheUser = findViewById(R.id.verification_code_entered_by_user);
        loadingBar = new ProgressDialog(this);
        signUp = findViewById(R.id.signUp);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {

        final String patName = editText_patName.getText().toString();
        final String age = editText_age.getText().toString();
        final String husName = editText_husName.getText().toString();
        final String address = editText_address.getText().toString();
        final String phn = editText_phn.getText().toString();
        final String opd = editText_opd.getText().toString();
        final String hosName = editText_hosName.getText().toString();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            loadingBar.dismiss();

                            // Create Database
                            users information = new users(
                                    patName, age, husName, address, phn, opd, hosName
                            );
                            databaseReference
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(information)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                SendUserToMainPageActivity();
                                                Toast.makeText(SignupActivity.this, "Patient's Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(SignupActivity.this, "Verification Failed!" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SendUserToMainPageActivity() {
        Intent i = new Intent(getApplicationContext(), MainPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
