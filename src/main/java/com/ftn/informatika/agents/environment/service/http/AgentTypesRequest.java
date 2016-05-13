package com.ftn.informatika.agents.environment.service.http;

import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class AgentTypesRequest {
    private AgentCenter agentCenter;
    private List<AgentType> agentTypes;

    public AgentTypesRequest() {
    }

    public AgentTypesRequest(AgentCenter agentCenter, List<AgentType> agentTypes) {
        this.agentCenter = agentCenter;
        this.agentTypes = agentTypes;
    }

    public AgentCenter getAgentCenter() {
        return agentCenter;
    }

    public void setAgentCenter(AgentCenter agentCenter) {
        this.agentCenter = agentCenter;
    }

    public List<AgentType> getAgentTypes() {
        return agentTypes;
    }

    public void setAgentTypes(List<AgentType> agentTypes) {
        this.agentTypes = agentTypes;
    }
}
