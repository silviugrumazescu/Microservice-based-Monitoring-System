package com.example.monitoringmicroservice.repository;

import com.example.monitoringmicroservice.dto.ConsumptionPastHourQuery;
import com.example.monitoringmicroservice.dto.ConsumptionPerHourQuery;
import com.example.monitoringmicroservice.model.ConsumptionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ConsumptionEntryRepository extends JpaRepository<ConsumptionEntry, UUID> {

    @Query(
            value="SELECT NEW com.example.monitoringmicroservice.dto.ConsumptionPerHourQuery(ce.device.id, DATE_TRUNC('hour', ce.timestamp), COUNT(ce.entryId), SUM(ce.measurementValue)) from ConsumptionEntry ce group by DATE_TRUNC('hour', ce.timestamp), ce.device.id"
    )
    List<ConsumptionPerHourQuery> findConsumptionPerHour();

    @Query(
            value="SELECT NEW com.example.monitoringmicroservice.dto.ConsumptionPerHourQuery(ce.device.id, DATE_TRUNC('hour', ce.timestamp), COUNT(ce.entryId), SUM(ce.measurementValue)) from ConsumptionEntry ce where ce.device.id = :deviceID AND DATE_TRUNC('day', ce.timestamp) = :specificDay group by DATE_TRUNC('hour', ce.timestamp), ce.device.id"
    )
    List<ConsumptionPerHourQuery> findConsumptionPerHourInDayByDevice(@Param("deviceID") UUID deviceID, @Param("specificDay") Timestamp specificDate);

    @Query(name = "find_consumption_last_hour", nativeQuery = true)
    List<ConsumptionPastHourQuery> findConsumptionLastHour();

}
