package com.example.monitoringmicroservice.service;

import com.example.monitoringmicroservice.dto.ConsumptionEntryDTO;
import com.example.monitoringmicroservice.model.ConsumptionEntry;
import com.example.monitoringmicroservice.model.Device;
import com.example.monitoringmicroservice.repository.ConsumptionEntryRepository;
import com.example.monitoringmicroservice.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsumptionEntryService {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ConsumptionEntryRepository consumptionEntryRepository;
    public void insertEntry(ConsumptionEntryDTO consumptionEntryDTO) {
        Optional<Device> device = deviceRepository.findById(UUID.fromString(consumptionEntryDTO.getDevice_id()));
        if(device.isPresent()) {
            System.out.println("Found device");

            ConsumptionEntry consumptionEntry = new ConsumptionEntry(Timestamp.valueOf(consumptionEntryDTO.getTimestamp()),
                    device.get(), consumptionEntryDTO.getMeasurement_value());
            consumptionEntryRepository.save(consumptionEntry);

        }
        else {
            System.out.println("Could not find device with UUID: " + consumptionEntryDTO.getDevice_id());
        }
    }
}
