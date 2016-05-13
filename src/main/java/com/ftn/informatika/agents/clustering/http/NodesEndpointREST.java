package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ws.rs.*;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/nodes")
public interface NodesEndpointREST {

    @GET
    Object isAlive();

    @POST
    Object addNodes(List<AgentCenter> agentCenters);

    @Path("/{alias}")
    @DELETE
    Object deleteNode(@PathParam("alias") String alias);
}
