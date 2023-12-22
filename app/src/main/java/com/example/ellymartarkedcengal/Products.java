package com.example.ellymartarkedcengal;

public class Products {

        private String productId;
        private String name;
        private double price;
        private boolean availability;
        private String description;
        private int quantity;

        // Constructors, getters, and setters
        public Products() {
                // Default constructor needed for Firebase
        }

        public Products(String name, double price, String description) {
                this.name = name;
                this.price = price;
                this.description = description;
                this.availability = true; // Assuming the default availability is true
                this.quantity = 0; // Assuming the default quantity is 0
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
}


