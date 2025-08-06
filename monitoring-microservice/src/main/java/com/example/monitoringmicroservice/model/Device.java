package com.example.monitoringmicroservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class Device {

    @Id
    private UUID id;

    @Column(name="maxconsumption")
    private Integer maxConsumption;

    @Column(name="owner")
    private String username;

    @OneToMany(mappedBy="device")
    List<ConsumptionEntry> consumptionEntries;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Integer maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public List<ConsumptionEntry> getConsumptionEntries() {
        return consumptionEntries;
    }

    public void setConsumptionEntries(List<ConsumptionEntry> consumptionEntries) {
        this.consumptionEntries = consumptionEntries;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
