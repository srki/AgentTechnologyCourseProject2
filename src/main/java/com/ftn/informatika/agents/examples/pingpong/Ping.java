package com.ftn.informatika.agents.examples.pingpong;

import com.ftn.informatika.agents.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.log.LogLocal;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Example of a Ping agent.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class Ping extends Agent{

    @EJB
    private LogLocal logger;

    @EJB
    private ConfigurationLocal config;

    @Override
    protected boolean handleRequest(ACLMessage message) {
        logger.info("Request to Ping: " + message);

        AgentType atPong = new AgentType(Pong.class);
        AID pongAid = new AID(message.getContent(), config.getAgentCenter(), atPong);
        ACLMessage msgToPong = new ACLMessage();
        msgToPong.setPerformative(ACLMessage.Performative.REQUEST);
        msgToPong.setSender(myAid);
        msgToPong.getReceivers().add(pongAid);
        msm().sendMessage(msgToPong);

        return true;
    }

    @Override
    protected boolean handleInform(ACLMessage message) {
        logger.info("Inform to Ping: " + message);

        logger.info("Ping received INFORM from Pong: " + message);
        logger.info("PingPongTest finished.");

        return true;
    }
}
