package com.ftn.informatika.agents.environment.agents;

import com.ftn.informatika.agents.environment.exceptions.AgentClassDoesNotExistException;
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
public class AgentClassesBean implements AgentClassesLocal {

    @EJB
    private StreamLocal streamBean;

    private List<AgentType> localTypes = new ArrayList<>();
    private HashMap<String, List<AgentType>> allTypes = new HashMap<>();

    @Lock(LockType.READ)
    @Override
    public List<AgentType> getLocalClasses() {
        return localTypes;
    }

    @Lock(LockType.READ)
    @Override
    public Map<String, List<AgentType>> getAllClasses() {
        return allTypes;
    }

    @Lock(LockType.READ)
    @Override
    public List<AgentType> getAllClassesAsList() {
        Set<AgentType> set = new HashSet<>();
        allTypes.forEach((alias, list) -> set.addAll(list));
        return new ArrayList<>(set);
    }

    @Lock(LockType.WRITE)
    @Override
    public void addClasses(String alias, List<AgentType> types) {
        if (!allTypes.containsKey(alias)) {
            allTypes.put(alias, new ArrayList<>());
        }

        allTypes.get(alias).addAll(types);
        sendClassesToStream();
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeClasses(String alias, List<AgentType> types) {
        if (!allTypes.containsKey(alias)) {
            return;
        }

        allTypes.get(alias).removeAll(types);
        sendClassesToStream();
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeClasses(String alias) {
        allTypes.remove(alias);
        sendClassesToStream();
    }

    @Lock(LockType.READ)
    @Override
    public String findAgentCenter(AgentType agentType) {
        for (Map.Entry<String, List<AgentType>> entry : allTypes.entrySet()) {
            for (AgentType type : entry.getValue()) {
                if (agentType.equals(type)) {
                    return entry.getKey();
                }
            }
        }

        throw new AgentClassDoesNotExistException();
    }

    private void sendClassesToStream() {
        streamBean.sendMessage(new StreamMessage(StreamMessage.MessageType.CLASSES, getAllClassesAsList()));
    }
}
