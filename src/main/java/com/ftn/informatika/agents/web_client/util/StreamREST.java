package com.ftn.informatika.agents.web_client.util;

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

}
