package com.example.monitoringmicroservice.controller;

import com.example.monitoringmicroservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @GetMapping("/getDeviceUUIDs")
    public ResponseEntity<?> getDeviceUUIDs() {
        return ResponseEntity.ok(deviceService.getAllDeviceUUIDs());
    }

}
