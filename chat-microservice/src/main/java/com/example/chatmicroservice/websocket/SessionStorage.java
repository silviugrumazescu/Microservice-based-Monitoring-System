package com.example.chatmicroservice.websocket;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class SessionStorage {

    public HashMap<String, UUID> usernameSessionPairList;

    public SessionStorage() {
        usernameSessionPairList = new HashMap<>();
    }

    public void addPair(UUID sessionID, String username) {
        usernameSessionPairList.put(username, sessionID);
    }

    public void deletePair(UUID sessionID) {
        usernameSessionPairList.entrySet().removeIf(stringUUIDEntry -> stringUUIDEntry.getValue().equals(sessionID));
    }

    public UUID getSessionID(String username) {

        UUID sessionID = usernameSessionPairList.get(username);

        if(sessionID == null) {
            return null;
        }
        return sessionID;
    }

}
