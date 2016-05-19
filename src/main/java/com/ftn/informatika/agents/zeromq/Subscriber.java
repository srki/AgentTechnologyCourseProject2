package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZThread.IAttachedRunnable;

/**
 * The subscriber thread requests messages starting with A and B, then reads and counts incoming messages.
 *
 * @author Dragan Vidakovic
 */
public class Subscriber implements IAttachedRunnable {
    @Override
    public void run(Object[] objects, ZContext zContext, Socket socket) {
        // subscribe to A and B
        Socket subscriber = zContext.createSocket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:6001");
        subscriber.subscribe("A".getBytes());
        subscriber.subscribe("B".getBytes());

        // example for reading 5 messages
        int count = 0;
        while (count < 5) {
            String string = subscriber.recvStr();
            System.out.println("Subscriber read: " + string);
            if (string == null)
                break; //Interrupted
            count++;
        }
        zContext.destroySocket(subscriber);

    }
}
