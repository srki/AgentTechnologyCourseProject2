package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/nodes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface NodesEndpointREST {

    @GET
    Object isAlive();

    @POST
    Object addNodes(List<AgentCenter> agentCenters);

    @Path("/{alias}")
    @DELETE
    Object removeNode(@PathParam("alias") String alias);

    @Path("/list")
    @GET
    List<AgentCenter> getAgentCenters();
}
