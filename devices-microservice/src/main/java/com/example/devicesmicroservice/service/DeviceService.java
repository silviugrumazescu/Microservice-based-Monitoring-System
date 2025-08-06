package com.example.devicesmicroservice.service;

import com.example.devicesmicroservice.dto.DeviceDTO;
import com.example.devicesmicroservice.dto.MonitorDeviceSyncDTO;
import com.example.devicesmicroservice.dto.OperationType;
import com.example.devicesmicroservice.messaging.MonitorSyncService;
import com.example.devicesmicroservice.model.Device;
import com.example.devicesmicroservice.model.User;
import com.example.devicesmicroservice.repository.DeviceRepository;
import com.example.devicesmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MonitorSyncService monitorSyncService;

    public ArrayList<DeviceDTO> getAllDevices() {
        ArrayList<DeviceDTO> deviceDTOS = new ArrayList<DeviceDTO>();

        List<Device> deviceList = deviceRepository.findAll();

        for(Device d : deviceList) {
            DeviceDTO deviceDTO = new DeviceDTO(
                    d.getId().toString(),
                    d.getDescription(),
                    d.getAddress(),
                    d.getMaxconsumption()
            );

            deviceDTOS.add(deviceDTO);
        }

        return deviceDTOS;
    }

    public ArrayList<DeviceDTO> getAvailableDevices() {
        ArrayList<DeviceDTO> deviceDTOS = new ArrayList<>();
        List<Device> deviceList = deviceRepository.findByUserIsNull()   ;
        System.out.println("AVAILABLE: " + deviceList.size());
        for(Device d : deviceList) {
            DeviceDTO deviceDTO = new DeviceDTO(
                    d.getId().toString(),
                    d.getDescription(),
                    d.getAddress(),
                    d.getMaxconsumption()
            );
            deviceDTOS.add(deviceDTO);
        }
        return deviceDTOS;
    }

    public void createDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMaxconsumption(deviceDTO.getMaxconsumption());

        Device savedDevice = deviceRepository.save(device);
        monitorSyncService.sendObject(new MonitorDeviceSyncDTO(OperationType.CREATE, savedDevice.getId().toString(), savedDevice.getMaxconsumption(), ""));
    }

    public void updateDevice(DeviceDTO deviceDTO) {
        Optional<Device> optionalDevice = deviceRepository.findById(UUID.fromString(deviceDTO.getUuid()));
        if(!optionalDevice.isPresent()) {
            // throw err
        }
        else {
            Device device = optionalDevice.get();
            device.setDescription(deviceDTO.getDescription());
            device.setAddress(deviceDTO.getAddress());
            device.setMaxconsumption(deviceDTO.getMaxconsumption());

            deviceRepository.save(device);
            monitorSyncService.sendObject(new MonitorDeviceSyncDTO(OperationType.UPDATE, deviceDTO.getUuid().toString(), device.getMaxconsumption(), device.getUser().getUsername()));

        }
    }

    public void deleteDevice(UUID uuid) {
        Optional<Device> optionalDevice = deviceRepository.findById(uuid);

        if(!optionalDevice.isPresent()) {
            // throw ex
        }
        else {
            deviceRepository.delete(optionalDevice.get());
            monitorSyncService.sendObject(new MonitorDeviceSyncDTO(OperationType.DELETE, uuid.toString(), 0, ""));
        }
    }

    public void linkDeviceToUser(String userUUID, String deviceUUID) {
        System.out.println("USER UUID " + userUUID + "|" + " deviceUUID "+ deviceUUID);
        Optional<Device> optionalDevice = deviceRepository.findById(UUID.fromString(deviceUUID));
        if(optionalDevice.isPresent()) {
            Optional<User> optionalUser = userRepository.findById(UUID.fromString(userUUID));
            if(optionalUser.isPresent()) {
                Device device = optionalDevice.get();
                device.setUser(optionalUser.get());

                deviceRepository.save(device);
                monitorSyncService.sendObject(new MonitorDeviceSyncDTO(OperationType.LINK, deviceUUID, 0, optionalUser.get().getUsername()));

            }
            else {
                System.out.println("User not found");
            }
        }
        else {
            System.out.println("Device not found");
        }
    }

    public List<DeviceDTO> getDevicesByUsername(String username) {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        ArrayList<DeviceDTO> deviceDTOS = new ArrayList<DeviceDTO>();
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Device> deviceList = deviceRepository.findDeviceByUser(user);

            for(Device d : deviceList) {
                deviceDTOS.add(new DeviceDTO(
                        d.getId().toString(),
                        d.getDescription(),
                        d.getAddress(),
                        d.getMaxconsumption()
                ));
            }

        }
        return deviceDTOS;
    }

}
