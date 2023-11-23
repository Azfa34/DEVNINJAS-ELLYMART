package com.example.ellymartarkedcengal;

public class User {
    private String userId;
    private String name;
    private String phonenumber;

    // Constructors, getters, and setters

    public User() {
        // Default constructor is needed for Firebase to be able to deserialize the object
    }

    // Parameterized constructor
    public User(String userId, String name, String phonenumber) {
        this.userId = userId;
        this.name = name;
        this.phonenumber = phonenumber;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}

