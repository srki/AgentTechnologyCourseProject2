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

    boolean containsRemoteNode(String alias);

    AgentCenter getRemoteNode(String alias);

    List<AgentCenter> getAllNodes();

    List<AgentCenter> getRemoteNodes();

    AgentCenter getLocal();

    void setLocal(AgentCenter agentCenter);

}
