package com.example.ellymartarkedcengal;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;
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

public class CustSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private SearchView searchView;
    private List<Products> productList;
    private List<Products> originalProductList;
    private DatabaseReference databaseReference;
    private TextView noResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        originalProductList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList, position -> {

        });
        recyclerView.setAdapter(productAdapter);

        searchView = findViewById(R.id.searchView);
        noResultsTextView = findViewById(R.id.noResultsTextView); // Add this line
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

        // Load initial data
        loadProductList();
    }

    private void loadProductList() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    productList.clear();
                    originalProductList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Products product = snapshot.getValue(Products.class);
                        productList.add(product);
                        originalProductList.add(product);
                    }
                    productAdapter.setProducts(productList);
                    updateNoResultsVisibility(); // Add this line
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CustSearchActivity", "Error loading data: " + databaseError.getMessage());
            }
        });
    }

    private void performSearch(String query) {
        List<Products> filteredList = filterList(originalProductList, query);
        productAdapter.setProducts(filteredList);
        updateNoResultsVisibility(); // Add this line
    }

    private List<Products> filterList(List<Products> productList, String query) {
        List<Products> filteredList = new ArrayList<>();
        for (Products product : productList) {
            if (product.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }

    private void updateNoResultsVisibility() {
        if (productAdapter.getItemCount() == 0) {
            noResultsTextView.setVisibility(TextView.VISIBLE);
        } else {
            noResultsTextView.setVisibility(TextView.GONE);
        }
    }
}
