package com.ftn.informatika.agents.environment.model;

import java.io.Serializable;

/**
 * @author - Srđan Milaković
 */
public class AgentCenter implements Serializable {
    private String address;
    private String alias;

    public AgentCenter() {
    }

    public AgentCenter(String address, String alias) {
        this.address = address;
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentCenter that = (AgentCenter) o;

        return alias != null ? alias.equals(that.alias) : that.alias == null;
    }

    @Override
    public int hashCode() {
        return alias != null ? alias.hashCode() : 0;
    }
}

