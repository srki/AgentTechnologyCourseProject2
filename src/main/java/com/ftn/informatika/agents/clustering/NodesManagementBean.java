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
            List<AgentType> agentTypes = getAgentClasses(newAgentCenter.getAddress());

            // Send new classes to other nodes
            for (AgentCenter agentCenter : nodesDbBean.getRemoteNodes()) {
                try {
                    new NodesRequester(agentCenter.getAddress()).addNodes(Collections.singletonList(newAgentCenter));
                    new AgentsManagementRequester(agentCenter.getAddress()).addClasses(newAgentCenter, agentTypes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            agentClassesBean.addClasses(newAgentCenter.getAlias(), agentTypes);
            nodesDbBean.addNode(newAgentCenter);

            try {
                sendNodesToNewNode(newAgentCenter);
                sendClassesToNewNode(newAgentCenter);
                sendRunningAgentsToNewNode(newAgentCenter);
            } catch (Exception e) {
                cleanUp(newAgentCenter.getAddress());
            }

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
            nodesDbBean.getRemoteNodes().forEach(node -> new NodesRequester(node.getAddress()).removeNode(alias));
        }
    }

    private List<AgentType> getAgentClasses(String address) {
        try {
            return new AgentsRequester(address).getClasses();
        } catch (Exception e) {
            System.err.println("Can not get classes from the new node. Error: " + e.getMessage() + " Sending again!");
            return new AgentsRequester(address).getClasses();
        }
    }

    private void sendNodesToNewNode(AgentCenter newAgentCenter) {
        try {
            new NodesRequester(newAgentCenter.getAddress()).addNodes(nodesDbBean.getAllNodes());
        } catch (Exception e) {
            System.err.println("Can not register nodes to the new node. Error: " + e.getMessage() + " Sending again!");
            new NodesRequester(newAgentCenter.getAddress()).addNodes(nodesDbBean.getAllNodes());
        }
    }

    private void sendClassesToNewNode(AgentCenter newAgentCenter) {
        AgentsManagementRequester requester = new AgentsManagementRequester(newAgentCenter.getAddress());
        try {
            requester.addClasses(agentClassesBean.getAllClasses());
        } catch (Exception e) {
            System.err.println("Can not send classes to the new node. Error: " + e.getMessage() + " Sending again!");
            requester.addClasses(agentClassesBean.getAllClasses());
        }
    }

    private void sendRunningAgentsToNewNode(AgentCenter newAgentCenter) {
        AgentsManagementRequester requester = new AgentsManagementRequester(newAgentCenter.getAddress());
        try {
            requester.addRunning(runningAgentsBean.getAllRunningAgents());
        } catch (Exception e) {
            System.err.println("Can not send running agents to the new node. Error: " + e.getMessage() + " Sending again!");
            requester.addRunning(runningAgentsBean.getAllRunningAgents());
        }
    }

    private void cleanUp(String alias) {
        agentClassesBean.removeClasses(alias);
        nodesDbBean.removeNode(alias);

        for (AgentCenter agentCenter : nodesDbBean.getRemoteNodes()) {
            try {
                new NodesRequester(agentCenter.getAddress()).removeNode(alias);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
