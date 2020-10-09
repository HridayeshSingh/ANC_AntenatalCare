package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    EditText editText_patName, editText_age, editText_husName, editText_address, editText_phn, editText_opd, editText_hosName;
    Button signUp;

    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editText_patName = findViewById(R.id.editText_patNameL);
        editText_age = findViewById(R.id.editText_age);
        editText_husName = findViewById(R.id.editText_husName);
        editText_address = findViewById(R.id.editText_address);
        editText_phn = findViewById(R.id.editText_phnL);
        editText_opd = findViewById(R.id.editText_opdL);
        editText_hosName = findViewById(R.id.editText_hosName);

        signUp = findViewById(R.id.signUp);

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

                // Create Database
                users information = new users(
                        patName, age, husName, address, phn, opd, hosName
                );

                FirebaseDatabase.getInstance().getReference("users")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SignupActivity.this, "Sign Up Completed", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(), VerifyPhn.class);
                intent.putExtra("phn", phn);
                startActivity(intent);


            }
        });
    }
}
