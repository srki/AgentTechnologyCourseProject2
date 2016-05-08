package com.ftn.informatika.agents.service;

import com.ftn.informatika.agents.model.ACLMessage;
import com.ftn.informatika.agents.service.endpoints.MessagesEndpointREST;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessagesREST implements MessagesEndpointREST {
    @Override
    public Object sendMessage(ACLMessage message) {
        return null;
    }

    @Override
    public List<String> getPerformatives() {
        List<String> performatives = new ArrayList<>();
        for (ACLMessage.Performative performative : ACLMessage.Performative.values()) {
            performatives.add(performative.name().replace('_', ' '));
        }
        return performatives;
    }
}
