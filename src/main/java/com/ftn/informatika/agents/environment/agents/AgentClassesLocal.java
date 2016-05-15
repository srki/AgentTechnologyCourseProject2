package com.ftn.informatika.agents.environment.agents;

import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@Local
public interface AgentClassesLocal {

    List<AgentType> getLocalClasses();

    Map<String, List<AgentType>> getAllClasses();

    List<AgentType> getAllClassesAsList();

    void addClasses(String alias, List<AgentType> types);

    void removeClasses(String alias, List<AgentType> types);

    void removeClasses(String alias);

    String findAgentCenter(AgentType agentType);
}
