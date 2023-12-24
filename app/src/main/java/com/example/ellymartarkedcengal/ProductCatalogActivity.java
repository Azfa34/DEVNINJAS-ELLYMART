package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ProductCatalogActivity extends AppCompatActivity {

    private List<Products> productList;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_catalog);

        // Initialize your product list (you might fetch it from Firebase)
        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize the adapter with the click listener
        productAdapter = new ProductAdapter(this, productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Products selectedProduct = productList.get(position);
                // Redirect to the product card activity with the selected product ID or details
                openProductCardActivity(selectedProduct.getProductId());
            }
        });
        recyclerView.setAdapter(productAdapter);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        // Fetch data from Firebase
        fetchDataFromFirebase();

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Products selectedProduct = productList.get(position);
                // Redirect to the product card activity with the selected product ID or details
                openProductCardActivity(selectedProduct.getProductId());
            }
        });
    }

    private void fetchDataFromFirebase() {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear(); // Clear existing data

                // Iterate through the dataSnapshot to get product data
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Products product = productSnapshot.getValue(Products.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }

                // Notify the adapter that the data has changed
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void openProductCardActivity(String productId) {
        // Create an Intent to start the ProductCardActivity
        Intent intent = new Intent(this, AdminCardProduct.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }
}
