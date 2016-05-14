package com.ftn.informatika.agents.web_client.service.web_socket.beans;

import com.ftn.informatika.agents.web_client.service.web_socket.model.WebSocketPacket;
import com.google.gson.Gson;

import javax.ejb.*;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class SessionsDbBean implements SessionsDbLocal {

    private HashMap<String, Session> sessions = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeSession(Session session) {
        sessions.remove(session.getId());
    }

    @Lock(LockType.READ)
    @Override
    public List<Session> getAll() {
        return new ArrayList<>(sessions.values());
    }

    @Lock(LockType.READ)
    @Override
    public void sendToAll(WebSocketPacket packet) {
        sessions.values().forEach(s -> {
            try {
                s.getBasicRemote().sendText(new Gson().toJson(packet));
            } catch (IOException e) {
                System.err.println(SessionsDbBean.class.getName() + " WebSocket exception: " + e.getMessage());
            }
        });
    }
}
