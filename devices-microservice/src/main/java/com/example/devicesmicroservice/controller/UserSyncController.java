package com.example.devicesmicroservice.controller;

import com.example.devicesmicroservice.dto.UserDTO;
import com.example.devicesmicroservice.messaging.MonitorSyncService;
import com.example.devicesmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserSyncController {

    @Autowired
    MonitorSyncService monitorSyncService;

    @Autowired
    UserService userService;

    @PostMapping("/sync/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok("SUCCES");
    }

    @PostMapping("/sync/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseEntity.ok("SUCCES");
    }

    @PostMapping("/sync/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody String uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping("/sync/sendMessageToMonitor")
    public ResponseEntity<?> sendMessageToMonitor(@RequestBody String message) {
        System.out.println("GOT HERE");
        //monitorSyncService.sendObject(message);
        return ResponseEntity.ok("Succesfully sent message");
    }

}
