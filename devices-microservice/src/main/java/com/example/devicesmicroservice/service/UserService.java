package com.example.devicesmicroservice.service;

import com.example.devicesmicroservice.dto.MonitorDeviceSyncDTO;
import com.example.devicesmicroservice.dto.OperationType;
import com.example.devicesmicroservice.dto.UserDTO;
import com.example.devicesmicroservice.messaging.MonitorSyncService;
import com.example.devicesmicroservice.model.Device;
import com.example.devicesmicroservice.model.User;
import com.example.devicesmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MonitorSyncService monitorSyncService;

    public void createUser(UserDTO userDTO) {
        User user = new User(UUID.fromString(userDTO.getUuid()), userDTO.getUsername(), userDTO.getRole());
        userRepository.save(user);
    }

    public void updateUser(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(userDTO.getUuid()));

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDTO.getUsername());
            userRepository.save(user);
        }
    }

    public void deleteUser(String id) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(id));

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Removing ownership from devices
            List<Device> deviceList = user.getDevices();
            for(Device d : deviceList) {
                d.setUser(null);
            }

            monitorSyncService.sendObject(new MonitorDeviceSyncDTO(OperationType.UNLINK, "", 0, user.getUsername()));

            userRepository.delete(user);

        }
    }

}
