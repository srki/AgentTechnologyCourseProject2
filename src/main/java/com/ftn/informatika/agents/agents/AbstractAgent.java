package com.ftn.informatika.agents.agents;

import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.MessagesLocal;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.util.log.LogBean;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;

/**
 * Base class for all agents.
 *
 * @author Dragan Vidakovic
 */
// TODO: Refactor AbstractAgent
@Lock(LockType.READ)
public abstract class AbstractAgent implements RemoteAgent {

    @EJB
    private LogBean logger;

    protected AID myAid;
    private AgentsLocal agm;
    private MessagesLocal msm;

    @Override
    public void init(AID aid) {
        myAid = aid;
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        // TODO: support for heartbeats?
        try {
            onMessage(msg);
        } catch (Exception e) {
            logger.error("Error while delivering message: " + msg);
        }
    }

    protected abstract void onMessage(ACLMessage msg);

    public AID getAid() {
        return myAid;
    }

    // TODO: ObjectFactory ?
    protected AgentsLocal agm(){
//        if (agm == null)
//            agm = ObjectFactory.getAgentManager();
        return agm;
    }

    protected MessagesLocal msm() {
//        if (msm == null)
//            msm = ObjectFactory.getMessageManager();
        return msm;
    }

}
