package com.ftn.informatika.agents.web_client.service.web_socket;

import com.ftn.informatika.agents.environment.agents.AgentClassesLocal;
import com.ftn.informatika.agents.environment.agents.RunningAgentsLocal;
import com.ftn.informatika.agents.environment.agents.manager.AgentManagerLocal;
import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.messages.MessagesLocal;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
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
    private AgentClassesLocal agentClassesBean;
    @EJB
    private RunningAgentsLocal runningAgentsBean;
    @EJB
    private MessagesLocal messagesBean;
    @EJB
    private SessionsDbLocal sessionsBean;
    @EJB
    private AgentManagerLocal agentManagerBean;

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
                    handleGetClasses(session);
                    break;
                case GET_RUNNING:
                    handleGetRunning(session);
                    break;
                case RUN_AGENT:
                    handleRunAgent(websocketPacket.getData(), session);
                    break;
                case STOP_AGENT:
                    handleStopAgent(websocketPacket.getData());
                    break;
                case SEND_MESSAGE:
                    handleSendMessage(websocketPacket.getData(), session);
                    break;
                case GET_PERFORMATIVES:
                    handleGetPerformatives(session);
                    break;
                default:
                    System.err.println("Unknown WebSocket type: " + websocketPacket.getType());
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

    private void handleGetClasses(Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_CLASSES, agentClassesBean.getAllClassesAsList());
    }

    private void handleGetRunning(Session session) throws IOException {
        createAndSendPackage(session, WebSocketPacket.Type.GET_RUNNING, runningAgentsBean.getAllRunningAgents());
    }

    private void handleRunAgent(String data, Session session) throws IOException {
        String errorMessage = null;
        RunAgentRequest request = new Gson().fromJson(data, RunAgentRequest.class);

        try {
            AID aid = agentManagerBean.runAgent(request.getAgentType(), request.getName());
            createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT, aid);
        } catch (EJBException e) {
            if (e.getCause() instanceof NameAlreadyExistsException) {
                errorMessage = "Name already exists";
            } else {
                e.printStackTrace();
                errorMessage = e.getMessage();
            }
        } catch (NameAlreadyExistsException e) {
            errorMessage = "Name already exists";
        }

        createAndSendPackage(session, WebSocketPacket.Type.RUN_AGENT, new ErrorObject(errorMessage), false);
    }

    private void handleStopAgent(String data) throws IOException {
        AID aid = new Gson().fromJson(data, AID.class);
        agentManagerBean.stopAgent(aid);
    }

    private void handleSendMessage(String data, Session session) throws IOException {
        messagesBean.sendMessage(new Gson().fromJson(data, ACLMessage.class));
        createAndSendPackage(session, WebSocketPacket.Type.SEND_MESSAGE, "");
    }

    private void handleGetPerformatives(Session session) throws IOException {
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
