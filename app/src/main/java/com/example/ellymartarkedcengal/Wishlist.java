package com.example.ellymartarkedcengal;

public class Wishlist {
    private String wishProductId; // Added field

    private String itemName;
    private String itemDescription;

    public Wishlist() {
        // Default constructor required for Firebase
    }

    public Wishlist(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    // Add the new field's getter and setter
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
}

