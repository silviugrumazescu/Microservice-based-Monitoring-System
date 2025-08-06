package com.example.monitoringmicroservice.rabbitmq;

import com.example.monitoringmicroservice.dto.ConsumptionEntryDTO;
import com.example.monitoringmicroservice.dto.MonitorDeviceSyncDTO;
import com.example.monitoringmicroservice.model.Device;
import com.example.monitoringmicroservice.service.ConsumptionEntryService;
import com.example.monitoringmicroservice.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ConsumptionUpdateService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DeviceService deviceService;
    @Autowired
    ConsumptionEntryService consumptionEntryService;

    @RabbitListener(queues = "${spring.rabbitmq.consumptionqueue}")
    public void receivedMessage(ConsumptionEntryDTO consumptionEntryDTO) {
        System.out.println("Received : " + consumptionEntryDTO.getDevice_id() + " " + consumptionEntryDTO.getTimestamp());

        consumptionEntryService.insertEntry(consumptionEntryDTO);
    }

    @RabbitListener(queues = "${spring.rabbitmq.devicequeue}")
    public void receivedMessage(MonitorDeviceSyncDTO monitorDeviceSyncDTO) {
        System.out.println("Received from device queue: <"+ monitorDeviceSyncDTO.getOperationType() + " " + monitorDeviceSyncDTO.getUuid() + " " + monitorDeviceSyncDTO.getMaxConsumption());
        switch(monitorDeviceSyncDTO.getOperationType()) {
            case CREATE:
                deviceService.createDevice(monitorDeviceSyncDTO);
                break;
            case UPDATE:
                deviceService.updateDevice(monitorDeviceSyncDTO);
                break;
            case DELETE:
                deviceService.deleteDevice(monitorDeviceSyncDTO);
                break;
            case LINK:
                deviceService.linkDevice(monitorDeviceSyncDTO);
                break;
            case UNLINK:
                deviceService.unlinkUserDevices(monitorDeviceSyncDTO);
                break;
            default:
                System.out.println("Operation not supported!");
        }
    }

}
