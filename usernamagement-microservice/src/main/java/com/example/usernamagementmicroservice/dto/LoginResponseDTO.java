package com.example.usernamagementmicroservice.dto;

import com.example.usernamagementmicroservice.model.Role;

public class LoginResponseDTO {

    public LoginResponseDTO(String username, String jwt, String role) {
        this.username = username;
        this.jwt = jwt;
        this.role = role;
    }


    private String username;
    private String jwt;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
