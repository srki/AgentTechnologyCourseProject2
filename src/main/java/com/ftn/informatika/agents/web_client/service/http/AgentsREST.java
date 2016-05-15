package com.ftn.informatika.agents.web_client.service.http;

import com.ftn.informatika.agents.clustering.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.service.http.AgentsRequester;
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
    private AgentsLocal agentsBean;
    @EJB
    private ConfigurationLocal configurationBean;

    @Override
    public List<AgentType> getClasses() {
        return agentsBean.getClassesAsList();
    }

    @Override
    public List<AID> getRunning() {
        return agentsBean.getRunningAgents();
    }

    @Override
    public Object runAgent(AgentType type, String name) {
        try {
            agentsBean.runAgent(type, name);
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
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object stopAgent(AID aid) {
        if (!configurationBean.getAgentCenter().equals(aid.getHost())) {
            new AgentsRequester(aid.getHost().getAddress()).stopAgent(aid);
        } else {
            agentsBean.stopAgent(aid);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
