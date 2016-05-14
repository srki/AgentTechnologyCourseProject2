package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.web_client.util.StreamLocal;
import com.ftn.informatika.agents.web_client.util.StreamMessage;

import javax.ejb.*;
import java.util.*;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class AgentsBean implements AgentsLocal, AgentsRemote {

    @EJB
    private ConfigurationLocal configurationBean;
    @EJB
    private StreamLocal streamBean;

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
    public Map<String, List<AgentType>> getClasses() {
        return allTypes;
    }

    @Override
    public List<AgentType> getClassesAsList() {
        Set<AgentType> set = new HashSet<>();
        allTypes.forEach((alias, list) -> set.addAll(list));
        return new ArrayList<>(set);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(List<AgentType> types) {
        localTypes.addAll(types);
        addClasses(configurationBean.getAlias(), types);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(String alias, List<AgentType> types) {
        if (!this.allTypes.containsKey(alias)) {
            this.allTypes.put(alias, new ArrayList<>());
        }

        this.allTypes.get(alias).addAll(types);
        sendClassesToStream();
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
        sendClassesToStream();
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeRunningAgents(List<AID> agents) {
        allAgents.removeAll(agents);
        sendClassesToStream();
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
        sendClassesToStream();
    }

    @Lock(LockType.WRITE)
    @Override
    public AID runAgent(AgentType agentType, String name) {
        // TODO: implement
        sendAgentsToStream();
        return null;
    }

    @Lock(LockType.WRITE)
    @Override
    public AID stopAgent(AID aid) {
        // TODO: implement
        sendAgentsToStream();
        return null;
    }

    @Override
    public Agent getAgent(AID aid) {
        return localAgents.get(aid);
    }

    private void sendClassesToStream() {
        streamBean.sendMessage(new StreamMessage(StreamMessage.MessageType.CLASSES, getClassesAsList()));
    }

    private void sendAgentsToStream() {
        streamBean.sendMessage(new StreamMessage(StreamMessage.MessageType.AGENTS, getLocalRunningAgents()));
    }
}
