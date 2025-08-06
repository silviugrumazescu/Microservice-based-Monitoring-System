package com.example.monitoringmicroservice.model;

import com.example.monitoringmicroservice.dto.ConsumptionPastHourQuery;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;



@Entity
@NamedNativeQuery(
        name= "find_consumption_last_hour",
        query = "SELECT ce.device_id as deviceid, COUNT(ce.entry_id) as cnt, SUM(ce.measurement_value) as suma from consumption_entry ce where ce.timestamp >= CURRENT_TIMESTAMP - interval '1' hour group by ce.device_id",
        resultSetMapping = "consumptionPerHourDTO"
)
@SqlResultSetMapping(
        name = "consumptionPerHourDTO",
        classes = @ConstructorResult(
                targetClass = ConsumptionPastHourQuery.class,
                columns = {
                        @ColumnResult(name = "deviceid", type = UUID.class),
                        @ColumnResult(name = "cnt", type = Long.class),
                        @ColumnResult(name = "suma", type = Double.class)
                }
        )
)
public class ConsumptionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID entryId;

    @Column(name="timestamp")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name="device_id")
    private Device device;

    @Column(name="measurement_value")
    private Float measurementValue;

    public ConsumptionEntry(Timestamp timestamp, Device device, Float measurementValue) {
        this.timestamp = timestamp;
        this.device = device;
        this.measurementValue = measurementValue;
    }
    public ConsumptionEntry(){
        
    }

    public UUID getEntryId() {
        return entryId;
    }

    public void setEntryId(UUID entryId) {
        this.entryId = entryId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Float getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Float measurementValue) {
        this.measurementValue = measurementValue;
    }


}
