package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText WishItemNameEditText;
    EditText WishItemNameDescription;
    private Uri selectedImageUri;

    // Firebase Storage
     FirebaseStorage storage;
     StorageReference storageReference;

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


        WishItemNameEditText = findViewById(R.id.editTextWName);
        WishItemNameDescription = findViewById(R.id.editTextWDescription);



        btnPickPhotoWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImagePicker();
            }
        });
        btnMakeWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveWishlistToFirebase();
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

    private void saveWishlistToFirebase() {
        String itemName = WishItemNameEditText.getText().toString().trim();
        String itemDescription = WishItemNameDescription.getText().toString().trim();

        if (!itemName.isEmpty() && !itemDescription.isEmpty()) {

            Wishlist wishlist = new Wishlist(itemName, itemDescription);

            DatabaseReference newProductRef = wishesRef.child("wishlist").push();
            String wishProductId = newProductRef.getKey();
            wishlist.setWishProductId(wishProductId);

            wishesRef.child("wishlist").child(wishProductId).setValue(wishlist);
            Toast.makeText(WishlistForm.this, "Successful", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(WishlistForm.this, "Failed to submit wishlist item", Toast.LENGTH_SHORT).show();
        }
    }




 /*private void uploadImageToFirebaseStorage(String wishKey) {
        StorageReference imageRef = storageReference.child("images/" + wishKey + ".jpg");

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

                // Update the wish with the download URL
                DatabaseReference wishToUpdateRef = FirebaseDatabase.getInstance().getReference().child("wishes").child(wishKey);
                wishToUpdateRef.child("imageUrl").setValue(downloadUrl)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(WishlistForm.this, "Wish submitted successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(WishlistForm.this, Dashboard.class));
                        })
                        .addOnFailureListener(e -> {
                            e.printStackTrace();
                            Toast.makeText(WishlistForm.this, "Failed to update wish with download URL", Toast.LENGTH_SHORT).show();
                        });
            }).addOnFailureListener(e -> {
                e.printStackTrace();
            });
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            // Handle failure to upload image
        });
    }*/

}
