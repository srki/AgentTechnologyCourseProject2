package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.model.AgentCenter;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface NodesDbLocal {
    void addNode(AgentCenter agentCenter);

    void removeNode(AgentCenter agentCenter);

    boolean containsNode(AgentCenter agentCenter);

    boolean containsNode(String alias);

    List<AgentCenter> getNodes();
}
