package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Example of a Pong agent.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class Pong extends Agent{

    @Override
    protected boolean handleRequest(ACLMessage message) {
        getLogManager().info("Request to Pong: " + message.getContent());

        ACLMessage reply = new ACLMessage();
        reply.setPerformative(ACLMessage.Performative.INFORM);
        reply.getReceivers().add(message.getSender());
        reply.setContent("Hello Ping!");
        getMessageManager().sendMessage(reply);

        return true;
    }
}
