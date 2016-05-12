package com.ftn.informatika.agents.web_client.util.stream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class StreamREST implements StreamEndpointREST {

    @EJB
    private StreamLocal streamBean;

    @Override
    public List<StreamMessage> getMessages(Long last) {
        return streamBean.getMessages(last);
    }

    @Override
    public Object addMessage() {
        for (int i = 0; i < 5; i++)
            streamBean.sendMessage(new StreamMessage("STREAM_MESSAGES", "This message is created for testing."));
        return "ok";
    }
}
