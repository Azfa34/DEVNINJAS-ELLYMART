package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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

public class NewItemPage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private DatabaseReference databaseReference;

    private ImageView imageViewItem;
    Button buttonPickPhotoNewItem, buttonSaveItem;
    EditText editTextItemName, editTextItemPrice, editTextItemDescription;

    private Uri selectedImageUri;

    //Firebase Storage

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_page);
        FirebaseApp.initializeApp(this);
        imageViewItem = findViewById(R.id.imageViewItem);
        buttonPickPhotoNewItem = findViewById(R.id.buttonPickPhotoNewItem);
        buttonSaveItem = findViewById(R.id.buttonSaveItem);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemDescription = findViewById(R.id.editTextItemDescription);
        editTextItemPrice.addTextChangedListener(new CurrencyTextWatcher());

        buttonPickPhotoNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImagePicker();
            }
        });
        buttonSaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidCurrency(editTextItemPrice.getText().toString())) {
                    saveNewItem();
                } else {
                    Toast.makeText(NewItemPage.this, "Please enter a valid price with up to 2 decimal places", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isValidCurrency(String str) {
        // Allow digits and up to two decimal places
        return str.matches("\\d{0,7}(\\.\\d{0,2})?") && !str.matches("\\d*\\.0$");
    }

    private class CurrencyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String currentText = editable.toString();
            if (!isValidCurrency(currentText)) {
                // Remove the last entered character if the currency format is invalid
                editable.delete(editable.length() - 1, editable.length());
            }
        }

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
                imageViewItem.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveNewItem() {
        String itemName = editTextItemName.getText().toString();
        double itemPrice = Double.parseDouble(editTextItemPrice.getText().toString());
        String itemDescription = editTextItemDescription.getText().toString();

        if (!itemName.isEmpty()) {
            Products newItem = new Products(itemName, itemPrice, itemDescription, null);

            DatabaseReference newProductRef = databaseReference.child("products").push();
            String productId = newProductRef.getKey();
            newItem.setProductId(productId);

            newProductRef.setValue(newItem)
                    .addOnSuccessListener(aVoid -> {
                        // Successfully saved item details, now upload the image
                        StorageReference imageRef = storageReference.child("product_images/" + productId + ".jpg");
                        ImageView imageView = findViewById(R.id.imageViewItem); // Update to the correct ID

                        if (imageView != null && imageView.getDrawable() != null) {
                            imageView.setDrawingCacheEnabled(true);
                            imageView.buildDrawingCache();
                            Bitmap bitmap = imageView.getDrawingCache();

                            if (bitmap != null) {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                // Upload image to Firebase Storage
                                UploadTask uploadTask = imageRef.putBytes(data);

                                uploadTask.addOnSuccessListener(taskSnapshot -> {
                                    // Image uploaded successfully, get the download URL
                                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String downloadUrl = uri.toString();

                                        // Update the product with the download URL
                                        DatabaseReference productToUpdateRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);
                                        productToUpdateRef.child("imageUrl").setValue(downloadUrl)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        newItem.setImageUrl(downloadUrl);
                                                        Toast.makeText(NewItemPage.this, "Item saved successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(NewItemPage.this, ProductCatalogActivity.class);
                                                        startActivity(intent);
                                                        finish(); //
                                                    } else {
                                                        Toast.makeText(NewItemPage.this, "Failed to update item with download URL", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }).addOnFailureListener(e -> {
                                        e.printStackTrace();
                                    });
                                }).addOnFailureListener(e -> {
                                    e.printStackTrace();
                                    Toast.makeText(NewItemPage.this, "Failed to upload item image" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                            } else {
                                Toast.makeText(NewItemPage.this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(NewItemPage.this, "ImageView or Drawable is null", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(NewItemPage.this, "Error saving item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(NewItemPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
