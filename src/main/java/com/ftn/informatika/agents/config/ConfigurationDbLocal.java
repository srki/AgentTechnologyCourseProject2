package com.ftn.informatika.agents.config;

import com.ftn.informatika.agents.model.AgentCenter;

import javax.ejb.Local;

/**
 * @author - Srđan Milaković
 */
@Local
public interface ConfigurationDbLocal {
    String getMasterAddress();

    void setMasterAddress(String masterAddress);

    AgentCenter getAgentCenter();

    void setAgentCenter(AgentCenter agentCenter);

    AgentCenter cloneAgentCenter();

    String getLocalAddress();

    String getAlias();

    boolean isMaster();
}
