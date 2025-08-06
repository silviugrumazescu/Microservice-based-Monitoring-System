package com.example.devicesmicroservice.dto;

public class DeviceDTO {

    private String uuid;
    private String description;
    private String address;
    private Integer maxconsumption;

    public DeviceDTO(String uuid, String description, String address, Integer maxconsumption) {
        this.uuid = uuid;
        this.description = description;
        this.address = address;
        this.maxconsumption = maxconsumption;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
