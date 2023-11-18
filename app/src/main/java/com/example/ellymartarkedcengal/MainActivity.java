package com.example.ellymartarkedcengal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    Button saveButton;
    TextView textview;
    EditText NameEditText;
    EditText PhoneNumberEditText;
    DatabaseReference usersRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textview = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        NameEditText = findViewById(R.id.etAdminName);
        PhoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        saveButton = findViewById(R.id.buttonSave);

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
            startActivity(intent);
            finish();
        } else {
            textview.setText(user.getEmail());

        }

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserDetails();
            }

            private void saveUserDetails() {
                String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                String name = NameEditText.getText().toString().trim();
                String number = PhoneNumberEditText.getText().toString().trim();

                if (!name.isEmpty() && !number.isEmpty()) {
                    User user = new User(userId, name, number);
                    usersRef.child(userId).setValue(user);

                    Toast.makeText(MainActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] items = {"Open", "On Break", "Closed"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner); // Make sure to change this to your actual Spinner ID
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                // Handle the item selection here
                String selectedItem = items[position];
                Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
            });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(intent);
                finish();
            }

        });

    }


}
