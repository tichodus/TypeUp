package com.example.stefan.workup.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable{
     String firstname;
    String lastname;
     String username;
     String password;
     UserType type;
     String id;

     public User(){}

    public User(String id, String firstname, String lastname, String username, String password, UserType type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return this.type;
    }

    public void setType(UserType type){
        this.type = type;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }
}