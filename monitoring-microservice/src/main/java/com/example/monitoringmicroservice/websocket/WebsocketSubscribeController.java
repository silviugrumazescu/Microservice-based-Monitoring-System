package com.example.monitoringmicroservice.websocket;

import com.example.monitoringmicroservice.service.NotificationService;
import com.example.monitoringmicroservice.utils.JwtUtils;
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


}
