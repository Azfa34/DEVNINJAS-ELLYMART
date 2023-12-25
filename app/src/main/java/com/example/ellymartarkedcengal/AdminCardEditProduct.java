package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminCardEditProduct extends AppCompatActivity {

    private Products product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_product);

        // Get the product ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productId")) {
            String productId = intent.getStringExtra("productId");

            // Now you can fetch the product details using the productId
            // Fetch product details and update UI as needed
            getProductDetailsById(productId);

            // Set up the "Update" button click listener
            Button updateButton = findViewById(R.id.btnProductUpdate);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call a method to update the product details
                    updateProductDetails();
                }
            });
        }
    }

    // Implement this method to fetch product details by ID from your data source
    private void getProductDetailsById(String productId) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Product data exists, retrieve and update UI
                    product = dataSnapshot.getValue(Products.class);

                    if (product != null) {
                        // Update UI with product details
                        updateUI(product);
                    }
                } else {
                    // Product data does not exist
                    // Handle the case where the product is not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void updateUI(Products product) {
        // Update UI with product details
        // For example, if you have TextViews and an ImageView in your activity, update them here
        TextView productNameTextView = findViewById(R.id.textView8);
        EditText editTextProductName = findViewById(R.id.editTextEditName);
        EditText editTextProductPrice = findViewById(R.id.editTextEditPrice);
        ImageView productImageView = findViewById(R.id.admin_productPic);

        if (productNameTextView != null && editTextProductName != null && editTextProductPrice != null && productImageView != null) {
            productNameTextView.setText(product.getName());
            editTextProductName.setText(product.getName());
            editTextProductPrice.setText(String.valueOf(product.getPrice()));

            // Check if the image URL is not empty or null before loading with Picasso
            String imageUrl = product.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(productImageView);
            } else {
                // Handle the case where the image URL is empty or null
                // You can set a placeholder image or take any other appropriate action
                productImageView.setImageResource(R.drawable.baseline_product_list_24);
            }
        }
    }

    private void updateProductDetails() {
        // Get updated details from the EditText fields
        String updatedProductName = ((EditText) findViewById(R.id.editTextEditName)).getText().toString().trim();
        String updatedProductPrice = ((EditText) findViewById(R.id.editTextEditPrice)).getText().toString().trim();

        // Perform validation on the updated details if needed

        // Update the product details in the database
        if (product != null) {
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products").child(product.getProductId());
            productRef.child("name").setValue(updatedProductName);
            productRef.child("price").setValue(Double.parseDouble(updatedProductPrice));

            // You can also update other details like image URL if needed

            // Show a success message or handle the update completion
            // For example, you can display a Toast message
            Toast.makeText(AdminCardEditProduct.this, "Product details updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
