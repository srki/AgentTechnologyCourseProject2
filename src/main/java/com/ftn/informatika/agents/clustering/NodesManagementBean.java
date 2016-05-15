package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.clustering.config.ConfigurationLocal;
import com.ftn.informatika.agents.clustering.exception.AliasExistsException;
import com.ftn.informatika.agents.clustering.http.NodesRequester;
import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.service.http.AgentsManagementRequester;
import com.ftn.informatika.agents.environment.service.http.AgentsRequester;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

/**
 * @author Dragan Vidakovic
 * @author Srđan Milaković
 */
@Stateless
public class NodesManagementBean implements NodesManagementLocal {
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

            // Get classes from new node
            AgentCenter newAgentCenter = agentCenters.get(0);
            List<AgentType> agentTypes = new AgentsRequester(newAgentCenter.getAddress()).getClasses();

            // Send new classes to other nodes
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

            nodesDbBean.addNode(newAgentCenter);
            agentsBean.addClasses(newAgentCenter.getAlias(), agentTypes);

            // Deliver all nodes to the new node
            new NodesRequester(newAgentCenter.getAddress()).addNodes(nodesDbBean.getNodes());

            // Deliver all classes and running agents to the new node
            AgentsManagementRequester requester = new AgentsManagementRequester(newAgentCenter.getAddress());
            requester.addClasses(agentsBean.getClasses());
            requester.addRunning(agentsBean.getRunningAgents());

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
    public void removeNode(String alias) {
        System.out.println(alias + " removed from " + configurationBean.getAlias());
        nodesDbBean.removeNode(alias);
        agentsBean.removeClasses(alias, null);
        agentsBean.removeRunningAgentsFromNode(alias);

        if (configurationBean.isMaster()) {
            nodesDbBean.getNodes().forEach(node -> {
                if (!configurationBean.getAgentCenter().equals(node)) {
                    new NodesRequester(node.getAddress()).removeNode(alias);
                }
            });
        }

    }
}
