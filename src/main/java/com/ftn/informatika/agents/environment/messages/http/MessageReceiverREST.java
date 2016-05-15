package com.ftn.informatika.agents.environment.messages.http;

import com.ftn.informatika.agents.environment.messages.MessagesLocal;
import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessageReceiverREST implements MessageReceiverEndpointREST {

    @EJB
    private MessagesLocal messagesBean;

    @Override
    public Object receiveMessage(ACLMessage message) {
        messagesBean.receiveMessage(message);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
