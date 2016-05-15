package com.ftn.informatika.agents.environment.agents.manager;

import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;

/**
 * @author - Srđan Milaković
 */
public interface AgentManager {
    AID runAgent(AgentType agentType, String name) throws NameAlreadyExistsException;

    void stopAgent(AID aid);

    Agent getAgent(AID aid);
}
