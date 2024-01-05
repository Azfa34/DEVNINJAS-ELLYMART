package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CustCardProduct extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust_card_view_item);

        // Initialize views
        productImageView = findViewById(R.id.addProductPic);
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);

        // Get the product ID from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("productId")) {
            String productId = intent.getStringExtra("productId");

            // Fetch product details from the database based on productId
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);
            productRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Map the dataSnapshot to your Products class (assuming you have a class named Products)
                        Products product = dataSnapshot.getValue(Products.class);

                        // Update the views with the product details
                        if (product != null) {
                            productNameTextView.setText(product.getName());
                            productPriceTextView.setText(String.valueOf(product.getPrice()));

                            // Load image using your preferred image loading library (e.g., Picasso, Glide)
                            // Here, I'll assume you have a URL for the image
                            String imageUrl = product.getImageUrl();
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                // Use your preferred image loading library to load the image into productImageView
                                // For example, using Picasso:
                                Picasso.get().load(imageUrl).into(productImageView);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
}

