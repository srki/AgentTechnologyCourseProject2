package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.environment.model.AID;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/agents")
public interface AgentsManagementEndpointREST {
    @POST
    @Path("/classes")
    Object addClasses(AgentTypesRequest request);

    @POST
    @Path("/running")
    Object addRunning(List<AID> agents);

    @DELETE
    @Path("/classes")
    Object removeClasses(AgentTypesRequest request);

    @DELETE
    @Path("/running")
    Object removeRunning(List<AID> agents);
}
