package com.ftn.informatika.agents.web_client.service.http.endpoint_interface;

import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MessagesEndpointREST {

    @POST
    Object sendMessage(ACLMessage message);

    @GET
    List<String> getPerformatives();

}
