package com.example.monitoringmicroservice.dto;

import jakarta.persistence.Entity;

import java.sql.Timestamp;
import java.util.UUID;
public class ConsumptionPastHourQuery {

    private UUID deviceUUID;
    private Double totalConsumption;
    private Long entriesCount;

    public ConsumptionPastHourQuery(UUID deviceUUID, Long entriesCount, Double totalConsumption) {
        this.deviceUUID = deviceUUID;
        this.totalConsumption = totalConsumption;
        this.entriesCount = entriesCount;
    }

    public UUID getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(UUID deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public Long getEntriesCount() {
        return entriesCount;
    }

    public void setEntriesCount(Long entriesCount) {
        this.entriesCount = entriesCount;
    }
}
