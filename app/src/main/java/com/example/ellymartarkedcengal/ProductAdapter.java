package com.example.ellymartarkedcengal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso; // Add this import
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Products> productList;
    private Context context;

    public ProductAdapter(Context context, List<Products> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Products product = productList.get(position);


        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));

        // Set your UI elements with product data
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));
        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the deleteProduct method with the product ID
                deleteProduct(product.getProductId());
            }
        });
        // Load image using Picasso
        Picasso.get().load(product.getImageUrl()).into(holder.productImageView);
    }

    private void deleteProduct(String productId) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId);
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Successfully deleted from Firebase, now update local data
                    int index = findProductIndex(productId);
                    if (index != -1) {
                        productList.remove(index);
                        notifyDataSetChanged();
                    }
                } else {
                    // Handle failure
                    Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int findProductIndex(String productId) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }

    public int getItemCount() {
        return productList.size();
    }

    public void setProducts(List<Products> products) {
        productList.clear();
        productList.addAll(products);
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        ImageView productImageView;
        Button btnDeleteProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}

