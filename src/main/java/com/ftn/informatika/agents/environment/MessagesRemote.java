package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.ejb.Remote;

/**
 * @author - Srđan Milaković
 */
@Remote
public interface MessagesRemote {
    void sendMessage(ACLMessage message);
}
