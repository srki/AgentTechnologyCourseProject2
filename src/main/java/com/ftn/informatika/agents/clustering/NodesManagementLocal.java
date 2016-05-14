package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.exception.AliasExistsException;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Dragan Vidakovic
 */
@Local
public interface NodesManagementLocal {

    void registerNodes(List<AgentCenter> agentCenters) throws AliasExistsException;

    void removeNode(String alias);

}
