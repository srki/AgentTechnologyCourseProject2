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
    private AgentCenter localNode;
    private Map<String, AgentCenter> remoteNodes = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addNode(AgentCenter node) throws AliasExistsException {
        if (node.equals(localNode)) {
            return;
        }

        if (remoteNodes.containsKey(node.getAlias())) {
            throw new AliasExistsException();
        }

        remoteNodes.put(node.getAlias(), node);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeNode(String alias) {
        remoteNodes.remove(alias);
    }

    @Lock(LockType.READ)
    @Override
    public boolean containsRemoteNode(String alias) {
        return remoteNodes.containsKey(alias);
    }

    @Override
    public AgentCenter getRemoteNode(String alias) {
        return remoteNodes.get(alias);
    }

    @Lock(LockType.READ)
    @Override
    public List<AgentCenter> getAllNodes() {
        List<AgentCenter> allNodes = new ArrayList<>(remoteNodes.values());
        allNodes.add(localNode);
        return allNodes;
    }

    @Override
    public List<AgentCenter> getRemoteNodes() {
        return new ArrayList<>(remoteNodes.values());
    }

    @Override
    public AgentCenter getLocal() {
        return localNode;
    }

    @Override
    public void setLocal(AgentCenter agentCenter) {
        remoteNodes.remove(agentCenter.getAlias());
        localNode = agentCenter;
    }
}
