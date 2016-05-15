package com.ftn.informatika.agents.examples.contractnet;

import com.ftn.informatika.agents.environment.AgentsRemote;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.util.ArrayList;

/**
 * Represents Contract Net Protocol Initiator.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class ContractNetMaster extends Agent {

    private int NUMBER_OF_SLAVES = 5;
    private int DELIVERED = 0;
    private ArrayList<AID> rejectAids = new ArrayList<AID>();
    private AID acceptAid = null;
    private int bestBid = 1001;
    private int REFUSED = 0;
    private int PROPOSED = 0;

    @Override
    protected boolean handleRequest(ACLMessage message) {
        // reseting values, in case of restart
        resetValues();

        getLogManager().info("Request to ContractNetMaster: " + message.getContent());

        // start agents
        AgentsRemote agm = ManagerFactory.getAgentManager();
        ArrayList<AID> slaveAids = new ArrayList<AID>();

        for (int i = 0; i < NUMBER_OF_SLAVES; i++) {
            String slaveName = "ContractNetSlave" + i;
            AgentType slaveAt = new AgentType(ContractNetSlave.class);
            AID slaveAid = agm.runAgent(slaveAt, slaveName);
            slaveAids.add(slaveAid);
        }

        // send messages
        for (int i = 0; i < NUMBER_OF_SLAVES; i++) {
            ACLMessage cfp = new ACLMessage();
            cfp.setPerformative(ACLMessage.Performative.CFP);
            cfp.setSender(aid);
            cfp.getReceivers().add(slaveAids.get(i));
            cfp.setContent("Master is Calling For Proposals, please send your bids!");
            getMessageManager().sendMessage(cfp);
        }

        return true;
    }

    @Override
    protected boolean handleRefuse(ACLMessage message) {
        REFUSED++;
        String senderName = message.getSender().getName();
        getLogManager().info("Refuse to ContractNetMaster from " + senderName + ": " + message.getContent());

        if (REFUSED + PROPOSED == NUMBER_OF_SLAVES) {
            deadline();
        }

        return true;
    }

    @Override
    protected boolean handlePropose(ACLMessage message) {
        PROPOSED++;
        String senderName = message.getSender().getName();
        getLogManager().info("Propose to ContractNetMaster from " + senderName + ": " + message.getContent());
        int bid = Integer.parseInt(message.getContent());
        if (bid < bestBid) {
            bestBid = bid;
            if (acceptAid != null) {
                rejectAids.add(acceptAid);
            }
            acceptAid = message.getSender();
        }

        if (REFUSED + PROPOSED == NUMBER_OF_SLAVES) {
            deadline();
        }
        return true;
    }


    private void deadline() {
        getLogManager().info("Deadline!");
        if (acceptAid == null) {
            getLogManager().info("Nobody sent Proposal :(");
        } else {
            sendReject();
            sendAccept();
        }
    }

    @Override
    protected boolean handleFailure(ACLMessage message) {
        String senderName = message.getSender().getName();
        getLogManager().info(senderName + " failed to finish: " + message.getContent());
        return true;
    }

    @Override
    protected boolean handleInform(ACLMessage message) {
        String senderName = message.getSender().getName();
        getLogManager().info(senderName + " finished successfully: " + message.getContent());
        return true;
    }

    private void sendReject() {
        if (rejectAids.size() > 0) {
            ACLMessage reject = new ACLMessage();
            reject.setPerformative(ACLMessage.Performative.REJECT_PROPOSAL);
            reject.setSender(aid);
            for (AID rejectAid : rejectAids) {
                reject.getReceivers().add(rejectAid);
            }
            reject.setContent("Master rejects Proposal!");
            getMessageManager().sendMessage(reject);
        }
    }

    private void sendAccept() {
        ACLMessage accept = new ACLMessage();
        accept.setPerformative(ACLMessage.Performative.ACCEPT_PROPOSAL);
        accept.setSender(aid);
        accept.getReceivers().add(acceptAid);
        accept.setContent("Master accepts Proposal!");
        getMessageManager().sendMessage(accept);
    }

    private void resetValues() {
        DELIVERED = 0;
        rejectAids.clear();
        acceptAid = null;
        bestBid = 1001;
        REFUSED = 0;
        PROPOSED = 0;
    }

}
