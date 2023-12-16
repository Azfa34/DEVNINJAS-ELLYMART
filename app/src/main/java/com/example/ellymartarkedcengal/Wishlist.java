package com.example.ellymartarkedcengal;

public class Wishlist {

    private String imageUrl;
    private String wishDescription;
    // Add other fields as needed

    // Required empty constructor for Firebase
    public Wishlist() {
    }

    public Wishlist(String imageUrl, String wishDescription) {
        this.imageUrl = imageUrl;
        this.wishDescription = wishDescription;
        // Initialize other fields as needed
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWishDescription() {
        return wishDescription;
    }

    public void setWishDescription(String wishDescription) {
        this.wishDescription = wishDescription;
    }

    // Add getters and setters for other fields as needed
}

