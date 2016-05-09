package com.ftn.informatika.agents.web_client.beans;

import com.ftn.informatika.agents.model.AID;
import com.ftn.informatika.agents.model.AgentType;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface AgentsLocal {
    List<AgentType> getAgentTypes();

    List<AID> getRunningAgents();

    AID runAgent(AgentType agentType, String name);

    AID stopAgent(AID aid);
}
