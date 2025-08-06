package com.example.devicesmicroservice.model;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="description")
    private String description;

    @Column(name="address")
    private String address;

    @Column(name="maxconsumption")
    private Integer maxconsumption;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxconsumption() {
        return maxconsumption;
    }

    public void setMaxconsumption(Integer maxconsumption) {
        this.maxconsumption = maxconsumption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
