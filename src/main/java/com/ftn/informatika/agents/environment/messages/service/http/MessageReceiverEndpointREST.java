package com.ftn.informatika.agents.environment.messages.service.http;

import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author - Srđan Milaković
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MessageReceiverEndpointREST {

    @PUT
    Object receiveMessage(ACLMessage message);
}
