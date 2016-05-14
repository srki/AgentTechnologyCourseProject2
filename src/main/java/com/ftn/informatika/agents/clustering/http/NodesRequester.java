package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class NodesRequester {

    private String destinationAddress;

    public NodesRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void addNodes(List<AgentCenter> agentCenters) {
        createEndpoint().addNodes(agentCenters);
    }

    public void removeNode(String alias) {
        createEndpoint().removeNode(alias);
    }

    private NodesEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(NodesEndpointREST.class);
    }
}
