package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.clustering.config.ConfigurationLocal;
import com.ftn.informatika.agents.clustering.http.NodesRequester;
import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author - Srđan Milaković
 */
@Singleton
public class HeartbeatBean {

    @EJB
    private NodesDbLocal nodesDbBean;
    @EJB
    private ConfigurationLocal configurationBean;

    private Set<AgentCenter> preDestroy = new HashSet<>();

    @Schedule(hour = "*", minute = "*", second = "*/15", info = "every 45th second")
    public void isAlive() {
        System.out.println(getClass().getName());
        List<AgentCenter> destroy = new ArrayList<>();

        // Check if nodes are alive
        nodesDbBean.getNodes().forEach(node -> {
            if (configurationBean.getAgentCenter().equals(node)) {
                return;
            }

            try {
                new NodesRequester(node.getAddress()).isAlive();
                preDestroy.remove(node);
            } catch (Exception e) {
                if (preDestroy.contains(node)) {
                    preDestroy.remove(node);
                    destroy.add(node);
                    System.err.println("Removing node " + node.getAlias());
                } else {
                    System.err.println("First warning for " + node.getAlias());
                    preDestroy.add(node);
                }
            }
        });

        // Notify all nodes
        destroy.forEach(node -> nodesDbBean.removeNode(node.getAlias()));
        nodesDbBean.getNodes().forEach(node -> {
            NodesRequester requester = new NodesRequester(node.getAddress());
            destroy.forEach(removeNode -> {
                try {
                    requester.removeNode(removeNode.getAlias());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
