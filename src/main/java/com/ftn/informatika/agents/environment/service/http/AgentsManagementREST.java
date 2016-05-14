package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class AgentsManagementREST implements AgentsManagementEndpointREST {

    @EJB
    private AgentsLocal agentsBean;

    @Override
    public Object addClasses(Map<String, List<AgentType>> classes) {
        classes.forEach((alias, types) -> agentsBean.addClasses(alias, types));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object addRunning(List<AID> agents) {
        agentsBean.addRunningAgents(agents);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object removeClasses(Map<String, List<AgentType>> classes) {
        classes.forEach((alias, types) -> agentsBean.removeClasses(alias, types));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object removeRunning(List<AID> agents) {
        agentsBean.removeRunningAgents(agents);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
