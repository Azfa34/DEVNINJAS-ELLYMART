package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAdminInfoActivity extends AppCompatActivity {
    private EditText editAdminInfoEditText;
    private EditText editTelNumberEditText;
    private boolean isEditingMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_info);

        editAdminInfoEditText = findViewById(R.id.editTextNewAdminInfo);
        editTelNumberEditText = findViewById(R.id.editTextNewTelNumber);

        Intent intent = getIntent();
        String existingAdminInfo = intent.getStringExtra("existingAdminInfo");
        String editedTelNumber = intent.getStringExtra("editedTelNumber");
        isEditingMode = intent.getBooleanExtra("isEditingMode", false);

        editAdminInfoEditText.setText(existingAdminInfo);
        editTelNumberEditText.setText(editedTelNumber);

        Button saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdminInfo();
            }
        });
    }

    private void saveAdminInfo() {
        String editedAdminInfo = editAdminInfoEditText.getText().toString();
        String editedTelNumber = editTelNumberEditText.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("editedAdminInfo", editedAdminInfo);
        resultIntent.putExtra("editedTelNumber", editedTelNumber);
        resultIntent.putExtra("isEditingMode", isEditingMode);
        String userId = getIntent().getStringExtra("userId");
        setResult(RESULT_OK, resultIntent);

        // If it's in editing mode, update the details in the database
        if (isEditingMode) {
            updateDetailsInDatabase(editedAdminInfo, editedTelNumber);
        }

        finish();
    }

    private void updateDetailsInDatabase(String editedAdminInfo, String editedTelNumber) {
        // Retrieve the user ID or any unique identifier for the user
        // For demonstration purposes, I'm assuming you have a key or ID passed through Intent
        String userId = getIntent().getStringExtra("userId");

        // Update the details in the database using the retrieved user ID
        // Replace "users" with the actual reference to your database node
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        // Update the specific fields in the database
        userRef.child("name").setValue(editedAdminInfo);

        userRef.child("phonenumber").setValue(editedTelNumber);
    }
}
