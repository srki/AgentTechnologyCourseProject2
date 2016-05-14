package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.clustering.http.NodesRequester;
import com.ftn.informatika.agents.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.service.http.AgentsManagementRequester;
import com.ftn.informatika.agents.environment.service.http.AgentsRequester;
import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.AliasNotExistsException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collections;
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
    @EJB
    private AgentsLocal agentsBean;

    @Override
    public void registerNodes(List<AgentCenter> agentCenters) throws AliasExistsException {
        if (configurationBean.isMaster()) {
            if (agentCenters.size() != 1) {
                System.err.println("AgentCenter list has size: " + agentCenters.size());
                return;
            }

            AgentCenter newAgentCenter = agentCenters.get(0);
            AgentsRequester agentsRequester = new AgentsRequester(newAgentCenter.getAddress());

            List<AgentType> agentTypes = agentsRequester.getClasses();

            try {
                for (AgentCenter agentCenter : nodesDbBean.getNodes()) {
                    if (configurationBean.getAlias().equals(agentCenter.getAlias())) {
                        continue;
                    }
                    new NodesRequester(agentCenter.getAddress()).addNodes(Collections.singletonList(newAgentCenter));
                    new AgentsManagementRequester(agentCenter.getAddress()).addClasses(newAgentCenter, agentTypes);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            new NodesRequester(newAgentCenter.getAddress()).addNodes(nodesDbBean.getNodes());

            AgentsManagementRequester requester = new AgentsManagementRequester(newAgentCenter.getAddress());
            requester.addClasses(agentsBean.getAllClasses());
            requester.addRunning(agentsBean.getAllRunningAgents());

            nodesDbBean.addNode(newAgentCenter);
            System.out.println(newAgentCenter.getAlias() + " registered to " + configurationBean.getAlias());
        } else {
            agentCenters.forEach(agentCenter -> {
                try {
                    nodesDbBean.addNode(agentCenter);
                    System.out.println(agentCenter.getAlias() + " registered to " + configurationBean.getAlias());
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
