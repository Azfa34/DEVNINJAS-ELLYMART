package com.example.ellymartarkedcengal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private List<Wishlist> wishlistList = new ArrayList<>();

    public WishlistAdapter(List<Wishlist> wishlistList) {
        this.wishlistList = wishlistList;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Wishlist wishlist = wishlistList.get(position);

        // Set your UI elements with wishlist item data
        holder.wishlistNameTextView.setText(wishlist.getItemName());


        // Load image using Picasso
        Picasso.get().load(wishlist.getImageUrl()).into(holder.wishlistImageView);
    }

    @Override
    public int getItemCount() {
        return wishlistList.size();
    }

    public void setItems(List<Wishlist> wishlistList) {
        wishlistList.clear();
        wishlistList.addAll(wishlistList);
        notifyDataSetChanged();
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder {
        TextView wishlistNameTextView;
        TextView wishlistPriceTextView;
        ImageView wishlistImageView;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            wishlistNameTextView = itemView.findViewById(R.id.wishlistNameTextView);
            wishlistPriceTextView = itemView.findViewById(R.id.wishlistPriceTextView);
            wishlistImageView = itemView.findViewById(R.id.wishlistImageView);
        }
    }
}
