package com.example.ellymartarkedcengal;

public class Wishlist {
    private String wishProductId;
    private String itemName;
    private String itemDescription;
    private String imageUrl; // Added field for image URL

    // Constructors, getters, and setters
    public Wishlist() {
        // Default constructor required for Firebase
    }

    public Wishlist(String itemName, String itemDescription, String imageUrl) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.imageUrl = imageUrl;
    }

    public String getWishProductId() {
        return wishProductId;
    }

    public void setWishProductId(String wishProductId) {
        this.wishProductId = wishProductId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
