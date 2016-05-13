package com.ftn.informatika.agents.config;

import com.ftn.informatika.agents.model.AgentCenter;

import javax.ejb.*;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class ConfigurationDbBeanLocal implements ConfigurationDbLocal {
    private String masterAddress;
    private AgentCenter agentCenter;

    @Lock(LockType.READ)
    @Override
    public String getMasterAddress() {
        return masterAddress;
    }

    @Lock(LockType.WRITE)
    @Override
    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    @Lock(LockType.READ)
    @Override
    public AgentCenter getAgentCenter() {
        return agentCenter;
    }

    @Lock(LockType.WRITE)
    @Override
    public void setAgentCenter(AgentCenter agentCenter) {
        this.agentCenter = agentCenter;
    }

    @Lock(LockType.READ)
    @Override
    public AgentCenter cloneAgentCenter() {
        return new AgentCenter(agentCenter.getAddress(), agentCenter.getAlias());
    }

    @Lock(LockType.READ)
    @Override
    public String getLocalAddress() {
        return agentCenter.getAddress();
    }

    @Lock(LockType.READ)
    @Override
    public String getAlias() {
        return agentCenter.getAlias();
    }

    @Lock(LockType.READ)
    @Override
    public boolean isMaster() {
        return masterAddress == null;
    }
}
