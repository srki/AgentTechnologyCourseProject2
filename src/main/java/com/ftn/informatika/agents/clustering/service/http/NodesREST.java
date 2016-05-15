package com.ftn.informatika.agents.clustering.service.http;

import com.ftn.informatika.agents.clustering.NodesDbLocal;
import com.ftn.informatika.agents.clustering.NodesManagementLocal;
import com.ftn.informatika.agents.clustering.exception.AliasExistsException;
import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class NodesREST implements NodesEndpointREST {

    @EJB
    private NodesDbLocal nodesDbBean;
    @EJB
    private NodesManagementLocal nodesManagementBean;

    @Override
    public Object isAlive() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object addNodes(List<AgentCenter> agentCenters) {
        try {
            nodesManagementBean.registerNodes(agentCenters);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (AliasExistsException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Object removeNode(String alias) {
        nodesManagementBean.removeNode(alias);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public List<AgentCenter> getAgentCenters() {
        return nodesDbBean.getAllNodes();
    }
}
