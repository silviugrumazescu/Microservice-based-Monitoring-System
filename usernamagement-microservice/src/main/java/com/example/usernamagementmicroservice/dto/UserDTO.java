package com.example.usernamagementmicroservice.dto;

import java.util.UUID;

public class UserDTO {
    public UserDTO(String uuid, String name, String username, String password, String email) {
        this.uuid = uuid;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    private String uuid;
    private String name;
    private String username;
    private String password;
    private String email;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
