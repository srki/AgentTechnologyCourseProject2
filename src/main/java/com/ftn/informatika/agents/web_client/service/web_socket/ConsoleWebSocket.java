package com.ftn.informatika.agents.web_client.service.web_socket;

import com.ftn.informatika.agents.clustering.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.messages.MessagesLocal;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.service.http.AgentsRequester;
import com.ftn.informatika.agents.web_client.service.ErrorObject;
import com.ftn.informatika.agents.web_client.service.web_socket.beans.SessionsDbLocal;
import com.ftn.informatika.agents.web_client.service.web_socket.model.RunAgentRequest;
import com.ftn.informatika.agents.web_client.service.web_socket.model.WebSocketPacket;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.websocket.*;
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
    @EJB
    private SessionsDbLocal sessionsBean;
    @EJB
    private ConfigurationLocal configurationBean;

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

    @OnOpen
    public void onOpen(Session session) {
        sessionsBean.addSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessionsBean.removeSession(session);
    }

    private void handleGetClasses(String data, Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_CLASSES, agentsBean.getClassesAsList());
    }

    private void handleGetRunning(String data, Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_RUNNING, agentsBean.getRunningAgents());
    }

    private void handleRunAgent(String data, Session session) throws IOException {
        RunAgentRequest request = new Gson().fromJson(data, RunAgentRequest.class);
        try {
            AID aid = agentsBean.runAgent(request.getAgentType(), request.getName());
            createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT, aid);
        } catch (EJBException e) {
            if (e.getCause() instanceof NameAlreadyExistsException) {
                createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT,
                        new ErrorObject("Name already exists!."), false);
            } else {
                e.printStackTrace();
                createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT,
                        new ErrorObject(e.getMessage()), false);
            }
        } catch (NameAlreadyExistsException e) {
            createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT,
                    new ErrorObject("Name already exists!."), false);
        }
    }

    private void handleStopAgent(String data, Session session) throws IOException {
        AID aid = new Gson().fromJson(data, AID.class);
        if (!configurationBean.getAgentCenter().equals(aid.getHost())) {
            new AgentsRequester(aid.getHost().getAddress()).stopAgent(aid);
        } else {
            agentsBean.stopAgent(aid);
        }
    }

    private void handleSendMessage(String data, Session session) throws IOException {
        messagesBean.sendMessage(new Gson().fromJson(data, ACLMessage.class));
        createAndSendPackage(session, WebSocketPacket.Type.SEND_MESSAGE, "");
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
