package com.example.chatmicroservice.service;

import com.example.chatmicroservice.dto.WebsocketChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatService {


    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendChat(UUID id, WebsocketChatMessage message) {
        System.out.println("Sent chat message with id " + id + " data: " + message.getMessage());
        simpMessagingTemplate.convertAndSendToUser(id.toString(), "/topic/chat", message);
    }


}
