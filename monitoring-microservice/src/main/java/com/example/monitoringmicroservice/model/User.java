package com.example.monitoringmicroservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.UUID;

public class User {

    private String id;

    private String username;

    private Role role;

    private Boolean isUserValid;

    public User(String id, String username, Role role, Boolean isUserValid) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.isUserValid = isUserValid;
    }

    public User(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getUserValid() {
        return isUserValid;
    }

    public void setUserValid(Boolean userValid) {
        isUserValid = userValid;
    }
}
