package com.ftn.informatika.agents.web_client.service.http;

import com.ftn.informatika.agents.model.ACLMessage;
import com.ftn.informatika.agents.environment.MessagesLocal;
import com.ftn.informatika.agents.web_client.service.http.endpoint_interface.MessagesEndpointREST;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessagesREST implements MessagesEndpointREST {
    @EJB
    private MessagesLocal messagesBean;

    @Override
    public Object sendMessage(ACLMessage message) {
        messagesBean.sendMessage(message);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public List<String> getPerformatives() {
        return messagesBean.getPerformatives();
    }
}
