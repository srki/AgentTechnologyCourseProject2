package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.environment.model.AID;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class AgentsManagementREST implements AgentsManagementEndpointREST {
    @Override
    public Object addClasses(AgentTypesRequest request) {
        return null;
    }

    @Override
    public Object addRunning(List<AID> agents) {
        return null;
    }

    @Override
    public Object removeClasses(AgentTypesRequest request) {
        return null;
    }

    @Override
    public Object removeRunning(List<AID> agents) {
        return null;
    }
}
