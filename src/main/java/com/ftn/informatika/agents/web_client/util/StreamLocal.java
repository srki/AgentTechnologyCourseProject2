package com.ftn.informatika.agents.web_client.util;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface StreamLocal {
    void sendMessage(StreamMessage message);
    List<StreamMessage> getMessages(Long time);
}
