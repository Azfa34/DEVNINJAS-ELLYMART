package com.example.ellymartarkedcengal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText adminEmailEditText;
    Button resetPasswordButton;
    Button loginButton;
    FirebaseAuth mAuth;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        adminEmailEditText = findViewById(R.id.etAdminEmail);
        resetPasswordButton = findViewById(R.id.btnAdminReset);
        loginButton = findViewById(R.id.btnAdminLogin);
        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = adminEmailEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    adminEmailEditText.setError("Invalid email address");
                }
            }
        });
    }


        private void ResetPassword() {
            resetPasswordButton.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ForgotPassword.this, "Password reset email sent to " + adminEmailEditText, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword.this, AdminLogin.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ForgotPassword.this, "Error : -" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    resetPasswordButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }

