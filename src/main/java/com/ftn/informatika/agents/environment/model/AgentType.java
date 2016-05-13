package com.ftn.informatika.agents.environment.model;

import com.google.gson.Gson;

/**
 * @author - Srđan Milaković
 */
public class AgentType {
    private String name;
    private String module;

    public AgentType() {
    }

    public AgentType(String string) {
        AgentType agentType = new Gson().fromJson(string, AgentType.class);
        name = agentType.getName();
        module = agentType.getModule();
    }

    public AgentType(String name, String module) {
        this.name = name;
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
