package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginPatientActivity extends AppCompatActivity {

    TextView login;
    EditText editText_patName, editText_phn, editText_opd, verification_Code_entered_by_user;

    private String verificationCodeBySystem;
    private PhoneAuthProvider.ForceResendingToken mResendCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ProgressDialog loadingBar;

    Button verify_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);

        setTitle("Login");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        verification_Code_entered_by_user = findViewById(R.id.verification_code_entered_by_user);
        editText_patName = findViewById(R.id.editText_patNameL);
        editText_phn = findViewById(R.id.editText_phnL);
        editText_opd = findViewById(R.id.editText_opdL);

        verify_btn = findViewById(R.id.verify_btn);
        loadingBar = new ProgressDialog(this);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                final String patName = editText_patName.getText().toString();
                final String phn = editText_phn.getText().toString();

                loadingBar.setTitle("Phone Verification");
                loadingBar.setMessage("please wait, we are authenticating your phone...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                if (patName.isEmpty() && phn.isEmpty()) {
                    Toast.makeText(LoginPatientActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                    editText_patName.requestFocus();
                    editText_phn.requestFocus();
                } else {

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phn,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            LoginPatientActivity.this,               // Activity (for callback binding)
                            mCallbacks);
                }

            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = verification_Code_entered_by_user.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    verification_Code_entered_by_user.setError("Wrong OTP...");
                    verification_Code_entered_by_user.requestFocus();
                }

                else {
                    loadingBar.setTitle("Verification code");
                    loadingBar.setMessage("please wait, we are verifying code...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
                    signInUserByCredntials(credential);
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInUserByCredntials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                loadingBar.dismiss();
                Toast.makeText(LoginPatientActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                verification_Code_entered_by_user.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
                verify_btn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                loadingBar.dismiss();

                verificationCodeBySystem = s;
                mResendCode = forceResendingToken;
                Toast.makeText(LoginPatientActivity.this, "Verification Code Sent!", Toast.LENGTH_SHORT).show();

                login.setVisibility(View.INVISIBLE);
                verification_Code_entered_by_user.setVisibility(View.VISIBLE);
                verify_btn.setVisibility(View.VISIBLE);
            }
        };

    }

        private void signInUserByCredntials(PhoneAuthCredential credential) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(LoginPatientActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                loadingBar.dismiss();

                                Intent i = new Intent(getApplicationContext(), MainPage.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

                            }
                            else {
                                Toast.makeText(LoginPatientActivity.this, "Verification Failed!" ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
