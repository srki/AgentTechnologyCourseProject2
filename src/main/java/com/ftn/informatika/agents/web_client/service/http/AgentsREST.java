package com.ftn.informatika.agents.web_client.service.http;

import com.ftn.informatika.agents.model.AID;
import com.ftn.informatika.agents.model.AgentType;
import com.ftn.informatika.agents.web_client.beans.AgentsLocal;
import com.ftn.informatika.agents.web_client.service.http.endpoint_interface.AgentsEndpointREST;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class AgentsREST implements AgentsEndpointREST {

    @EJB
    private AgentsLocal agentsBean;

    @Override
    public List<AgentType> getClasses() {
        return agentsBean.getClasses();
    }

    @Override
    public List<AID> getRunning() {
        return agentsBean.getRunningAgents();
    }

    @Override
    public AID runAgent(AgentType type, String name) {
        return agentsBean.runAgent(type, name);
    }

    @Override
    public AID stopAgent(AID aid) {
        return agentsBean.stopAgent(aid);
    }
}
