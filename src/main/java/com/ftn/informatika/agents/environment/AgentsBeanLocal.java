package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Singleton
public class AgentsBeanLocal implements AgentsLocal {
    @Override
    public List<AgentType> getClasses() {
        return new ArrayList<>();
    }

    @Override
    public List<AID> getRunningAgents() {
        return new ArrayList<>();
    }

    @Override
    public AID runAgent(AgentType agentType, String name) {
        return null;
    }

    @Override
    public AID stopAgent(AID aid) {
        return null;
    }
}
