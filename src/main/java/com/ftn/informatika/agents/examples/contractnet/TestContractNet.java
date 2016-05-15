package com.ftn.informatika.agents.examples.contractnet;

import com.ftn.informatika.agents.environment.agents.manager.AgentManagerRemote;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Agent for testing ContractNet example
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class TestContractNet extends Agent {
    @Override
    protected void handleRequest(ACLMessage message) {
        getLogManager().info("Starting ContractNetProtocol Example...");

        AgentManagerRemote agm = ManagerFactory.getAgentManager();

        // starting master
        AgentType cnmAt = new AgentType(ContractNetMaster.class);
        AID masterAid = agm.runAgent(cnmAt, "ContractNetMaster");

        // sending initial message
        ACLMessage msg = new ACLMessage();
        msg.setPerformative(ACLMessage.Performative.REQUEST);
        msg.getReceivers().add(masterAid);
        msg.setContent("Start!");
        getMessageManager().sendMessage(msg);
    }
}
