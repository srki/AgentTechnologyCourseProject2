package com.ftn.informatika.agents.environment.messages;

import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface MessagesLocal extends MessagesRemote {
    List<String> getPerformatives();

    void receiveMessage(ACLMessage message);
}
