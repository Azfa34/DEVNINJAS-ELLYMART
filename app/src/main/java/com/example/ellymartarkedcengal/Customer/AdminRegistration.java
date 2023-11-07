package com.example.ellymartarkedcengal.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ellymartarkedcengal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AdminRegistration extends AppCompatActivity {

    private EditText adminNameEditText, adminEmailEditText, adminPasswordEditText;
    private Button registerButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        mAuth= FirebaseAuth.getInstance();
        adminNameEditText = findViewById(R.id.etAdminName);
        adminEmailEditText = findViewById(R.id.etAdminEmail);
        adminPasswordEditText = findViewById(R.id.etAdminPassword);
        registerButton = findViewById(R.id.btnRegisterAdmin);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve admin registration data from the EditText fields
                String adminName = adminNameEditText.getText().toString();
                String adminEmail = adminEmailEditText.getText().toString();
                String adminPassword = adminPasswordEditText.getText().toString();

                if(TextUtils.isEmpty(adminEmail)){
                    Toast.makeText(AdminRegistration.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                // You can implement your registration logic here, e.g., store the admin data in a database.
                if(TextUtils.isEmpty(adminPassword)) {
                    Toast.makeText(AdminRegistration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(adminEmail, adminPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AdminRegistration.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AdminRegistration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                // Display a message to indicate successful registration
                Toast.makeText(AdminRegistration.this, "Admin registered successfully!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}