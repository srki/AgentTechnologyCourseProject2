package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Example of a Ping agent.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class Ping extends Agent {

    @Override
    protected void handleRequest(ACLMessage message) {
        getLogManager().info("Request to Ping: " + message.getContent());

        AID pongAid = message.getReplyTo();
        ACLMessage msgToPong = new ACLMessage();
        msgToPong.setPerformative(ACLMessage.Performative.REQUEST);
        msgToPong.setSender(aid);
        msgToPong.getReceivers().add(pongAid);
        msgToPong.setContent("Hello Pong!");
        getMessageManager().sendMessage(msgToPong);
    }

    @Override
    protected void handleInform(ACLMessage message) {
        getLogManager().info("Inform to Ping: " + message.getContent());
        getLogManager().info("Ping received INFORM from Pong: " + message.getContent());
    }
}
