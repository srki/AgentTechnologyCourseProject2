package com.ftn.informatika.agents.environment.agents.service.http;

import com.ftn.informatika.agents.environment.agents.AgentClassesLocal;
import com.ftn.informatika.agents.environment.agents.RunningAgentsLocal;
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
    private AgentClassesLocal agentClassesBean;
    @EJB
    private RunningAgentsLocal runningAgentsBean;

    @Override
    public Object addClasses(Map<String, List<AgentType>> classes) {
        classes.forEach((alias, types) -> agentClassesBean.addClasses(alias, types));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object removeClasses(Map<String, List<AgentType>> classes) {
        classes.forEach((alias, types) -> agentClassesBean.removeClasses(alias, types));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object addRunning(List<AID> agents) {
        runningAgentsBean.addRunningAgents(agents);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object removeRunning(List<AID> agents) {
        runningAgentsBean.removeRunningAgents(agents);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
