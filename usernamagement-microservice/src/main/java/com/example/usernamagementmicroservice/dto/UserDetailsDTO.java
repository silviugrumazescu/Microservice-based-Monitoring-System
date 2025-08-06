package com.example.usernamagementmicroservice.dto;

import com.example.usernamagementmicroservice.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.UUID;

public class UserDetailsDTO {
    private String id;
    private String username;
    private String role;
    private Boolean isUserValid;

    public UserDetailsDTO(Boolean isUserValid, String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.isUserValid = isUserValid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getUserValid() {
        return isUserValid;
    }

    public void setUserValid(Boolean userValid) {
        isUserValid = userValid;
    }
}
