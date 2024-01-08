package com.example.ellymartarkedcengal;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Products {
        private String productId;
        private String name;
        private double price;
        private String description;
        private String imageUrl; // Added field for image URL

        // Constructors, getters, and setters
        public Products() {
                // Default constructor required for Firebase
        }

        public Products(String name, double price, String description, String imageUrl) {
                this.name = name;
                this.price = price;
                this.description = description;
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

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getImageUrl() {
                return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
        }

        public String getProductId() {
                return productId;
        }

        public void setProductId(String productId) {
                this.productId = productId;
        }
}


