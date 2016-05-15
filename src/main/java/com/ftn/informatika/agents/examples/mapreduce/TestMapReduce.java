package com.ftn.informatika.agents.examples.mapreduce;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Agent for testing MapReduce example.
 *
 * @author Dragan Vidakovic
 */
@Stateful
@Remote(RemoteAgent.class)
public class TestMapReduce extends Agent {
    @Override
    protected void handleRequest(ACLMessage message) {
        getLogManager().info("Starting MapReduce Example...");

        // Start master
        AgentType mrmAt = new AgentType(MapReduceMaster.class);
        AID masterAid = getAgentManager().runAgent(mrmAt, "MapReduceMaster");

        // Send initial message
        ACLMessage msg = new ACLMessage();
        msg.setPerformative(ACLMessage.Performative.REQUEST);
        msg.getReceivers().add(masterAid);
        msg.setContent(message.getContent());
        getMessageManager().sendMessage(msg);
    }
}
