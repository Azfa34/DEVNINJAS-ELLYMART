package com.example.ellymartarkedcengal;

public class User {
    private String userId;
    private String name;
    private String number;

    // Constructors, getters, and setters

    public User() {
        // Default constructor is needed for Firebase to be able to deserialize the object
    }

    // Parameterized constructor
    public User(String userId, String name, String number) {
        this.userId = userId;
        this.name = name;
        this.number = number;
    }

    // Getters and setters go here


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

