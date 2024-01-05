package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button button;
    private Button btnEditAdminInfo;
    private DatabaseReference usersRef;
    private FirebaseUser user;
    private static final String STORAGE_PATH = "profile_images/";
    private StorageReference storageReference;
    private static final int EDIT_ADMIN_INFO_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private ImageView profileImageView;
    private Uri profileImageUri;
    private TextView adminInfoTextView;
    private TextView adminEmailTextView;
    private TextView adminTelNumberTextView;

    private SharedPreferences sharedPreferences;
     static final String PREF_NAME = "MyPrefs";
    static final String KEY_SELECTED_STATUS = "selectedStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminInfoTextView = findViewById(R.id.adminInfoTextView);
        adminTelNumberTextView = findViewById(R.id.adminTelNumberTextView);
        adminEmailTextView = findViewById(R.id.adminEmailTextView);

        storageReference = FirebaseStorage.getInstance().getReference();
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        btnEditAdminInfo = findViewById(R.id.btnEditAdminInfo);
        user = auth.getCurrentUser();
        profileImageView = findViewById(R.id.profileImageView);
        Button selectImageButton = findViewById(R.id.btnSelectProfileImage);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        selectImageButton.setOnClickListener(v -> openImagePicker());
        loadProfileImage();

        updateAdminDetails();
        setUpSpinner();


        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
            startActivity(intent);
            finish();
        });

        btnEditAdminInfo.setOnClickListener(v -> openEditAdminInfoActivity());
    }

    private void openEditAdminInfoActivity() {
        // Save user details before opening the edit activity
        saveUserDetails();

        Intent intent = new Intent(this, EditAdminInfoActivity.class);
        intent.putExtra("existingAdminInfo", adminInfoTextView.getText().toString());
        intent.putExtra("editedTelNumber", adminTelNumberTextView.getText().toString());
        intent.putExtra("isEditingMode", true);
        intent.putExtra("userId", user.getUid());
        startActivityForResult(intent, EDIT_ADMIN_INFO_REQUEST);
    }

    private void updateAdminDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String adminName = dataSnapshot.child("name").getValue(String.class);
                        String adminEmail = user.getEmail();
                        String telNumber = dataSnapshot.child("phonenumber").getValue(String.class);

                        // Display admin's info
                        adminInfoTextView.setText(adminName);
                        adminEmailTextView.setText(adminEmail);
                        adminTelNumberTextView.setText(telNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ADMIN_INFO_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                String editedAdminInfo = data.getStringExtra("editedAdminInfo");
                String editedTelNumber = data.getStringExtra("editedTelNumber");
                boolean isEditingMode = data.getBooleanExtra("isEditingMode", false);

                adminInfoTextView.setText(editedAdminInfo);
                adminTelNumberTextView.setText(editedTelNumber);

                if (!isEditingMode) {
                    // btnEditAdminInfo.setEnabled(true); // Uncomment this line if you need it
                }
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profileImageUri = data.getData();
            saveProfileImage();
            displayProfileImage();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Use the updated request code
    }

    private void saveProfileImage() {
        String userId = auth.getCurrentUser().getUid();

        if (profileImageUri != null) {
            StorageReference imageRef = storageReference.child(STORAGE_PATH + userId);
            UploadTask uploadTask = imageRef.putFile(profileImageUri);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (downloadUri != null) {
                        updateProfileImageInDatabase(userId, downloadUri.toString());
                        Toast.makeText(MainActivity.this, "Profile image saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Handle case where no image is selected
            updateProfileImageInDatabase(userId, null);
        }
    }

    private void updateProfileImageInDatabase(String userId, String imageUrl) {
        // Update user's profile image URL in Firebase Realtime Database
        usersRef.child(userId).child("profileImageUrl").setValue(imageUrl);
    }

    private void loadProfileImage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String imageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            // Load and display the image using Picasso
                            profileImageUri = Uri.parse(imageUrl);
                            displayProfileImage();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Log.e("FirebaseDatabase", "Error loading profile image from Firebase", databaseError.toException());
                }
            });
        }
    }

    private void displayProfileImage() {
        Log.d("DEBUG", "displayProfileImage: Attempting to load image");

        if (profileImageUri != null) {
            Picasso.get().load(profileImageUri).into(profileImageView);
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userId = user.getUid();

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String imageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Log.d("DEBUG", "displayProfileImage: Loading image from URL");
                                Picasso.get().load(imageUrl).into(profileImageView);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        Log.e("FirebaseDatabase", "Error loading profile image from Firebase", databaseError.toException());
                    }
                });
            }
        }
    }

    private void setUpSpinner() {
        String[] items = {"Open", "On Break", "Closed"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        // Get the last selected status from SharedPreferences
        String lastSelectedStatus = sharedPreferences.getString(KEY_SELECTED_STATUS, "Open");

        int position = Arrays.asList(items).indexOf(lastSelectedStatus);
        if (position >= 0 && position < items.length) {
            spinner.setSelection(position);
        }

        // Handle item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String selectedItem = items[position];
                Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Update the selected status in SharedPreferences
                sharedPreferences.edit().putString(KEY_SELECTED_STATUS, selectedItem).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }


    private void saveUserDetails() {
        String userId = auth.getCurrentUser().getUid();
        String name = adminInfoTextView.getText().toString().trim();
        String email = adminEmailTextView.getText().toString().trim();
        String phoneNumber = adminTelNumberTextView.getText().toString().trim();

        if (!name.isEmpty() && !phoneNumber.isEmpty()) {
            User user = new User(userId, name, email, phoneNumber, "");

            // Check if the profile picture has changed
            if (profileImageUri != null) {
                // Upload the image to Firebase Storage
                uploadProfileImage(userId, user);
            } else {
                // If no image selected, update other user details without affecting the profile picture
                updateUserDataInDatabase(userId, user);
                Toast.makeText(MainActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage(String userId, User user) {
        StorageReference imageRef = storageReference.child(STORAGE_PATH + userId);
        UploadTask uploadTask = imageRef.putFile(profileImageUri);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return imageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Image uploaded successfully, get the URL
                Uri downloadUri = task.getResult();
                if (downloadUri != null) {
                    user.setProfileImageUrl(downloadUri.toString());

                    // Save both userId and profile picture URL to Firebase Realtime Database
                    usersRef.child(userId).setValue(user);
                    Toast.makeText(MainActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserDataInDatabase(String userId, User user) {
        // Update user details in Firebase Realtime Database
        usersRef.child(userId).setValue(user);
    }
}
