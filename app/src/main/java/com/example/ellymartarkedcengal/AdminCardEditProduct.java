package com.example.ellymartarkedcengal;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
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
        String productId = getIntent().getStringExtra("productId");
        if (productId != null) {
            getProductDetailsById(productId);


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

    private void getProductDetailsById(String productId) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    product = dataSnapshot.getValue(Products.class);
                    if (product != null) {
                        // Update UI with product details
                        updateUI(product);
                    }
                } else {
                    // Handle if the product does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void updateUI(Products product) {
        TextView productNameTextView = findViewById(R.id.textView8);
        EditText editTextProductName = findViewById(R.id.editTextEditName);
        EditText editTextProductPrice = findViewById(R.id.editTextEditPrice);
        ImageView productImageView = findViewById(R.id.admin_productPic);

        if (productNameTextView != null && editTextProductName != null && editTextProductPrice != null && productImageView != null) {
            productNameTextView.setText(product.getName());
            editTextProductName.setText(product.getName());
            editTextProductPrice.setText(String.valueOf(product.getPrice()));

            editTextProductPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String input = editable.toString();
                    if (!isValidCurrency(input)) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
            });

            String imageUrl = product.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(productImageView);
            } else {
                productImageView.setImageResource(R.drawable.baseline_product_list_24);
            }
        }
    }

    private boolean isValidCurrency(String str) {
        // Allow digits and up to two decimal points
        return str.matches("\\d{0,7}(\\.\\d{0,2})?") && !str.matches("\\d*\\.0$");
    }

    private void updateProductDetails() {
        // Get updated details from the EditText fields
        String updatedProductName = ((EditText) findViewById(R.id.editTextEditName)).getText().toString().trim();
        String updatedProductPrice = ((EditText) findViewById(R.id.editTextEditPrice)).getText().toString().trim();

        if (product != null) {
            // Restrict the price to two decimal places
            if (!isValidCurrency(updatedProductPrice)) {
                Toast.makeText(AdminCardEditProduct.this, "Invalid price format. Please enter up to two decimal places.", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products").child(product.getProductId());
            productRef.child("name").setValue(updatedProductName);
            productRef.child("price").setValue(Double.parseDouble(updatedProductPrice));

            Toast.makeText(AdminCardEditProduct.this, "Product details updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

}

