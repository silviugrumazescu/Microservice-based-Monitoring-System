package com.example.usernamagementmicroservice.dto;

import com.example.usernamagementmicroservice.model.Role;

public class UserSyncDTO {

    public UserSyncDTO(String uuid, String username, Role role) {
        this.uuid = uuid;
        this.username = username;
        this.role = role;
    }

    private String uuid;
    private String username;
    private Role role;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

}
