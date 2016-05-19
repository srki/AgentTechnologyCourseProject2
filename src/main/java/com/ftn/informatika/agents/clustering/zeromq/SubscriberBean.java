package com.ftn.informatika.agents.clustering.zeromq;

import com.ftn.informatika.agents.clustering.NodesDbLocal;
import com.ftn.informatika.agents.clustering.startup.config.ConfigurationLocal;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.*;
import org.zeromq.ZThread.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * @author Dragan Vidakovic
 */

@Stateful
public class SubscriberBean implements SubscriberLocal, IAttachedRunnable {
    @EJB
    private ConfigurationLocal configurationBean;

    @EJB
    private NodesDbLocal nodesDbBean;

    private Socket subscriber = null;
    private ZContext context = null;

    @PostConstruct
    @Override
    public void run(Object[] objects, ZContext zContext, Socket socket) {
        if (!configurationBean.isMaster()) {
            context = zContext;
            subscriber = context.createSocket(ZMQ.SUB);
            String localAddress = configurationBean.getLocalAddress().split(":")[0];
            int offset = 6001 + nodesDbBean.getAllNodes().size();
            subscriber.connect("tcp://" + localAddress + ":" + offset);
            subscriber.subscribe("MASTER".getBytes());
            System.out.println("Launching Subscriber...");
        }
    }

    @Override
    public String readMessage() {
        String retVal = null;

        if (subscriber != null)
            retVal = subscriber.recvStr();

        return retVal;
    }

    @PreDestroy
    public void preDestroy() {
        if (context != null)
            context.destroySocket(subscriber);
    }
}
