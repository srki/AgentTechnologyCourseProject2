package com.ftn.informatika.agents.environment.messages.service.http;

import com.ftn.informatika.agents.ApplicationConfig;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * @author - Srđan Milaković
 */
public class MessageReceiverRequester {
    private String destinationAddress;

    public MessageReceiverRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void receiveMessage(ACLMessage message) {
        createEndpoint().receiveMessage(message);
    }

    private MessageReceiverEndpointREST createEndpoint() {
        String url = String.format(ApplicationConfig.APPLICATION_URL, destinationAddress);
        return new ResteasyClientBuilder().build().target(url).proxy(MessageReceiverEndpointREST.class);
    }
}
