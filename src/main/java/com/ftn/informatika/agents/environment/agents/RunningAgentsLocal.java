package com.ftn.informatika.agents.environment.agents;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@Local
public interface RunningAgentsLocal {
    Map<AID, Agent> getLocalRunningAgents();

    List<AID> getAllRunningAgents();

    Agent getLocalAgent(AID aid);

    void addLocalAgent(Agent agent);

    void removeLocalAgent(AID aid);

    boolean containsAgent(AID aid);

    void addRunningAgents(List<AID> agents);

    void removeRunningAgents(List<AID> agents);

    void removeRunningAgentsFromNode(String alias);

}
