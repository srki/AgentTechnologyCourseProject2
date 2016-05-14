package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.config.ConfigurationLocal;
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

    @EJB
    private ConfigurationLocal configurationBean;

    private List<AgentType> localTypes = new ArrayList<>();
    private HashMap<String, List<AgentType>> allTypes = new HashMap<>();

    private Map<AID, Agent> localAgents = new HashMap<>();
    private List<AID> allAgents = new ArrayList<>();

    @Lock(LockType.READ)
    @Override
    public List<AgentType> getLocalClasses() {
        return localTypes;
    }

    @Lock(LockType.READ)
    @Override
    public HashMap<String, List<AgentType>> getClasses() {
        return allTypes;
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(List<AgentType> types) {
        addClasses(configurationBean.getAlias(), types);
        localTypes.addAll(types);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(String alias, List<AgentType> types) {
        if (!this.allTypes.containsKey(alias)) {
            this.allTypes.put(alias, new ArrayList<>());
        }

        this.allTypes.get(alias).addAll(types);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeClasses(String alias, List<AgentType> types) {
        if (!this.allTypes.containsKey(alias)) {
            return;
        }

        if (types == null) {
            this.allTypes.remove(alias);
        } else {
            this.allTypes.get(alias).removeAll(types);
        }
    }

    /**
     * Returns local agents as AID list
     *
     * @return local agents
     */
    @Lock(LockType.READ)
    @Override
    public Map<AID, Agent> getLocalRunningAgents() {
        return localAgents;
    }

    /**
     * Returns local and remote agents as AID list
     *
     * @return all agents
     */
    @Lock(LockType.READ)
    @Override
    public List<AID> getRunningAgents() {
        return allAgents;
    }

    @Lock(LockType.WRITE)
    @Override
    public void addRunningAgents(List<AID> agents) {
        allAgents.addAll(agents);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeRunningAgents(List<AID> agents) {
        allAgents.removeAll(agents);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeRunningAgentsFromNode(String alias) {
        List<AID> removeList = new ArrayList<>();

        allAgents.forEach(aid -> {
            if (aid.getHost().getAlias().equals(alias)) {
                removeList.add(aid);
            }
        });

        allAgents.removeAll(removeList);
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
