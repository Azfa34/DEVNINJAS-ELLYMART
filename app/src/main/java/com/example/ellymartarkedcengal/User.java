package com.example.ellymartarkedcengal;

public class User {
    private String userId;
    private String name;
    private String phonenumber;
    private String profileImageUrl; // New field for profile image URL

    // Constructors, getters, and setters

    public User(String userId, String name, String email, String phoneNumber, String s) {
        // Default constructor is needed for Firebase to be able to deserialize the object
    }

    // Parameterized constructor
    public User(String userId, String name, String phonenumber, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.phonenumber = phonenumber;
        this.profileImageUrl = profileImageUrl;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
