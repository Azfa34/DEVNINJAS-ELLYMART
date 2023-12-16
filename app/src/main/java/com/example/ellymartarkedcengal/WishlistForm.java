package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WishlistForm extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    DatabaseReference wishesRef;
    private ImageView imageView;
    private Uri selectedImageUri;

    // Firebase Storage
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_form);
        FirebaseApp.initializeApp(this);
        imageView = findViewById(R.id.imageViewItem1);
        Button btnPickPhotoWish = findViewById(R.id.btnPickPhotoWish);
        Button btnMakeWish = findViewById(R.id.btnMakeWish);
        wishesRef = FirebaseDatabase.getInstance().getReference();
        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnPickPhotoWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImagePicker();
            }
        });
        btnMakeWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImageToFirebase();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebase() {
        if (selectedImageUri != null) {
            StorageReference imageRef = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            // Upload image to Firebase Storage
            UploadTask uploadTask = imageRef.putBytes(data);

            uploadTask.addOnSuccessListener(taskSnapshot -> {

                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();

                    saveDownloadUrlToDatabase(downloadUrl);
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                });
            }).addOnFailureListener(e -> {
                e.printStackTrace();
                // Handle failure to upload image
            });
        }
    }

    private void saveDownloadUrlToDatabase(String downloadUrl) {

        // Creating a new Wish object
        Wishlist newWish = new Wishlist(downloadUrl, "My Wishlist Description");

        DatabaseReference wishesRef = FirebaseDatabase.getInstance().getReference().child("wishes");
        wishesRef.push().setValue(newWish)
                .addOnSuccessListener(aVoid -> {

                    Toast.makeText(WishlistForm.this, "Wish submitted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WishlistForm.this, Dashboard.class));
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();

                    Toast.makeText(WishlistForm.this, "Failed to submit wish", Toast.LENGTH_SHORT).show();
                });
    }
}
