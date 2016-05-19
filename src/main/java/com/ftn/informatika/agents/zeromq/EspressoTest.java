package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZThread;

/**
 * Starts the subscriber and publisher,and then sets itself up as a listening proxy.
 * The listener runs as a child thread.
 *
 * @author Dragan Vidakovic
 */
public class EspressoTest {

    public static void main(String[] argv) {
        ZContext ctx = new ZContext();
        ZThread.fork(ctx, new Publisher());
        ZThread.fork(ctx, new Subscriber());

        Socket subscriber = ctx.createSocket(ZMQ.XSUB);
        subscriber.connect("tcp://localhost:6000");
        Socket publisher = ctx.createSocket(ZMQ.XPUB);
        publisher.bind("tcp://*:6001");
        Socket listener = ZThread.fork(ctx, new Listener());
        ZMQ.proxy(subscriber, publisher, listener);

        System.out.println("Interrupted");
        // tell attached threads to exit
        ctx.destroy();
    }

}
