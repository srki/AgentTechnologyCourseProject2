package com.ftn.informatika.agents.environment.messages;

import com.ftn.informatika.agents.environment.model.ACLMessage;

/**
 * @author - Srđan Milaković
 */
public interface MessagesManager {
    void sendMessage(ACLMessage message);
}
