package com.ftn.informatika.agents.environment.agents.service.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
public class AgentsManagementRequester {

    private String destinationAddress;

    public AgentsManagementRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void addClasses(Map<String, List<AgentType>> classes) {
        createEndpoint().addClasses(classes);
    }

    public void addClasses(AgentCenter agentCenter, List<AgentType> agentTypes) {
        Map<String, List<AgentType>> classes = new HashMap<>();
        classes.put(agentCenter.getAlias(), agentTypes);
        addClasses(classes);
    }

    public void addRunning(List<AID> agents) {
        createEndpoint().addRunning(agents);
    }

    public void removeClasses(Map<String, List<AgentType>> classes) {
        createEndpoint().removeClasses(classes);
    }

    public void removeClasses(AgentCenter agentCenter, List<AgentType> agentTypes) {
        Map<String, List<AgentType>> classes = new HashMap<>();
        classes.put(agentCenter.getAlias(), agentTypes);
        removeClasses(classes);
    }

    public void removeRunning(List<AID> agents) {
        createEndpoint().removeRunning(agents);
    }

    private AgentsManagementEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(AgentsManagementEndpointREST.class);
    }
}
