package com.ftn.informatika.agents.environment.model;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author - Srđan Milaković
 */
public class AID implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AID aid = (AID) o;

        return name != null ? name.equals(aid.name) : aid.name == null
                && (host != null ? host.equals(aid.host) : aid.host == null
                && (type != null ? type.equals(aid.type) : aid.type == null));

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
