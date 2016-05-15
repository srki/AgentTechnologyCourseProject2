package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.clustering.exception.AliasExistsException;
import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class NodesDbBean implements NodesDbLocal {
    private Map<String, AgentCenter> nodes = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addNode(AgentCenter agentCenter) throws AliasExistsException {
        if (nodes.containsKey(agentCenter.getAlias())) {
            throw new AliasExistsException();
        }

        nodes.put(agentCenter.getAlias(), agentCenter);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeNode(String alias) {
        nodes.remove(alias);
    }

    @Lock(LockType.READ)
    @Override
    public boolean containsNode(AgentCenter agentCenter) {
        return nodes.containsKey(agentCenter.getAlias());
    }

    @Lock(LockType.READ)
    @Override
    public boolean containsNode(String alias) {
        return nodes.containsKey(alias);
    }

    @Lock(LockType.READ)
    @Override
    public List<AgentCenter> getNodes() {
        return new ArrayList<>(nodes.values());
    }
}
