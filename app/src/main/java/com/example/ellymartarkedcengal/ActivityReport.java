package com.example.ellymartarkedcengal;

// ActivityReport.java

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityReport extends AppCompatActivity {

    private RecyclerView recyclerViewWishlist;
    private WishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        recyclerViewWishlist = findViewById(R.id.recyclerViewWishlist);
        recyclerViewWishlist.setLayoutManager(new LinearLayoutManager(this));


        wishlistAdapter = new WishlistAdapter(this, getWishlistData());
        recyclerViewWishlist.setAdapter(wishlistAdapter);


    }


    private List<Wishlist> getWishlistData() {

        return new ArrayList<>();
    }
}
