package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.clustering.exception.AliasExistsException;
import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface NodesDbLocal {
    void addNode(AgentCenter agentCenter) throws AliasExistsException;

    void removeNode(String alias);

    boolean containsNode(AgentCenter agentCenter);

    boolean containsNode(String alias);

    List<AgentCenter> getNodes();
}
