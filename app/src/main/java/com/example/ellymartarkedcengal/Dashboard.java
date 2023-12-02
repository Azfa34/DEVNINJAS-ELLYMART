package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import android.widget.TextView;


public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        Intent intent = getIntent();
        boolean isAdminUser = intent.getBooleanExtra("isAdminUser", true);

        setNavigationViewHeader(isAdminUser);

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

                } else if (itemId == R.id.profile) {
                    Intent intent = new Intent(Dashboard.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Dashboard.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                }

                return false;
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
}

