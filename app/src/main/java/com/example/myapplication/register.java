package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnReg;

    private TextView login_txt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_reg);
        pass = findViewById(R.id.password_reg);

        btnReg = findViewById(R.id.reg_btn);

        login_txt = findViewById(R.id.login_txt);

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString();
                String mPass = pass.getText().toString();
                if(TextUtils.isEmpty(mEmail)) {
                    email.setError("Required Field..");
                    return;
                }
                 else if(TextUtils.isEmpty(mPass)){
                    pass.setError("Required Field..");
                    return;
                }
                 else if (!(mEmail.isEmpty() && mPass.isEmpty())){
                     mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (!task.isSuccessful()){
                                 Toast.makeText(getApplicationContext(), "Registration Unsuccessful. Please Try Again", Toast.LENGTH_SHORT).show();
                             } else{
                                 startActivity(new Intent(register.this, MainActivity.class));
                             }
                         }
                     });
                } else {
                    Toast.makeText(getApplicationContext(), "Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
