package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.AgentCenter;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.service.http.AgentsManagementRequester;
import com.ftn.informatika.agents.environment.service.http.AgentsRequester;
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
            List<AID> agents = agentsRequester.getRunning();


            try {
                for (AgentCenter agentCenter : nodesDbBean.getNodes()) {
                    AgentsManagementRequester requester = new AgentsManagementRequester(agentCenter.getAddress());
                    requester.addClasses(newAgentCenter, agentTypes);
                    requester.addRunning(agents);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            AgentsManagementRequester requester = new AgentsManagementRequester(newAgentCenter.getAddress());
            nodesDbBean.addNode(newAgentCenter);

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
