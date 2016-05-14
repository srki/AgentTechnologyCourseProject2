package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.environment.AgentsRemote;
import com.ftn.informatika.agents.environment.MessagesRemote;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;

import java.rmi.RemoteException;

/**
 * Class for testing Ping - Pong agents.
 *
 * @author Dragan Vidakovic
 */

public class PingPongTest {

    private void runPingPong() {
        AgentsRemote agm = ManagerFactory.getAgentManager();

        // starting ping
        AgentType atPing = new AgentType(Ping.class);
        AID pingAid = agm.runAgent(atPing, "Ping");

        // starting pong
        AgentType atPong = new AgentType(Pong.class);
        agm.runAgent(atPong, "Pong");

        MessagesRemote msm = ManagerFactory.getMessagesManager();
        ACLMessage message = new ACLMessage();
        message.setPerformative(ACLMessage.Performative.REQUEST);
        message.getReceivers().add(pingAid);
        message.setContent("Pong");
        msm.sendMessage(message);
    }

    public static void main(String[] args)
    {
        try {
            new PingPongTest().runPingPong();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
