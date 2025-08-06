package com.example.monitoringmicroservice.dto;

public class DeviceStatusInDayRequest {

    private String date;
    private String deviceID;

    public DeviceStatusInDayRequest(String date, String deviceID) {
        this.date = date;
        this.deviceID = deviceID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
