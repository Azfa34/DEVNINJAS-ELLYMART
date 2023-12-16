package com.example.ellymartarkedcengal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditAdminInfoActivity extends AppCompatActivity {

    private EditText editTextNewAdminInfo;
    private boolean isEditingMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_info);

        editTextNewAdminInfo = findViewById(R.id.editTextNewAdminInfo);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndReturn();
            }
        });

        Intent intent = getIntent();
        isEditingMode = intent.getBooleanExtra("isEditingMode", false);

        if (isEditingMode) {
            String existingAdminInfo = intent.getStringExtra("existingAdminInfo");
            editTextNewAdminInfo.setText(existingAdminInfo);
        }
    }

    private void saveAndReturn() {
        String editedAdminInfo = editTextNewAdminInfo.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("editedAdminInfo", editedAdminInfo);
        resultIntent.putExtra("isEditingMode", isEditingMode);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
