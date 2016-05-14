package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@Path("/agents")
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
