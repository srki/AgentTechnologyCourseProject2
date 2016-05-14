package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@Local
public interface AgentsLocal extends AgentsRemote {
    // Agent classes
    List<AgentType> getLocalClasses();

    HashMap<String, List<AgentType>> getClasses();

    void addClasses(List<AgentType> types);

    void addClasses(String alias, List<AgentType> types);

    void removeClasses(String alias, List<AgentType> types);

    // Running agents
    Map<AID, Agent> getLocalRunningAgents();

    List<AID> getRunningAgents();

    void addRunningAgents(List<AID> agents);

    void removeRunningAgents(List<AID> agents);

    void removeRunningAgentsFromNode(String alias);

}
