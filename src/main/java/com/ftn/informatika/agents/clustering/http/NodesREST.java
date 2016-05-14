package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.clustering.NodesManagementLocal;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.exception.AliasExistsException;

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
    public Object deleteNode(String alias) {
        return null;
    }
}
