package com.example.chatmicroservice.websocket;

import com.example.chatmicroservice.dto.WebsocketChatMessage;
import com.example.chatmicroservice.dto.WebsocketNotification;
import com.example.chatmicroservice.dto.NotificationType;
import com.example.chatmicroservice.service.ChatService;
import com.example.chatmicroservice.service.NotificationService;
import com.example.chatmicroservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
public class WebsocketSubscribeController {

    @Autowired
    SessionStorage sessionStorage;
    @Autowired
    ChatService chatService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    JwtUtils jwtUtils;

    @MessageMapping("/subscribe")
    public void subscribe(String jwt, Principal principal) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        System.out.println("Mapped" + jwt +  " to " + principal.getName());
        sessionStorage.addPair(UUID.fromString(principal.getName()), username);
    }

    @MessageMapping("/unsubscribe")
    public void unsubscribe(Principal principal) {
        sessionStorage.deletePair(UUID.fromString(principal.getName()));
    }

    @MessageMapping("/sendmessage")
    public void sendMessage(WebsocketChatMessage websocketChatMessage, Principal principal) {
        System.out.println("RECEIVED: " + websocketChatMessage.getMessage() + " to " + websocketChatMessage.getDestinationID());
        // get username from jwt
        String username = jwtUtils.getUserNameFromJwtToken(websocketChatMessage.getSenderID());
        websocketChatMessage.setSenderID(username);
        // get destination session
        UUID destinationSessionID = sessionStorage.getSessionID(websocketChatMessage.getDestinationID());

        if(destinationSessionID != null) {
            chatService.sendChat(destinationSessionID, websocketChatMessage);
        }
    }

    @MessageMapping("/notifyAnotherUser")
    public void notifyAnotherUser(WebsocketChatMessage websocketChatMessage, Principal principal) {
        String username = jwtUtils.getUserNameFromJwtToken(websocketChatMessage.getSenderID());
        websocketChatMessage.setSenderID(username);
        UUID destinationSessionID = sessionStorage.getSessionID(websocketChatMessage.getDestinationID());

        if(destinationSessionID != null) {
            WebsocketNotification notification =  new WebsocketNotification(
                    username,
                    websocketChatMessage.getDestinationID(),
                    NotificationType.TYPING,
                    ""
            );
            if(websocketChatMessage.getMessage().equals("NOT_TYPING")) {
                notification.setNotificationType(NotificationType.NOT_TYPING);
            } else if (websocketChatMessage.getMessage().equals("SEEN")) {
                notification.setNotificationType(NotificationType.SEEN);
            }

            notificationService.sendNotification(destinationSessionID, notification);
        }
    }

}
