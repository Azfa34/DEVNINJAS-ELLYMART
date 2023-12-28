package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    DatabaseReference usersRef;
    RecyclerView productRecyclerView;
    RecyclerView wishlistRecyclerView;
    private ProductAdapter productAdapter;
    private WishlistAdapter wishlistAdapter;
    private List<Products> productList;
    private List<Wishlist> wishlist;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openNotificationActivity(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Intent intent = getIntent();
        boolean isAdminUser = intent.getBooleanExtra("isAdminUser", true);

        productRecyclerView = findViewById(R.id.recyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productList = new ArrayList<>();

        // Pass an instance of the ProductAdapter.OnItemClickListener to the constructor
        productAdapter = new ProductAdapter(this, productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Products selectedProduct = productList.get(position);
                openProductCardActivity(selectedProduct.getProductId());
            }
        });

        productRecyclerView.setAdapter(productAdapter);

        wishlist = new ArrayList<>();
        wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        wishlistAdapter = new WishlistAdapter(this, wishlist);
        wishlistRecyclerView.setAdapter(wishlistAdapter);

        setNavigationViewHeader(isAdminUser);
        fetchProductsFromDatabase();

        String[] items = {"Open", "On Break", "Closed"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String selectedItem = items[position];
                Toast.makeText(Dashboard.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });



        Button btnNotification = findViewById(R.id.btnNotifications);
        if (isAdminUser) {
            btnNotification.setVisibility(View.VISIBLE);
            btnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNotificationActivity(v);
                }
            });
        } else {
            btnNotification.setVisibility(View.GONE);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    Intent intent = new Intent(Dashboard.this, Dashboard.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Home Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.wishlist) {
                    Intent intent = new Intent(Dashboard.this, WishlistForm.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Wishlist Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.wishlistList) {
                    Intent intent = new Intent(Dashboard.this, WishlistCatalogActivity.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Wishlist List Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.activity) {
                    Intent intent = new Intent(Dashboard.this, ActivityReport.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Activity Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.additem) {
                    Intent intent = new Intent(Dashboard.this, NewItemPage.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Add New Item Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.productlist) {
                    Intent intent = new Intent(Dashboard.this, ProductCatalogActivity.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Product List Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.notification) {
                    Intent intent = new Intent(Dashboard.this, NotificationActivity.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Notification Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.notificationReport) {
                    Intent intent = new Intent(Dashboard.this, Cust_NotificationReport.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Notification Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.productlist) {
                    Intent intent = new Intent(Dashboard.this, ProductCatalogActivity.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Product List Selected", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.profile) {
                    Intent intent = new Intent(Dashboard.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    private void fetchProductsFromDatabase() {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products product = snapshot.getValue(Products.class);
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

        // Fetch wishlist from Firebase
        DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference().child("wishlist");
        wishlistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wishlist.clear();
                for (DataSnapshot wishlistSnapshot : dataSnapshot.getChildren()) {
                    Wishlist wishlistItem = wishlistSnapshot.getValue(Wishlist.class);
                    if (wishlistItem != null) {
                        wishlist.add(wishlistItem);
                    }
                }
                wishlistAdapter.notifyDataSetChanged();  // Update the wishlist adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void setNavigationViewHeader(boolean isAdminUser) {
        View headerLayout = navigationView.getHeaderView(0);
        TextView ellysMartTextView = headerLayout.findViewById(R.id.ellys_mart_text);
        TextView adminSiteTextView = headerLayout.findViewById(R.id.admin_site_text);

        ellysMartTextView.setVisibility(View.VISIBLE);

        adminSiteTextView.setVisibility(isAdminUser ? View.VISIBLE : View.GONE);

        if (isAdminUser) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.admin_menu);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.cust_menu);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openProductCardActivity(String productId) {
        Intent intent = new Intent(this, AdminCardProduct.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }
}
