package com.ftn.informatika.agents.clustering.zeromq;

import com.ftn.informatika.agents.clustering.NodesDbLocal;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZThread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Dragan Vidakovic
 */

@Stateless
public class ZMQManagementBean implements ZMQManagementLocal {

    @EJB
    private NodesDbLocal nodesDbBean;

    private ZContext context = null;

    @PostConstruct
    public void postConstruct() {
        context = new ZContext();

        ZThread.fork(context, new PublisherBean());
        ZThread.fork(context, new SubscriberBean());

        Socket subscriber = context.createSocket(ZMQ.XSUB);
        subscriber.connect("tcp://*:6000");
        Socket publisher = context.createSocket(ZMQ.XPUB);
        int offset = 6001 + nodesDbBean.getAllNodes().size();
        publisher.bind("tcp://*:" + offset);
        Socket listener = ZThread.fork(context, new ListenerBean());
        ZMQ.proxy(subscriber, publisher, listener);
    }

    @PreDestroy
    public void preDestroy() {
        if (context != null)
            context.destroy();
    }

}
