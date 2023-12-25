package com.example.ellymartarkedcengal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminCardProduct extends AppCompatActivity {

    private Products product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_card_product);

        // Get the product ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productId")) {
            String productId = intent.getStringExtra("productId");

            // Now you can fetch the product details using the productId
            // For simplicity, let's assume you have a method to fetch product details by ID
            Products product = getProductDetailsById(productId);

            if (product != null) {
                // Update the UI with product details
                updateUIWithProductDetails(product);
            }
        }
    }

    private Products getProductDetailsById(String productId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child(productId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    product = dataSnapshot.getValue(Products.class);
                    if (product != null) {
                        updateUIWithProductDetails(product);
                    }
                } else {

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        // Placeholder return, the actual product details will be retrieved asynchronously
        return new Products(productId, 0.0, "", "");
    }

    // Update UI with product details
    private void updateUIWithProductDetails(Products product) {
        ImageView productImageView = findViewById(R.id.admin_productPic);
        TextView productNameTextView = findViewById(R.id.admin_productNameTextView);
        TextView productPriceTextView = findViewById(R.id.admin_productPriceTextView);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button updateBtn = findViewById(R.id.updateBtn);

        productNameTextView.setText(product.getName());
        productPriceTextView.setText(String.valueOf(product.getPrice()));

        String imageUrl = product.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(productImageView);
        } else {

            productImageView.setImageResource(R.drawable.baseline_product_list_24);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog before deleting the product
                showDeleteConfirmationDialog();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                Intent editIntent = new Intent(AdminCardProduct.this, AdminCardEditProduct.class);
                editIntent.putExtra("productId", product.getProductId());
                startActivity(editIntent);
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "Yes," delete the product
                if (product != null) {
                    deleteProduct(product.getProductId());
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "No," dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void deleteProduct(String productId) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Successfully deleted from Firebase
                    // Show a success message or navigate to a different screen
                    Toast.makeText(AdminCardProduct.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Finish the current activity or navigate to a different screen
                } else {
                    // Handle failure
                    Toast.makeText(AdminCardProduct.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

