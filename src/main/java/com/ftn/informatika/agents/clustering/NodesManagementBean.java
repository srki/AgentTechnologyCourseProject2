package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.clustering.exception.AliasExistsException;
import com.ftn.informatika.agents.clustering.service.http.NodesRequester;
import com.ftn.informatika.agents.clustering.startup.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.agents.AgentClassesLocal;
import com.ftn.informatika.agents.environment.agents.RunningAgentsLocal;
import com.ftn.informatika.agents.environment.agents.service.http.AgentsManagementRequester;
import com.ftn.informatika.agents.environment.agents.service.http.AgentsRequester;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;

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
    private AgentClassesLocal agentClassesBean;
    @EJB
    private RunningAgentsLocal runningAgentsBean;

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
                for (AgentCenter agentCenter : nodesDbBean.getAllNodes()) {
                    if (configurationBean.getAlias().equals(agentCenter.getAlias())) {
                        continue;
                    }
                    new NodesRequester(agentCenter.getAddress()).addNodes(Collections.singletonList(newAgentCenter));
                    new AgentsManagementRequester(agentCenter.getAddress()).addClasses(newAgentCenter, agentTypes);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            agentClassesBean.addClasses(newAgentCenter.getAlias(), agentTypes);
            nodesDbBean.addNode(newAgentCenter);

            // Deliver all nodes to the new node
            new NodesRequester(newAgentCenter.getAddress()).addNodes(nodesDbBean.getAllNodes());

            // Deliver all classes and running agents to the new node
            AgentsManagementRequester requester = new AgentsManagementRequester(newAgentCenter.getAddress());
            requester.addClasses(agentClassesBean.getAllClasses());
            requester.addRunning(runningAgentsBean.getAllRunningAgents());

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
        if (!nodesDbBean.containsRemoteNode(alias)) {
            return;
        }

        System.out.println(alias + " removed from " + configurationBean.getAlias());
        nodesDbBean.removeNode(alias);
        agentClassesBean.removeClasses(alias);
        runningAgentsBean.removeRunningAgentsFromNode(alias);

        if (configurationBean.isMaster()) {
            nodesDbBean.getAllNodes().forEach(node -> {
                if (!configurationBean.getAgentCenter().equals(node)) {
                    new NodesRequester(node.getAddress()).removeNode(alias);
                }
            });
        }

    }
}
