package com.ftn.informatika.agents.environment.agents.manager;

import com.ftn.informatika.agents.clustering.NodesDbLocal;
import com.ftn.informatika.agents.environment.agents.AgentClassesLocal;
import com.ftn.informatika.agents.environment.agents.RunningAgentsLocal;
import com.ftn.informatika.agents.environment.agents.service.http.AgentsManagementRequester;
import com.ftn.informatika.agents.environment.agents.service.http.AgentsRequester;
import com.ftn.informatika.agents.environment.exceptions.NameAlreadyExistsException;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collections;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class AgentManagerBean implements AgentManagerLocal, AgentManagerRemote {

    @EJB
    private AgentClassesLocal agentClassesBean;
    @EJB
    private RunningAgentsLocal runningAgentsBean;
    @EJB
    private NodesDbLocal nodesDbBean;

    @Override
    public AID runAgent(AgentType agentType, String name) throws NameAlreadyExistsException {
        Agent agent = agentType.createInstance(name, nodesDbBean.getLocal());

        if (agent == null) {
            String alias = agentClassesBean.findAgentCenter(agentType);
            return new AgentsRequester(nodesDbBean.getRemoteNode(alias).getAddress()).runAgent(agentType, name);
        }

        if (runningAgentsBean.containsAgent(agent.getAid())) {
            throw new NameAlreadyExistsException();
        }

        runningAgentsBean.addLocalAgent(agent);

        // TODO: extract to bean
        nodesDbBean.getRemoteNodes().forEach(node ->
                new AgentsManagementRequester(node.getAddress()).addRunning(Collections.singletonList(agent.getAid()))
        );

        return agent.getAid();
    }

    @Override
    public void stopAgent(AID aid) {
        runningAgentsBean.removeLocalAgent(aid);

        // TODO: extract to bean
        nodesDbBean.getRemoteNodes().forEach(node ->
                new AgentsManagementRequester(node.getAddress()).removeRunning(Collections.singletonList(aid))
        );
    }

    @Override
    public Agent getAgent(AID aid) {
        return runningAgentsBean.getLocalAgent(aid);
    }


}
