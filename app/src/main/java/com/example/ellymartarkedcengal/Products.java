package com.example.ellymartarkedcengal;

public class Products {

        private String name;
        private double price;
        private boolean availability;
        private String description;
        private int quantity;

        // Constructors, getters, and setters
        public Products(String name, double price, boolean availability, String description, int quantity) {
                this.name = name;
                this.price = price;
                this.availability = availability;
                this.description = description;
                this.quantity = quantity;
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
}

