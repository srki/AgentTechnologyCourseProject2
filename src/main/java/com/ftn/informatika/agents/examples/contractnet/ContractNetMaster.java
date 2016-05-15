package com.ftn.informatika.agents.examples.contractnet;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

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
    private ArrayList<AID> rejectAids = new ArrayList<>();
    private AID acceptAid = null;
    private int bestBid = 1001;
    private int REFUSED = 0;
    private int PROPOSED = 0;

    @Override
    protected void handleRequest(ACLMessage message) {
        // resetting values, in case of restart
        resetValues();

        getLogManager().info("Request to ContractNetMaster: " + message.getContent());

        // start agents
        ArrayList<AID> slaveAids = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_SLAVES; i++) {
            String slaveName = "ContractNetSlave" + i;
            AgentType slaveAt = new AgentType(ContractNetSlave.class);
            AID slaveAid = getAgentManager().runAgent(slaveAt, slaveName);
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
    }

    @Override
    protected void handleRefuse(ACLMessage message) {
        REFUSED++;
        String senderName = message.getSender().getName();
        getLogManager().info("Refuse to ContractNetMaster from " + senderName + ": " + message.getContent());

        if (REFUSED + PROPOSED == NUMBER_OF_SLAVES) {
            deadline();
        }
    }

    @Override
    protected void handlePropose(ACLMessage message) {
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
        } else {
            rejectAids.add(message.getSender());
        }

        if (REFUSED + PROPOSED == NUMBER_OF_SLAVES) {
            deadline();
        }
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
    protected void handleFailure(ACLMessage message) {
        String senderName = message.getSender().getName();
        getLogManager().info(senderName + " failed to finish: " + message.getContent());
    }

    @Override
    protected void handleInform(ACLMessage message) {
        String senderName = message.getSender().getName();
        getLogManager().info(senderName + " finished successfully: " + message.getContent());
    }

    private void sendReject() {
        if (rejectAids.size() > 0) {
            ACLMessage reject = new ACLMessage();
            reject.setPerformative(ACLMessage.Performative.REJECT_PROPOSAL);
            reject.setSender(aid);
            reject.setReceivers(rejectAids);
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
        rejectAids.clear();
        acceptAid = null;
        bestBid = 1001;
        REFUSED = 0;
        PROPOSED = 0;
    }

}
