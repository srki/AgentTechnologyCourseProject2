package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessagesBean implements MessagesLocal {
    @Override
    public void sendMessage(ACLMessage message) {
    }

    @Override
    public List<String> getPerformatives() {
        List<String> performatives = new ArrayList<>();
        for (ACLMessage.Performative performative : ACLMessage.Performative.values()) {
            performatives.add(performative.name());
        }
        return performatives;
    }
}
