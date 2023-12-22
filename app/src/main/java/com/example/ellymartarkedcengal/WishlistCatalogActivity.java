package com.example.ellymartarkedcengal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishlistCatalogActivity extends AppCompatActivity {

    private List<Wishlist> wishlistList;
    private RecyclerView wishlistRecyclerView;
    private WishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_catalog);

        wishlistList = new ArrayList<>();
        wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistAdapter = new WishlistAdapter(wishlistList);
        wishlistRecyclerView.setAdapter(wishlistAdapter);

        // Fetch data from Firebase
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        // Update database reference and onDataChange to fetch wishlist data
        DatabaseReference wishesRef = FirebaseDatabase.getInstance().getReference().child("wishlist");

        wishesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wishlistList.clear(); // Clear existing data

                // Iterate through the dataSnapshot to get wishlist item data
                for (DataSnapshot wishlistSnapshot : dataSnapshot.getChildren()) {
                    Wishlist wishlistItem = wishlistSnapshot.getValue(Wishlist.class);
                    if (wishlistItem != null) {
                        wishlistList.add(wishlistItem);
                    }
                }

                // Notify the adapter that the data has changed
                wishlistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
