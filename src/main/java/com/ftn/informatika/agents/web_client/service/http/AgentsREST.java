package com.ftn.informatika.agents.web_client.service.http;

import com.ftn.informatika.agents.environment.agents.AgentClassesLocal;
import com.ftn.informatika.agents.environment.agents.RunningAgentsLocal;
import com.ftn.informatika.agents.environment.agents.manager.AgentManagerLocal;
import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.web_client.service.ErrorObject;
import com.ftn.informatika.agents.web_client.service.http.endpoint_interface.AgentsEndpointREST;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class AgentsREST implements AgentsEndpointREST {

    @EJB
    private AgentManagerLocal agentManagerBean;
    @EJB
    private AgentClassesLocal agentClassesBean;
    @EJB
    private RunningAgentsLocal runningAgentsBean;

    @Override
    public List<AgentType> getClasses() {
        return agentClassesBean.getAllClassesAsList();
    }

    @Override
    public List<AID> getRunning() {
        return runningAgentsBean.getAllRunningAgents();
    }

    @Override
    public Object runAgent(AgentType type, String name) {
        try {
            return agentManagerBean.runAgent(type, name);
        } catch (EJBException e) {
            if (e.getCause() instanceof NameAlreadyExistsException) {
                return new ErrorObject("Name already exists.");
            } else {
                e.printStackTrace();
                return new ErrorObject(e.getMessage());
            }
        } catch (NameAlreadyExistsException e) {
            return new ErrorObject("Name already exists.");
        }
    }

    @Override
    public Object stopAgent(AID aid) {
        agentManagerBean.stopAgent(aid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
