package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.model.AgentCenter;

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
public class NodesDbBeanLocal implements NodesDbLocal {
    private Map<String, AgentCenter> nodes = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addNode(AgentCenter agentCenter) {
        nodes.put(agentCenter.getAlias(), agentCenter);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeNode(AgentCenter agentCenter) {
        nodes.remove(agentCenter.getAlias());
    }

    @Override
    public boolean containsNode(AgentCenter agentCenter) {
        return nodes.containsKey(agentCenter.getAlias());
    }

    @Override
    public boolean containsNode(String alias) {
        return nodes.containsKey(alias);
    }

    @Override
    public List<AgentCenter> getNodes() {
        return new ArrayList<>(nodes.values());
    }
}
