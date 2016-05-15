package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.environment.AgentsRemote;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;

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
    protected boolean handleRequest(ACLMessage message) {
        getLogManager().info("Starting PingPong example...");

        AgentsRemote agm = ManagerFactory.getAgentManager();

        // starting ping
        AgentType atPing = new AgentType(Ping.class);
        AID pingAid = agm.runAgent(atPing, "Ping");

        // starting pong
        AgentType atPong = new AgentType(Pong.class);
        AID pongAid = agm.runAgent(atPong, "Pong");

        ACLMessage msgToPing = new ACLMessage();
        msgToPing.setPerformative(ACLMessage.Performative.REQUEST);
        msgToPing.getReceivers().add(pingAid);
        msgToPing.setContent("Say hello to Pong!");
        msgToPing.setReplyTo(pongAid);
        getMessageManager().sendMessage(msgToPing);

        return true;
    }
}
