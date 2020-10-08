package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText editText_patName, editText_phn, editText_opd;

    String verificationCodeBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_patName = findViewById(R.id.editText_patNameL);
        editText_phn = findViewById(R.id.editText_phnL);
        editText_opd = findViewById(R.id.editText_opdL);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                final String patName = editText_patName.getText().toString();
                final String phn = editText_phn.getText().toString();

                final String opd = editText_opd.getText().toString();

                if (patName.isEmpty() && phn.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(patName.isEmpty() && phn.isEmpty())) {

                    Intent intent = new Intent(getApplicationContext(), VerifyPhn.class);
                    intent.putExtra("phn", phn);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
