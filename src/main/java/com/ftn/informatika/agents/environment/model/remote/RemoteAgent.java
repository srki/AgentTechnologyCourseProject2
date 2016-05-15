package com.ftn.informatika.agents.environment.model.remote;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;

/**
 * Remote interface for agents.
 *
 * @author Dragan Vidakovic
 */
public interface RemoteAgent {

    void init(AID aid);

    void handleMessage(ACLMessage msg);

}
