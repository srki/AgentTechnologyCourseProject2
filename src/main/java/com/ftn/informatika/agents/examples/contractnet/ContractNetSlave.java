package com.ftn.informatika.agents.examples.contractnet;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.Convert;
import java.util.Random;

/**
 * Represents Contract Net Protocol Participant.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class ContractNetSlave extends Agent {

    @Override
    protected boolean handleCFP(ACLMessage message) {
        String receiverName = aid.getName();
        getLogManager().info("Call For Proposal to " + receiverName + ": " + message.getContent());

        // checking if want to answer
        Random random = new Random();
        int percentage = random.nextInt(100);

        ACLMessage reply = new ACLMessage();
        reply.setSender(aid);
        reply.getReceivers().add(message.getSender());

        if (percentage < 50) {
            // reject
            reply.setPerformative(ACLMessage.Performative.REFUSE);
            reply.setContent("I don't want to participate!");

        } else {
            // accept
            int bid = random.nextInt(1000);
            reply.setPerformative(ACLMessage.Performative.PROPOSE);
            reply.setContent(Integer.toString(bid));
        }

        getMessageManager().sendMessage(reply);

        return true;
    }

    @Override
    protected boolean handleAcceptProposal(ACLMessage message) {
        String receiverName = aid.getName();
        getLogManager().info("Accept Proposal to " + receiverName + ": " + message.getContent());

        Random random = new Random();
        int percentage = random.nextInt(100);

        ACLMessage reply = new ACLMessage();
        reply.setSender(aid);
        reply.getReceivers().add(message.getSender());

        if (percentage < 10) {
            // failure
            reply.setPerformative(ACLMessage.Performative.FAILURE);
            reply.setContent("Failed!");
        } else {
            // inform
            reply.setPerformative(ACLMessage.Performative.INFORM);
            reply.setContent("Completed!");
        }

        getMessageManager().sendMessage(reply);

        return true;
    }

    @Override
    protected boolean handleRejectProposal(ACLMessage message) {
        String receiverName = aid.getName();
        getLogManager().info("Reject Proposal to " + receiverName + ": " + message.getContent());
        return true;
    }
}
