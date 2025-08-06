package com.example.monitoringmicroservice.controller;


import com.example.monitoringmicroservice.dto.ConsumptionPerHourQuery;
import com.example.monitoringmicroservice.dto.DeviceStatusInDayRequest;
import com.example.monitoringmicroservice.repository.ConsumptionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
public class MonitoringController {

    @Autowired
    ConsumptionEntryRepository consumptionEntryRepository;

    @CrossOrigin
    @PostMapping("/getDeviceStatInDay")
    public ResponseEntity<?>getDeviceStatInDay(@RequestBody DeviceStatusInDayRequest deviceStatusInDayRequest) {

        System.out.println("Date received: " + deviceStatusInDayRequest.getDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale(Locale.ENGLISH);
        LocalDate date = LocalDate.parse(deviceStatusInDayRequest.getDate(), formatter);
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());
        List<ConsumptionPerHourQuery> list = consumptionEntryRepository.findConsumptionPerHourInDayByDevice(UUID.fromString(deviceStatusInDayRequest.getDeviceID()), timestamp);
        return ResponseEntity.ok(list);
    }


}
