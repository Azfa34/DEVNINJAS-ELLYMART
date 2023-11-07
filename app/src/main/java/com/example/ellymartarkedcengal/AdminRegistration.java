package com.example.ellymartarkedcengal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AdminRegistration extends AppCompatActivity {

    private EditText adminNameEditText, adminEmailEditText, adminPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

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

                // You can implement your registration logic here, e.g., store the admin data in a database.

                // Display a message to indicate successful registration
                Toast.makeText(AdminRegistration.this, "Admin registered successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}