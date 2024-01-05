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

        productImageView = findViewById(R.id.addProductPic);
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);


        Intent intent = getIntent();
        if (intent.hasExtra("productId")) {
            String productId = intent.getStringExtra("productId");

            // Fetch product details from the database based on productId
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);
            productRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        Products product = dataSnapshot.getValue(Products.class);

                        if (product != null) {
                            productNameTextView.setText(product.getName());
                            productPriceTextView.setText(String.valueOf(product.getPrice()));

                            String imageUrl = product.getImageUrl();
                            if (imageUrl != null && !imageUrl.isEmpty()) {

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

