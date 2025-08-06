package com.example.usernamagementmicroservice.controller;


import com.example.usernamagementmicroservice.dto.RegisterDTO;
import com.example.usernamagementmicroservice.dto.UserDTO;
import com.example.usernamagementmicroservice.service.AuthService;
import com.example.usernamagementmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        ArrayList<UserDTO> userDTOArrayList = userService.readAllUsers();
        return ResponseEntity.ok(userDTOArrayList);
    }

    @PostMapping("/admin/getUser")
    public ResponseEntity<?> getUser(@RequestBody String uuid) {
        UUID uuid1 = UUID.fromString(uuid);
        UserDTO userDTO = userService.readUser(uuid1);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/admin/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody String uuid) {
        UUID uuid1 = UUID.fromString(uuid);
        userService.deleteUser(uuid1);
        return ResponseEntity.ok("Succesfully deleted " + uuid);
    }

    @PostMapping("/admin/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseEntity.ok("Succesfully updated " + userDTO.getUuid());
    }

    @PostMapping("/admin/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        RegisterDTO registerDTO = new RegisterDTO(userDTO.getName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
        authService.registerUser(registerDTO);
        return ResponseEntity.ok("Succesfully created user");
    }
}