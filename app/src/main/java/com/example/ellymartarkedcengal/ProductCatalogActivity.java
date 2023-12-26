package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set the grid layout manager with 2 columns

        productAdapter = new ProductAdapter(this, productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Products selectedProduct = productList.get(position);
                openProductCardActivity(selectedProduct.getProductId());
            }
        });

        recyclerView.setAdapter(productAdapter);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        // Fetch data from Firebase
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear(); // Clear existing data

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Products product = productSnapshot.getValue(Products.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void openProductCardActivity(String productId) {
        Intent intent = new Intent(this, AdminCardProduct.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }
}
