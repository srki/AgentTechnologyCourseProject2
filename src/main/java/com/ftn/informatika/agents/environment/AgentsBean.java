package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;

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
public class AgentsBean implements AgentsLocal {
    private List<AgentType> localTypes = new ArrayList<>();
    private HashMap<String, List<AgentType>> remoteTypes = new HashMap<>();

    private Map<AID, Agent> localAgents = new HashMap<>();
    private List<AID> agents = new ArrayList<>();

    @Lock(LockType.READ)
    @Override
    public List<AgentType> getClasses() {
        return localTypes;
    }

    @Lock(LockType.READ)
    @Override
    public HashMap<String, List<AgentType>> getAllClasses() {
        return remoteTypes;
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(List<AgentType> types) {
        localTypes.addAll(types);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(String alias, List<AgentType> types) {
        if (!remoteTypes.containsKey(alias)) {
            remoteTypes.put(alias, new ArrayList<>());
        }

        remoteTypes.get(alias).addAll(types);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeClasses(String alias, List<AgentType> types) {
        if (!remoteTypes.containsKey(alias)) {
            return;
        }

        remoteTypes.get(alias).removeAll(types);
    }

    /**
     * Returns local agents as AID list
     *
     * @return local agents
     */
    @Lock(LockType.READ)
    @Override
    public Map<AID, Agent> getRunningAgents() {
        return localAgents;
    }

    /**
     * Returns local and remote agents as AID list
     *
     * @return all agents
     */
    @Override
    public List<AID> getAllRunningAgents() {
        return agents;
    }

    @Lock(LockType.WRITE)
    @Override
    public void addRunningAgents(List<AID> agents) {
        this.agents.addAll(agents);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeRunningAgents(List<AID> agents) {
        this.agents.removeAll(agents);
    }

    @Lock(LockType.WRITE)
    @Override
    public AID runAgent(AgentType agentType, String name) {
        // TODO: implement
        return null;
    }

    @Lock(LockType.WRITE)
    @Override
    public AID stopAgent(AID aid) {
        // TODO: implement
        return null;
    }

    @Override
    public Agent getAgent(AID aid) {
        return localAgents.get(aid);
    }
}
