package com.example.ellymartarkedcengal;

public class Wishlist {
    private String itemName;
    private String itemDescription;

    // Required empty constructor for Firebase
    public Wishlist() {
    }

    public Wishlist(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
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
