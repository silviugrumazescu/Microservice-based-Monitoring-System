package com.example.monitoringmicroservice.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.dynalink.Operation;

import java.io.Serializable;

public class MonitorDeviceSyncDTO implements Serializable {

    private OperationType operationType;
    private String uuid;
    private Integer maxConsumption;
    private String username;

    public MonitorDeviceSyncDTO(OperationType operationType, String uuid, Integer maxConsumption, String username) {
        this.operationType = operationType;
        this.uuid = uuid;
        this.maxConsumption = maxConsumption;
        this.username = username;
    }

    public MonitorDeviceSyncDTO() {
        super();
    }

    // Object from serialized
    public MonitorDeviceSyncDTO(String serial) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MonitorDeviceSyncDTO monitorDeviceSyncDTO = objectMapper.readValue(serial, MonitorDeviceSyncDTO.class);
            this.operationType = monitorDeviceSyncDTO.getOperationType();
            this.uuid = monitorDeviceSyncDTO.getUuid();
            this.maxConsumption = monitorDeviceSyncDTO.getMaxConsumption();
            this.username = monitorDeviceSyncDTO.getUsername();
        } catch(Exception ex) {
            System.out.println("Failed to parse serial object with exception: ");
            ex.printStackTrace();
        }

    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Integer maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

