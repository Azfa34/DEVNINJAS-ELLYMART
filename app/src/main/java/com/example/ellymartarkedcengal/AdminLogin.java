package com.example.ellymartarkedcengal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    private EditText adminEmailEditText, adminPasswordEditText;
    private Button loginButton,forgotPassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

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

                // You can implement your login logic here, e.g., check if the provided credentials are valid.

                // For this basic example, show a simple message to indicate a successful login.
                Toast.makeText(AdminLogin.this, "Admin login successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
