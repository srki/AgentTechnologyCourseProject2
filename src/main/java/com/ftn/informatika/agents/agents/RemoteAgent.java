package com.ftn.informatika.agents.agents;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;

/**
 * Remote interface for agents.
 *
 * @author Dragan Vidakovic
 */

// TODO: Refactor RemoteAgent.
public interface RemoteAgent {

    void init(AID aid);

    void stop();

    void handleMessage(ACLMessage msg);

    String ping();

}
