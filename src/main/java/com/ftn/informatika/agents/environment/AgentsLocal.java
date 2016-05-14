package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface AgentsLocal {
    List<AgentType> getClasses();

    HashMap<String, List<AgentType>> getAllClasses();

    void addClasses(List<AgentType> types);

    void addClasses(String alias, List<AgentType> types);

    void removeClasses(String alias, List<AgentType> types);

    List<AID> getRunningAgents();

    List<AID> getAllRunningAgents();

    void addRunningAgents(List<AID> agents);

    void removeRunningAgents(List<AID> agents);

    AID runAgent(AgentType agentType, String name);

    AID stopAgent(AID aid);
}
