package com.ftn.informatika.agents.environment.model;

import com.ftn.informatika.agents.environment.agents.manager.AgentManager;
import com.ftn.informatika.agents.environment.messages.MessagesManager;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;
import com.ftn.informatika.agents.environment.util.log.LogManager;

import javax.ejb.Lock;
import javax.ejb.LockType;

/**
 * Base class for all agents.
 *
 * @author - Srđan Milaković
 * @author Dragan Vidakovic
 */

@Lock(LockType.READ)
public abstract class Agent implements RemoteAgent {

    protected AID aid;

    private LogManager logManager;
    private AgentManager agentManager;
    private MessagesManager messageManager;

    @Override
    public void init(AID aid) {
        this.aid = aid;
    }

    @Override
    public void handleMessage(ACLMessage message) {
        switch (message.getPerformative()) {
            case ACCEPT_PROPOSAL:
                handleAcceptProposal(message);
                break;
            case AGREE:
                handleAgree(message);
                break;
            case CANCEL:
                handleCancel(message);
                break;
            case CFP:
                handleCFP(message);
                break;
            case CONFIRM:
                handleConfirm(message);
                break;
            case DISCONFIRM:
                handleDisconfirm(message);
                break;
            case FAILURE:
                handleFailure(message);
                break;
            case INFORM:
                handleInform(message);
                break;
            case INFORM_IF:
                handleInformIf(message);
                break;
            case INFORM_REF:
                handleInformRef(message);
                break;
            case NOT_UNDERSTOOD:
                handleNotUnderstood(message);
                break;
            case PROPOSE:
                handlePropose(message);
                break;
            case QUERY_IF:
                handleQueryIf(message);
                break;
            case QUERY_REF:
                handleQueryRef(message);
                break;
            case REFUSE:
                handleRefuse(message);
                break;
            case REJECT_PROPOSAL:
                handleRejectProposal(message);
                break;
            case REQUEST:
                handleRequest(message);
                break;
            case REQUEST_WHEN:
                handleRequestWhen(message);
                break;
            case REQUEST_WHENEVER:
                handleRequestWhenever(message);
                break;
            case SUBSCRIBE:
                handleSubscribe(message);
                break;
            case PROXY:
                handleProxy(message);
                break;
            case PROPAGATE:
                handlePropagate(message);
                break;
            case UNKNOWN:
                handleUnknown(message);
                break;
            default:
                System.err.println("Unknown performative: " + message.getPerformative());
        }
    }

    protected void handleAcceptProposal(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleAgree(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleCancel(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleCFP(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleConfirm(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleDisconfirm(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleFailure(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleInform(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleInformIf(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleInformRef(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleNotUnderstood(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handlePropose(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleQueryIf(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleQueryRef(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleRefuse(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleRejectProposal(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleRequest(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleRequestWhen(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleRequestWhenever(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleSubscribe(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleProxy(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handlePropagate(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void handleUnknown(ACLMessage message) {
        unhandledPerformative(message.getPerformative());
    }

    protected void unhandledPerformative(ACLMessage.Performative performative) {
        System.err.println("Warning: unhandled performative " + performative + ". Agent name: " + aid.getName());
    }

    public AID getAid() {
        return aid;
    }

    public void setId(AID aid) {
        this.aid = aid;
    }

    protected AgentManager getAgentManager() {
        if (agentManager == null) {
            agentManager = ManagerFactory.getAgentManager();
        }

        return agentManager;
    }

    protected MessagesManager getMessageManager() {
        if (messageManager == null) {
            messageManager = ManagerFactory.getMessagesManager();
        }

        return messageManager;
    }

    protected LogManager getLogManager() {
        if (logManager == null) {
            logManager = ManagerFactory.getLogManager();
        }

        return logManager;
    }
}
