package com.example.monitoringmicroservice.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class ConsumptionPerHourQuery {

    private UUID deviceUUID;
    private Timestamp timestamp;
    private Double totalConsumption;
    private Long entriesCount;

    public ConsumptionPerHourQuery(UUID deviceUUID, Timestamp timestamp, Long entriesCount, Double totalConsumption) {
        this.deviceUUID = deviceUUID;
        this.timestamp = timestamp;
        this.totalConsumption = totalConsumption;
        this.entriesCount = entriesCount;
    }

    public UUID getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(UUID deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
