package com.ftn.informatika.agents.environment.model;

import com.google.gson.Gson;

/**
 * @author - Srđan Milaković
 */
public class AID {
    private String name;
    private AgentCenter host;
    private AgentType type;

    public AID() {
    }

    public AID(String string) {
        AID aid = new Gson().fromJson(string, AID.class);
        this.name = aid.getName();
        this.host = aid.getHost();
        this.type = aid.getType();
    }

    public AID(String name, AgentCenter host, AgentType type) {
        this.name = name;
        this.host = host;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgentCenter getHost() {
        return host;
    }

    public void setHost(AgentCenter host) {
        this.host = host;
    }

    public AgentType getType() {
        return type;
    }

    public void setType(AgentType type) {
        this.type = type;
    }
}
