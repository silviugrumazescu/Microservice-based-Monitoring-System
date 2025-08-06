package com.example.devicesmicroservice.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private Role role;

    @OneToMany (mappedBy="user")
    private List<Device> devices;

    public User() {

    }

    public User(UUID id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
