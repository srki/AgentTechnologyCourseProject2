package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Agent for testing PingPong example.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class TestPingPong extends Agent {

    @Override
    protected void handleRequest(ACLMessage message) {
        getLogManager().info("Starting PingPong example...");

        // Start Ping
        AgentType atPing = new AgentType(Ping.class);
        AID pingAid = getAgentManager().runAgent(atPing, "Ping");

        // Start Pong
        AgentType atPong = new AgentType(Pong.class);
        AID pongAid = getAgentManager().runAgent(atPong, "Pong");

        ACLMessage msgToPing = new ACLMessage();
        msgToPing.setPerformative(ACLMessage.Performative.REQUEST);
        msgToPing.getReceivers().add(pingAid);
        msgToPing.setContent("Say hello to Pong!");
        msgToPing.setReplyTo(pongAid);
        getMessageManager().sendMessage(msgToPing);
    }
}
