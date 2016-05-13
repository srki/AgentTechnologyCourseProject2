package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.AliasNotExistsException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Dragan Vidakovic
 * @author Srđan Milaković
 */
@Stateless
public class NodesManagementBeanLocal implements NodesManagementLocal {
    @EJB
    private NodesDbLocal nodesDbBean;
    @EJB
    private ConfigurationLocal configurationBean;

    @Override
    public void registerNodes(List<AgentCenter> agentCenters) throws AliasExistsException {
        if (configurationBean.isMaster()) {
            if (agentCenters.size() != 1) {
                System.err.println("AgentCenter list has size: " + agentCenters.size());
                return;
            }

            AgentCenter agentCenter = agentCenters.get(0);
            nodesDbBean.addNode(agentCenter);

        } else {
            agentCenters.forEach(ac -> {
                try {
                    nodesDbBean.addNode(ac);
                } catch (AliasExistsException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    @Override
    public void removeNode(String alias) throws AliasNotExistsException {

    }
}
