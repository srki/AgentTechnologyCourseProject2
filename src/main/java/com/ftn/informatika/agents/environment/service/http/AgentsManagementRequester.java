package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class AgentsManagementRequester {

    private String destinationAddress;

    public AgentsManagementRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    private void addClasses(AgentCenter agentCenter, List<AgentType> agentTypes) {
        createEndpoint().addClasses(new AgentTypesRequest(agentCenter, agentTypes));
    }

    private void addRunning(List<AID> agents) {
        createEndpoint().addRunning(agents);
    }

    private void removeClasses(AgentCenter agentCenter, List<AgentType> agentTypes) {
        createEndpoint().removeClasses(new AgentTypesRequest(agentCenter, agentTypes));
    }

    private void removeRunning(List<AID> agents) {
        createEndpoint().removeRunning(agents);
    }

    private AgentsManagementEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(AgentsManagementEndpointREST.class);
    }
}
