package com.example.monitoringmicroservice.service;

import com.example.monitoringmicroservice.dto.DeviceDTO;
import com.example.monitoringmicroservice.dto.MonitorDeviceSyncDTO;
import com.example.monitoringmicroservice.model.Device;
import com.example.monitoringmicroservice.repository.DeviceRepository;
import com.example.monitoringmicroservice.utils.ConfigWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ConfigWriter configWriter;

    public void createDevice(MonitorDeviceSyncDTO deviceDTO) {
        Device device = new Device();
        device.setId(UUID.fromString(deviceDTO.getUuid()));
        device.setMaxConsumption(deviceDTO.getMaxConsumption());
        device.setUsername(deviceDTO.getUsername());

        Device savedDevice = deviceRepository.save(device);

        updateConfigFile();

    }

    private void updateConfigFile() {
        List<Device> devices = deviceRepository.findAll();
        ArrayList<UUID> uuids = new ArrayList<>();
        for(Device device1 : devices) {
            uuids.add(device1.getId());
        }
        configWriter.writeUUIDsToFile(uuids);
    }

    public List<UUID> getAllDeviceUUIDs() {
        List<Device> devices = deviceRepository.findAll();
        List<UUID> uuids = new ArrayList<>();
        for(Device device1 : devices) {
            uuids.add(device1.getId());
        }
        return uuids;
    }
    public void updateDevice(MonitorDeviceSyncDTO deviceSyncDTO) {
        Optional<Device> optionalDevice = deviceRepository.findById(UUID.fromString(deviceSyncDTO.getUuid()));
        if(!optionalDevice.isPresent()) {
            // throw err
        }
        else {
            Device device = optionalDevice.get();
            device.setMaxConsumption(deviceSyncDTO.getMaxConsumption());
            deviceRepository.save(device);
        }
    }

    public void deleteDevice(MonitorDeviceSyncDTO deviceSyncDTO) {
        Optional<Device> optionalDevice = deviceRepository.findById(UUID.fromString(deviceSyncDTO.getUuid()));
        if(!optionalDevice.isPresent()) {
            // throw ex
        }
        else {
            deviceRepository.delete(optionalDevice.get());
            updateConfigFile();
        }
    }

    public void linkDevice(MonitorDeviceSyncDTO deviceSyncDTO) {
        Optional<Device> optionalDevice = deviceRepository.findById(UUID.fromString(deviceSyncDTO.getUuid()));
        if(!optionalDevice.isPresent()) {
            // throw ex
        }
        else {
            Device device = optionalDevice.get();
            device.setUsername(deviceSyncDTO.getUsername());
            deviceRepository.save(device);
            System.out.println("Linked device " + device.getId() + " to " + device.getUsername());
        }
    }

    public void unlinkUserDevices(MonitorDeviceSyncDTO deviceSyncDTO) {
        List<Device> devices = deviceRepository.findDeviceByUsername(deviceSyncDTO.getUsername());
        for(Device d : devices) {
            d.setUsername("");
            deviceRepository.save(d);
            System.out.println("Unlinked device " + d.getId() + " from " + deviceSyncDTO.getUsername());
        }
    }





}
