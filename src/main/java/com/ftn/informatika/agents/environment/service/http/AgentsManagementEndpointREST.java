package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@Path("/agents")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AgentsManagementEndpointREST {
    @POST
    @Path("/classes")
    Object addClasses(Map<String, List<AgentType>> classes);

    @POST
    @Path("/running")
    Object addRunning(List<AID> agents);

    @DELETE
    @Path("/classes")
    Object removeClasses(Map<String, List<AgentType>> classes);

    @DELETE
    @Path("/running")
    Object removeRunning(List<AID> agents);
}
