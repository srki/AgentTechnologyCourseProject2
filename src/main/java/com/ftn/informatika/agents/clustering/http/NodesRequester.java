package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Object isAlive() {
        return createEndpoint().isAlive();
    }

    private NodesEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder()
                .establishConnectionTimeout(5, TimeUnit.SECONDS)
                .socketTimeout(5, TimeUnit.SECONDS)
                .build().target(url).proxy(NodesEndpointREST.class);
    }
}
