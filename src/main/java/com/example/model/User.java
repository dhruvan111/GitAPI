package com.example.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LoginInfo")
public class User {
    private String id;
    private String userName;
    private String password;

    public User() {
        // for serialization...
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
