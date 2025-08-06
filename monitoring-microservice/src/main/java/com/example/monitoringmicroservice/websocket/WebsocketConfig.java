package com.example.monitoringmicroservice.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/monitor-websocket").setAllowedOrigins("*").setHandshakeHandler(new CustomHandshakeHandler()).withSockJS();
        registry.addEndpoint("/monitor-websocket").setAllowedOrigins("*").setHandshakeHandler(new CustomHandshakeHandler());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/notifications", "/topic/chat", "/topic/typing");
        registry.setApplicationDestinationPrefixes("/app");
    }

}
