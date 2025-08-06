package com.example.chatmicroservice.dto;

public class WebsocketChatMessage {

    private String message;
    private String senderID;
    private String destinationID;

    public WebsocketChatMessage(String message, String senderID, String destinationID) {
        this.message = message;
        this.senderID = senderID;
        this.destinationID = destinationID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
