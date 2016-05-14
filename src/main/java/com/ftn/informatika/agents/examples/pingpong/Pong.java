package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.log.LogBean;

import javax.ejb.EJB;
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

    @EJB
    private LogBean logger;

    @Override
    protected boolean handleRequest(ACLMessage message) {
        logger.info("Request to Pong: " + message);

        ACLMessage reply = new ACLMessage();
        reply.setPerformative(ACLMessage.Performative.INFORM);
        reply.getReceivers().add(message.getSender());
        reply.setContent("Reply");
        msm().sendMessage(reply);

        return true;
    }
}
