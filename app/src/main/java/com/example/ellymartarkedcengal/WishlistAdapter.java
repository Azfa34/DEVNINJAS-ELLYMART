package com.example.ellymartarkedcengal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private  Context context;
    private List<Wishlist> wishlistList;

    public WishlistAdapter(Context context,List<Wishlist> wishlistList) {
        this.context = context;
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

        Wishlist wishlist = wishlistList.get(holder.getAdapterPosition());


        // Set your UI elements with wishlist item data
        holder.wishlistNameTextView.setText(wishlist.getItemName());
        holder.btnDeleteWishlistItem.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Call the deleteWishlistItem method with the Wishlist item ID
            deleteWishlistItem(wishlistList.get(holder.getAdapterPosition()).getWishProductId());
        }
    });

        // Load image using Picasso
        Picasso.get().load(wishlist.getImageUrl()).into(holder.wishlistImageView);
    }

    private void deleteWishlistItem(String wishProductId) {
        DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference().child("wishlist").child(wishProductId);
        wishlistRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Successfully deleted from Firebase, now update local data
                    int index = findWishlistItemIndex(wishProductId);
                    if (index != -1) {
                        wishlistList.remove(index);
                        notifyDataSetChanged();
                    }
                } else {
                    // Handle failure
                    Toast.makeText(context, "Failed to delete Wishlist item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int findWishlistItemIndex(String wishlistProductId) {
        for (int i = 0; i < wishlistList.size(); i++) {
            if (wishlistList.get(i).getWishProductId().equals(wishlistProductId)) {
                return i;
            }
        }
        return -1;
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
        Button btnDeleteWishlistItem;
        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            wishlistNameTextView = itemView.findViewById(R.id.wishlistNameTextView);
            wishlistPriceTextView = itemView.findViewById(R.id.wishlistPriceTextView);
            wishlistImageView = itemView.findViewById(R.id.wishlistImageView);
            btnDeleteWishlistItem = itemView.findViewById(R.id.btnDeleteWishlistItem);
        }
    }
}
