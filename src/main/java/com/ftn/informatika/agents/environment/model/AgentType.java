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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentType agentType = (AgentType) o;

        return name != null ? name.equals(agentType.name) : agentType.name == null
                && (module != null ? module.equals(agentType.module) : agentType.module == null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (module != null ? module.hashCode() : 0);
        return result;
    }
}
