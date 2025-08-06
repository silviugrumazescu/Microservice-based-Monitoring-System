package com.example.chatmicroservice.dto;

import com.example.chatmicroservice.dto.NotificationType;

public class WebsocketNotification {

    private NotificationType notificationType;
    private String senderID;
    private String destinationID;
    private String content;

    public WebsocketNotification(String senderID, String destinationID, NotificationType notificationType, String content) {
        this.senderID = senderID;
        this.destinationID = destinationID;
        this.notificationType = notificationType;
        this.content = content;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
