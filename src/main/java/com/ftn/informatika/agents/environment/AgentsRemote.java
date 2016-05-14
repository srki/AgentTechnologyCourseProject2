package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.Remote;

/**
 * @author - Srđan Milaković
 */
@Remote
public interface AgentsRemote {
    AID runAgent(AgentType agentType, String name);

    AID stopAgent(AID aid);

    Agent getAgent(AID aid);
}
