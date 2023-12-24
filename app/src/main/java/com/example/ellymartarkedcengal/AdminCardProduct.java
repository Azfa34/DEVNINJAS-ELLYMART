package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminCardProduct extends AppCompatActivity {



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
                ImageView productImageView = findViewById(R.id.admin_productPic);
                TextView productNameTextView = findViewById(R.id.admin_productNameTextView);
                TextView productPriceTextView = findViewById(R.id.admin_productPriceTextView);
                Button deleteBtn = findViewById(R.id.deleteBtn);
                Button updateBtn = findViewById(R.id.updateBtn);

                productNameTextView.setText(product.getName());
                productPriceTextView.setText(String.valueOf(product.getPrice()));

                // Check if the image URL is not empty or null before loading with Picasso
                if (!TextUtils.isEmpty(product.getImageUrl())) {
                    Picasso.get().load(product.getImageUrl()).into(productImageView);
                } else {
                    // Handle the case where the image URL is empty or null
                    // You can set a placeholder image or take any other appropriate action
                    productImageView.setImageResource(R.drawable.baseline_product_list_24);
                }


                // Hide the product ID (assuming you have a TextView for product ID)

                // Set click listeners for buttons
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle delete button click
                    }
                });

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle update button click
                    }
                });
            }
        }
    }



    // You need to implement a method to fetch product details by ID from your data source
    private Products getProductDetailsById(String productId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child(productId);

        // Assume 'Products' is the node in your Firebase database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // DataSnapshot contains the product details
                    Products product = dataSnapshot.getValue(Products.class);

                    // Now you have the actual product details
                    if (product != null) {
                        // Update the UI or perform any additional operations
                        updateUIWithProductDetails(product);
                    }
                } else {
                    // Product with the given ID does not exist
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
        Picasso.get().load(product.getImageUrl()).into(productImageView);

        // Hide the product ID (assuming you have a TextView for product ID)


        // Set click listeners for buttons
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click
            }
        });
    }
}

