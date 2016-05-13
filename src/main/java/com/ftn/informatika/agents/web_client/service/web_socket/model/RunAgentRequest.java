package com.ftn.informatika.agents.web_client.service.web_socket.model;

import com.ftn.informatika.agents.environment.model.AgentType;

/**
 * @author - Srđan Milaković
 */
public class RunAgentRequest {
    private AgentType agentType;
    private String name;

    public RunAgentRequest() {
    }

    public RunAgentRequest(AgentType agentType, String name) {
        this.agentType = agentType;
        this.name = name;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
