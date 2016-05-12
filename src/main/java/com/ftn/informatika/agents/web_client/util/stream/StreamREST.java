package com.ftn.informatika.agents.web_client.util.stream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import java.util.ArrayList;
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
        streamBean.sendMessage(new StreamMessage("dsa", "sad"));
        return "ok";
    }
}
