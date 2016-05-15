package com.ftn.informatika.agents.web_client.service.http.endpoint_interface;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/agents")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AgentsEndpointREST {

    @GET
    @Path("/classes")
    List<AgentType> getClasses();

    @GET
    @Path("/running")
    List<AID> getRunning();

    @PUT
    @Path("/running/{type}/{name}")
    Object runAgent(@PathParam("type") AgentType type, @PathParam("name") String name);

    @DELETE
    @Path("/{aid}")
    Object stopAgent(@PathParam("aid") AID aid);
}
