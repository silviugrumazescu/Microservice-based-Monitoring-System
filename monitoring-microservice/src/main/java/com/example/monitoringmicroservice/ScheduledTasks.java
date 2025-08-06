package com.example.monitoringmicroservice;

import com.example.monitoringmicroservice.dto.ConsumptionPerHourQuery;
import com.example.monitoringmicroservice.dto.WebsocketNotification;
import com.example.monitoringmicroservice.model.Device;
import com.example.monitoringmicroservice.model.NotificationType;
import com.example.monitoringmicroservice.repository.ConsumptionEntryRepository;
import com.example.monitoringmicroservice.repository.DeviceRepository;
import com.example.monitoringmicroservice.service.NotificationService;
import com.example.monitoringmicroservice.websocket.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduledTasks {

    @Autowired
    private SessionStorage sessionStorage;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ConsumptionEntryRepository consumptionEntryRepository;
    @Autowired
    DeviceRepository deviceRepository;

    @Scheduled(fixedRate = 10000)
    public void notifyConsumptionExceeded() {
        List<ConsumptionPerHourQuery> consumptionPerHourQueryList = consumptionEntryRepository.findConsumptionPerHour();

        for(ConsumptionPerHourQuery c : consumptionPerHourQueryList) {
            UUID uuid = c.getDeviceUUID();
            Optional<Device> deviceOptional = deviceRepository.findById(uuid);
            if(deviceOptional.isPresent()) {
                Device device = deviceOptional.get();
                if(device.getMaxConsumption() < c.getTotalConsumption()) {
                    if(device.getUsername() != null) {
                        UUID sessionID = sessionStorage.getSessionID(device.getUsername());
                        if(sessionID != null) {
                            WebsocketNotification notification = new WebsocketNotification("", "", NotificationType.ALERT_TEXT, "Device " + device.getId() + " exceded the maximum consumpion!");
                            notificationService.sendNotification(sessionID, notification);
                        }
                    }
                }
            }
        }
    }

}
