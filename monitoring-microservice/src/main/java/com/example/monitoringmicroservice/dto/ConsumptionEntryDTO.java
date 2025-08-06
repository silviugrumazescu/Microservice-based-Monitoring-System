package com.example.monitoringmicroservice.dto;

import java.sql.Timestamp;

public class ConsumptionEntryDTO {

    private String timestamp;
    private String device_id;
    private Float measurement_value;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Float getMeasurement_value() {
        return measurement_value;
    }

    public void setMeasurement_value(Float measurement_value) {
        this.measurement_value = measurement_value;
    }
}
