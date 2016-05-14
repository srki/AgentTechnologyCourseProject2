package com.ftn.informatika.agents.config.startup;

import com.ftn.informatika.agents.clustering.NodesDbLocal;
import com.ftn.informatika.agents.clustering.http.NodesRequester;
import com.ftn.informatika.agents.config.AgentsReader;
import com.ftn.informatika.agents.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.model.AgentCenter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

/**
 * @author - Srđan Milaković
 */
@Startup
@Singleton
public class StartupBeanLocal implements StartupLocal {
    private static final int DEFAULT_PORT = 8080;
    private static final String MASTER_ADDRESS_KEY = "master";
    private static final String LOCAL_ADDRESS_KEY = "local";
    private static final String ALIAS_KEY = "alias";
    private static final String PORT_OFFSET_KEY = "jboss.socket.binding.port-offset";

    @EJB
    private ConfigurationLocal configurationDbBean;
    @EJB
    private NodesDbLocal nodesDbBean;
    @EJB
    private AgentsLocal agentsBean;

    @PostConstruct
    public void postConstruct() {
        String masterAddress = System.getProperty(MASTER_ADDRESS_KEY);
        if (masterAddress == null) {
            System.out.println("This is master node!");
        } else {
            System.out.println("This is slave node! Master node is at: " + masterAddress);
            configurationDbBean.setMasterAddress(masterAddress);
        }

        // Get local IP address
        String localAddress = System.getProperty(LOCAL_ADDRESS_KEY);
        if (localAddress == null) {
            try {
                String portOffset = System.getProperty(PORT_OFFSET_KEY);
                int port = DEFAULT_PORT + (portOffset != null ? Integer.parseInt(portOffset) : 0);
                InetAddress local = InetAddress.getLocalHost();
                localAddress = local.getHostAddress() + ":" + port;

            } catch (UnknownHostException e) {
                System.err.println("Can't read Local IPv4 Address.");
            } catch (NumberFormatException e) {
                System.err.println("Cannot parse port offset.");
            }
        }
        System.out.println("Local IPv4 Address: " + localAddress);

        // Get alias
        String hostName = System.getProperty(ALIAS_KEY);
        if (hostName == null) {
            try {
                InetAddress local = InetAddress.getLocalHost();
                hostName = local.getHostName();
            } catch (UnknownHostException e) {
                System.err.println("Can't read Host Name.");
            }
        }
        System.out.println("Host Name: " + hostName);

        AgentCenter agentCenter = new AgentCenter(localAddress, hostName);
        configurationDbBean.setAgentCenter(agentCenter);
        agentsBean.addClasses(AgentsReader.getAgentsList());


        if (!configurationDbBean.isMaster()) {
            new NodesRequester(masterAddress).addNodes(Collections.singletonList(agentCenter));
        }
    }


    @PreDestroy
    public void preDestroy() {
        // TODO: Unregister if not master
    }

}
