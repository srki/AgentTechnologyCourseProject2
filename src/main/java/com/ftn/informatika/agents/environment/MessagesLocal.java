package com.ftn.informatika.agents.environment;

import com.ftn.informatika.agents.model.ACLMessage;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface MessagesLocal {
    void sendMessage(ACLMessage message);

    List<String> getPerformatives();
}
