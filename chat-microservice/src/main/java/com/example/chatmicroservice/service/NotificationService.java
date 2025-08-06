package com.example.chatmicroservice.service;


import com.example.chatmicroservice.dto.WebsocketNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(UUID id, WebsocketNotification data) {
        System.out.println("Sent notification with data " + data);
        simpMessagingTemplate.convertAndSendToUser(id.toString(), "/topic/notifications", data);
    }

}
