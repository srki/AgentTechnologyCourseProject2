package com.ftn.informatika.agents.clustering.zeromq;

import com.ftn.informatika.agents.clustering.startup.config.ConfigurationLocal;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZThread.IAttachedRunnable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 * @author Dragan Vidakovic
 */

@Singleton
public class PublisherBean implements PublisherLocal, IAttachedRunnable {
    @EJB
    private ConfigurationLocal configurationBean;

    private Socket publisher = null;
    private ZContext context = null;

    @PostConstruct
    @Override
    public void run(Object[] objects, ZContext zContext, Socket socket) {
        if (configurationBean.isMaster()) {
            context = zContext;
            publisher = context.createSocket(ZMQ.PUB);
            String masterAddress = configurationBean.getMasterAddress().split(":")[0];
            publisher.bind("tcp://" + masterAddress + ":6000");
            System.out.println("Launching Publisher...");
        }

    }

    @Override
    public void sendMessage(String message) {
        if (publisher != null)
            publisher.send(message);
    }

    @PreDestroy
    public void preDestroy() {
        if (context != null)
            context.destroySocket(publisher);
    }

}
