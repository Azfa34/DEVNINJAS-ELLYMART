package com.example.ellymartarkedcengal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    EditText adminEmailEditText;
    Button resetPasswordButton;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        adminEmailEditText = findViewById(R.id.etAdminEmail);
        resetPasswordButton = findViewById(R.id.btnAdminReset);
        loginButton = findViewById(R.id.btnAdminLogin);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = adminEmailEditText.getText().toString();
                if (isValidEmail(email)){
                    Toast.makeText(ForgotPassword.this, "Password reset email sent to " + email, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ForgotPassword.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),AdminLogin.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}