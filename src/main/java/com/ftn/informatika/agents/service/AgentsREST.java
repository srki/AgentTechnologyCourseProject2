package com.ftn.informatika.agents.service;

import com.ftn.informatika.agents.model.AID;
import com.ftn.informatika.agents.model.AgentType;
import com.ftn.informatika.agents.service.endpoints.AgentsEndpointREST;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class AgentsREST implements AgentsEndpointREST {

    @Override
    public List<AgentType> getClasses() {
        return null;
    }

    @Override
    public List<AID> getRunning() {
        return null;
    }

    @Override
    public AID runAgent(AgentType type, String name) {
        return null;
    }

    @Override
    public AID stopAgent(AID aid) {
        return null;
    }
}
