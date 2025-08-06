package com.example.devicesmicroservice.controller;

import com.example.devicesmicroservice.dto.DeviceDTO;
import com.example.devicesmicroservice.dto.DeviceLinkDTO;
import com.example.devicesmicroservice.security.CustomUserDetails;
import com.example.devicesmicroservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class DevicesController {

    @Autowired
    DeviceService deviceService;


    @GetMapping("/devices/item")
    public ResponseEntity<?> getItem() {
        return ResponseEntity.ok("Got entity from devices");
    }

    @GetMapping("/devices/getAllDevices")
    public ResponseEntity<?> getAllDevices() {
        ArrayList<DeviceDTO> deviceDTOArrayList = deviceService.getAllDevices();

        return ResponseEntity.ok(deviceDTOArrayList);
    }

    @GetMapping("/devices/getAvailableDevices")
    public ResponseEntity<?> getAvailableDevices() {
        List<DeviceDTO> deviceDTOList = deviceService.getAvailableDevices();

        return ResponseEntity.ok(deviceDTOList);
    }

    @PostMapping("/devices/createDevice")
    public ResponseEntity<?> createDevice(@RequestBody DeviceDTO deviceDTO) {
        deviceService.createDevice(deviceDTO);

        return ResponseEntity.ok("Succesfully added device!");
    }

    @PostMapping("/devices/updateDevice")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDTO deviceDTO) {
        deviceService.updateDevice(deviceDTO);
        System.out.println("fasdfasd");
        return ResponseEntity.ok("Succesfully updated device!");
    }

    @PostMapping("/devices/deleteDevice")
    public ResponseEntity<?> updateDevice(@RequestBody String uuid) {
        deviceService.deleteDevice(UUID.fromString(uuid));

        return ResponseEntity.ok("Succesfully deleted device!");
    }

    @PostMapping("/devices/linkDeviceToUser")
    public ResponseEntity<?> linkDeviceToUser(@RequestBody DeviceLinkDTO deviceLinkDTO) {
        deviceService.linkDeviceToUser(deviceLinkDTO.getUserUUID(), deviceLinkDTO.getDeviceUUID());
        return ResponseEntity.ok("Succesfully linked " + deviceLinkDTO.getUserUUID() + " to " + deviceLinkDTO.getDeviceUUID());
    }

    @GetMapping("/user/getUserDevices")
    public ResponseEntity<?> getUserDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            CustomUserDetails customUserDetails = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();

            List<DeviceDTO> deviceDTOS = deviceService.getDevicesByUsername(customUserDetails.getUsername());
            return ResponseEntity.ok(deviceDTOS);
        }

        return ResponseEntity.badRequest().body("You do not have access to this resource");
    }

}
