package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Role extends AppCompatActivity {
    private boolean isAdminUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        Button adminButton = findViewById(R.id.adminButton);
        Button customerButton = findViewById(R.id.customerButton);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAdminRegistration();
            }
        });
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDashboard();
            }
        });
    }
    private void navigateToAdminRegistration(){
        Intent adminRegistrationIntent = new Intent(this, Dashboard.class);
        startActivity(adminRegistrationIntent);
    }
    private void navigateToDashboard(){
        Intent dashboardIntent = new Intent(this,Dashboard.class);
        dashboardIntent.putExtra("isAdminUser", isAdminUser);
        startActivity(dashboardIntent);
    }
}