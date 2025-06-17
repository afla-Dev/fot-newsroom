package com.bmi.fotnewsapp;


public class User {
    public String username;
    public String email;

    public String password;

    public User() {
        // Default constructor required
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
