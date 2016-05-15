package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.web_client.service.http.endpoint_interface.AgentsEndpointREST;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class AgentsRequester {

    private String destinationAddress;

    public AgentsRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public List<AgentType> getClasses() {
        return createEndpoint().getClasses();
    }

    public List<AID> getRunning() {
        return createEndpoint().getRunning();
    }

    public void stopAgent(AID aid) {
        createEndpoint().stopAgent(aid);
    }

    private AgentsEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(AgentsEndpointREST.class);
    }
}
