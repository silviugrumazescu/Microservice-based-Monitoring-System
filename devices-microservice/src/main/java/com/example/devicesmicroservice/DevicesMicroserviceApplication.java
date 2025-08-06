package com.example.devicesmicroservice;

import com.example.devicesmicroservice.dto.DeviceDTO;
import com.example.devicesmicroservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DevicesMicroserviceApplication {

    @Autowired
    DeviceService deviceService;

    public static void main(String[] args) {
        SpringApplication.run(DevicesMicroserviceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertAdminInDatabase() {
//        deviceService.createDevice(new DeviceDTO("", "Device 1", "Address 1", 20));
//        deviceService.createDevice(new DeviceDTO("", "Device 2", "Address 2", 25));
//        deviceService.createDevice(new DeviceDTO("", "Device 3", "Address 3", 26));
//        deviceService.createDevice(new DeviceDTO("", "Device 4", "Address 4", 40));
//        deviceService.createDevice(new DeviceDTO("", "Device 5", "Address 5", 100));
//        deviceService.createDevice(new DeviceDTO("", "Device 6", "Address 6", 15));
    }

}
