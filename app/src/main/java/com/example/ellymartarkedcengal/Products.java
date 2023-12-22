package com.example.ellymartarkedcengal;

public class Products {

        private String productId;
        private String name;
        private double price;
        private boolean availability;
        private String description;
        private int quantity;
        private String imageUrl;  // New field for image URL

        // Constructors, getters, and setters
        public Products(String productId, double itemPrice, String itemDescription) {
                this.productId = productId;
                this.price = itemPrice;
                this.description = itemDescription;
                this.imageUrl = imageUrl;
        }

        public Products(String name, double price, boolean availability, String description, int quantity, String imageUrl) {
                this.name = name;
                this.price = price;
                this.availability = availability;
                this.description = description;
                this.quantity = quantity;
                this.imageUrl = imageUrl;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public boolean isAvailability() {
                return availability;
        }

        public void setAvailability(boolean availability) {
                this.availability = availability;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        public String getProductId() {
                return productId;
        }

        public void setProductId(String productId) {
                this.productId = productId;
        }

        public String getImageUrl() {
                return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
        }
}



