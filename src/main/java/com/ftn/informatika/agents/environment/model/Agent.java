package com.ftn.informatika.agents.environment.model;

import com.ftn.informatika.agents.environment.AgentsRemote;
import com.ftn.informatika.agents.environment.MessagesRemote;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;
import com.ftn.informatika.agents.environment.util.log.LogBean;

import javax.ejb.EJB;
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

    protected AID myAid;

    private AgentsRemote agm;
    private MessagesRemote msm;

    @Override
    public void init(AID aid) {
        myAid = aid;
    }

    @Override
    public boolean handleMessage(ACLMessage message) {
        switch (message.getPerformative()) {
            case ACCEPT_PROPOSAL:
                return handleAcceptProposal(message);
            case AGREE:
                return handleAgree(message);
            case CANCEL:
                return handleCancel(message);
            case CFP:
                return handleCFP(message);
            case CONFIRM:
                return handleConfirm(message);
            case DISCONFIRM:
                return handleDisconfirm(message);
            case FAILURE:
                return handleFailure(message);
            case INFORM:
                return handleInform(message);
            case INFORM_IF:
                return handleInformIf(message);
            case INFORM_REF:
                return handleInformRef(message);
            case NOT_UNDERSTOOD:
                return handleNotUnderstood(message);
            case PROPOSE:
                return handlePropose(message);
            case QUERY_IF:
                return handleQueryIf(message);
            case QUERY_REF:
                return handleQueryRef(message);
            case REFUSE:
                return handleRefuse(message);
            case REJECT_PROPOSAL:
                return handleRejectProposal(message);
            case REQUEST:
                return handleRequest(message);
            case REQUEST_WHEN:
                return handleRequestWhen(message);
            case REQUEST_WHENEVER:
                return handleRequestWhenever(message);
            case SUBSCRIBE:
                return handleSubscribe(message);
            case PROXY:
                return handleProxy(message);
            case PROPAGATE:
                return handlePropagate(message);
            case UNKNOWN:
                return handleUnknown(message);
            default:
                return false;
        }
    }

    protected boolean handleAcceptProposal(ACLMessage message) {
        return false;
    }

    protected boolean handleAgree(ACLMessage message) {
        return false;
    }

    protected boolean handleCancel(ACLMessage message) {
        return false;
    }

    protected boolean handleCFP(ACLMessage message) {
        return false;
    }

    protected boolean handleConfirm(ACLMessage message) {
        return false;
    }

    protected boolean handleDisconfirm(ACLMessage message) {
        return false;
    }

    protected boolean handleFailure(ACLMessage message) {
        return false;
    }

    protected boolean handleInform(ACLMessage message) {
        return false;
    }

    protected boolean handleInformIf(ACLMessage message) {
        return false;
    }

    protected boolean handleInformRef(ACLMessage message) {
        return false;
    }

    protected boolean handleNotUnderstood(ACLMessage message) {
        return false;
    }

    protected boolean handlePropose(ACLMessage message) {
        return false;
    }

    protected boolean handleQueryIf(ACLMessage message) {
        return false;
    }

    protected boolean handleQueryRef(ACLMessage message) {
        return false;
    }

    protected boolean handleRefuse(ACLMessage message) {
        return false;
    }

    protected boolean handleRejectProposal(ACLMessage message) {
        return false;
    }

    protected boolean handleRequest(ACLMessage message) {
        return false;
    }

    protected boolean handleRequestWhen(ACLMessage message) {
        return false;
    }

    protected boolean handleRequestWhenever(ACLMessage message) {
        return false;
    }

    protected boolean handleSubscribe(ACLMessage message) {
        return false;
    }

    protected boolean handleProxy(ACLMessage message) {
        return false;
    }

    protected boolean handlePropagate(ACLMessage message) {
        return false;
    }

    protected boolean handleUnknown(ACLMessage message) {
        return false;
    }

    public AID getId() {
        return myAid;
    }

    public void setId(AID myAid) {
        this.myAid = myAid;
    }

    protected AgentsRemote agm() {
        if (agm == null)
            agm = ManagerFactory.getAgentManager();
        return agm;
    }

    protected MessagesRemote msm() {
        if (msm == null)
            msm = ManagerFactory.getMessagesManager();
        return msm;
    }
}
