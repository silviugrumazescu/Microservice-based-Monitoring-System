package com.example.devicesmicroservice.repository;

import com.example.devicesmicroservice.model.Device;
import com.example.devicesmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    List<Device> findByUserIsNull();

    List<Device> findDeviceByUser(User user);

}
