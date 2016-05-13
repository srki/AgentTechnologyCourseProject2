package com.ftn.informatika.agents.clustering.http;

import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class NodesREST implements NodesEndpointREST {
    @Override
    public Object isAlive() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Object addNodes(List<AgentCenter> agentCenters) {
        return null;
    }

    @Override
    public Object deleteNode(String alias) {
        return null;
    }
}
