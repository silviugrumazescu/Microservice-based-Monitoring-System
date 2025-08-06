package com.example.devicesmicroservice.messaging;

import com.example.devicesmicroservice.dto.MonitorDeviceSyncDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MonitorSyncService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${spring.rabbitmq.devicequeue}")
    String deviceQueueName;

    public void sendObject(MonitorDeviceSyncDTO obj) {
        try {
            String payload = objectMapper.writeValueAsString(obj);

            rabbitTemplate.convertAndSend(deviceQueueName, payload);

            System.out.println("Message <" + payload + "> succesfully sent!");

        } catch (Exception ex) {
            System.out.println("ERROR: FAILED TO CONVERT OBJ TO STRING!!!");
        }
    }
}
