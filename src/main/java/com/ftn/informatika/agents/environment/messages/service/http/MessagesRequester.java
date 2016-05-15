package com.ftn.informatika.agents.environment.messages.service.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.web_client.service.http.endpoint_interface.MessagesEndpointREST;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * @author - Srđan Milaković
 */
public class MessagesRequester {

    private String destinationAddress;

    public MessagesRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void sendMessage(ACLMessage message) {
        createEndpoint().sendMessage(message);
    }

    private MessagesEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(MessagesEndpointREST.class);
    }
}
