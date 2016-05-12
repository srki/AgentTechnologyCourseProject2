package com.ftn.informatika.agents.web_client.service.web_socket.beans;

import com.ftn.informatika.agents.web_client.service.web_socket.model.WebSocketPacket;

import javax.ejb.Local;
import javax.websocket.Session;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface SessionsDbLocal {
    void addSession(Session session);
    void removeSession(Session session);
    List<Session> getAll();
    void sendToAll(WebSocketPacket packet);
}
