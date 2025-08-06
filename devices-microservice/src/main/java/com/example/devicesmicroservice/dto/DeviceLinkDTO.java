package com.example.devicesmicroservice.dto;

public class DeviceLinkDTO {

    public DeviceLinkDTO(){

    }

    public DeviceLinkDTO(String deviceUUID, String userUUID) {
        this.deviceUUID = deviceUUID;
        this.userUUID = userUUID;
    }

    private String deviceUUID;
    private String userUUID;

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }
}
