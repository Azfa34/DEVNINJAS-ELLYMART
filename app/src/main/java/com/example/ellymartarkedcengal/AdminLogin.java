package com.example.ellymartarkedcengal;

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
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {

    private EditText adminEmailEditText, adminPasswordEditText;
    private Button loginButton,forgotPassButton;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();
        adminEmailEditText = findViewById(R.id.etAdminEmail);
        adminPasswordEditText = findViewById(R.id.etAdminPassword);
        loginButton = findViewById(R.id.btnAdminLogin);
        forgotPassButton = findViewById(R.id.btnAdminForgotPass);

        loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                // Retrieve admin login data from the EditText fields
                String adminEmail = adminEmailEditText.getText().toString();
                String adminPassword = adminPasswordEditText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), AdminRegistration.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminLogin.this, "Admin login successful!", Toast.LENGTH_SHORT).show();

                mAuth.signInWithEmailAndPassword(adminEmail, adminPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(AdminLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        forgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
