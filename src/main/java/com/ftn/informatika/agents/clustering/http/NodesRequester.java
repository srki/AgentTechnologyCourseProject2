package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class NodesRequester {

    public static void addNodes(String destinationAddress, List<AgentCenter> agentCenters) {
        createEndpoint(destinationAddress).addNodes(agentCenters);
    }

    public static void removeNode(String destinationAddress, String alias) {
        createEndpoint(destinationAddress).deleteNode(alias);
    }

    private static NodesEndpointREST createEndpoint(String destinationAddress) {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(NodesEndpointREST.class);
    }
}
