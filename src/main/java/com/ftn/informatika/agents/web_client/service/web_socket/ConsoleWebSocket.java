package com.ftn.informatika.agents.web_client.service.web_socket;

import com.ftn.informatika.agents.model.ACLMessage;
import com.ftn.informatika.agents.model.AID;
import com.ftn.informatika.agents.web_client.beans.AgentsLocal;
import com.ftn.informatika.agents.web_client.beans.MessagesLocal;
import com.ftn.informatika.agents.web_client.service.web_socket.model.RunAgentRequest;
import com.ftn.informatika.agents.web_client.service.web_socket.model.WebSocketPacket;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author - Srđan Milaković
 */
@ServerEndpoint("/data")
@Singleton
public class ConsoleWebSocket {

    @EJB
    private AgentsLocal agentsBean;
    @EJB
    private MessagesLocal messagesBean;

    @OnMessage
    public void onMessage(String message, Session session) {
        if (!session.isOpen()) {
            return;
        }

        System.out.println("WebSocketEndpoint receiver message:" + message);
        WebSocketPacket websocketPacket = new Gson().fromJson(message, WebSocketPacket.class);
        try {
            switch (websocketPacket.getType()) {
                case GET_CLASSES:
                    handleGetClasses(websocketPacket.getData(), session);
                    break;
                case GET_RUNNING:
                    handleGetRunning(websocketPacket.getData(), session);
                    break;
                case RUN_AGENT:
                    handleRunAgent(websocketPacket.getData(), session);
                    break;
                case STOP_AGENT:
                    handleStopAgent(websocketPacket.getData(), session);
                    break;
                case SEND_MESSAGE:
                    handleSendMessage(websocketPacket.getData(), session);
                    break;
                case GET_PERFORMATIVES:
                    handleGetPerformatives(websocketPacket.getData(), session);
                    break;
                default:
                    System.err.println("Unknown performative: " + websocketPacket.getType());
            }

        } catch (IOException e) {
            System.err.println("WebSocket Exception: " + e.getMessage());
        }

    }

    @OnError
    public void onError(Throwable error) {
        System.err.println("onError: " + error.getMessage());
    }

    private void handleGetClasses(String data, Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_CLASSES, agentsBean.getClasses());
    }

    private void handleGetRunning(String data, Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_RUNNING, agentsBean.getRunningAgents());
    }

    private void handleRunAgent(String data, Session session) throws IOException {
        RunAgentRequest request = new Gson().fromJson(data, RunAgentRequest.class);
        AID aid = agentsBean.runAgent(request.getAgentType(), request.getName());
        createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT, aid);
    }

    private void handleStopAgent(String data, Session session) throws IOException {
        AID aid = agentsBean.stopAgent(new Gson().fromJson(data, AID.class));
        createAndSendPackage(session, WebSocketPacket.Type.STOP_AGENT, aid);
    }

    private void handleSendMessage(String data, Session session) {
        messagesBean.sendMessage(new Gson().fromJson(data, ACLMessage.class));
    }

    private void handleGetPerformatives(String data, Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_PERFORMATIVES, messagesBean.getPerformatives());
    }

    private void createAndSendPackage(Session session, WebSocketPacket.Type type, Object object) throws IOException {
        createAndSendPackage(session, type, object, true);
    }

    private void createAndSendPackage(Session session, WebSocketPacket.Type type, Object object, boolean success)
            throws IOException {
        WebSocketPacket packet = new WebSocketPacket(type, object, success);
        session.getBasicRemote().sendText(new Gson().toJson(packet));
    }


}
