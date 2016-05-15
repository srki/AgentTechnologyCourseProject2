package com.ftn.informatika.agents.environment.agents;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.web_client.util.StreamLocal;
import com.ftn.informatika.agents.web_client.util.StreamMessage;

import javax.ejb.*;
import java.util.*;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class RunningAgentsBean implements RunningAgentsLocal {

    @EJB
    private StreamLocal streamBean;

    private Map<AID, Agent> localAgents = new HashMap<>();
    private Set<AID> allAgents = new HashSet<>();

    @Lock(LockType.READ)
    @Override
    public Map<AID, Agent> getLocalRunningAgents() {
        return localAgents;
    }

    @Lock(LockType.READ)
    @Override
    public List<AID> getAllRunningAgents() {
        return new ArrayList<>(allAgents);
    }

    @Lock(LockType.READ)
    @Override
    public Agent getLocalAgent(AID aid) {
        return localAgents.get(aid);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addLocalAgent(Agent agent) {
        localAgents.put(agent.getAid(), agent);
        addRunningAgents(Collections.singletonList(agent.getAid()));
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeLocalAgent(AID aid) {
        localAgents.remove(aid);
        removeRunningAgents(Collections.singletonList(aid));
    }

    @Lock(LockType.READ)
    @Override
    public boolean containsAgent(AID aid) {
        return allAgents.contains(aid);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addRunningAgents(List<AID> agents) {
        allAgents.addAll(agents);
        sendAgentsToStream();
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeRunningAgents(List<AID> agents) {
        allAgents.removeAll(agents);
        sendAgentsToStream();
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
        sendAgentsToStream();
    }

    private void sendAgentsToStream() {
        streamBean.sendMessage(new StreamMessage(StreamMessage.MessageType.AGENTS, getAllRunningAgents()));
    }
}
