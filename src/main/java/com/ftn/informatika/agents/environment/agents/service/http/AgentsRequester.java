package com.ftn.informatika.agents.environment.agents.service.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.web_client.service.http.endpoint_interface.AgentsEndpointREST;
import com.google.gson.Gson;
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

    public AID runAgent(AgentType type, String name) {
        try {
            return new Gson().fromJson(new Gson().toJson(createEndpoint().runAgent(type, name)), AID.class);
        } catch (Exception e) {
            throw new NameAlreadyExistsException();
        }
    }

    public void stopAgent(AID aid) {
        createEndpoint().stopAgent(aid);
    }

    private AgentsEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(AgentsEndpointREST.class);
    }
}
