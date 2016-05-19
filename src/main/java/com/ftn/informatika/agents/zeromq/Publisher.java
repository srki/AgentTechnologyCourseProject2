package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZThread;

import java.util.Random;

/**
 * The publisher sends random messages starting with A - J
 *
 * @author Dragan Vidakovic
 */
public class Publisher implements ZThread.IAttachedRunnable {
    @Override
    public void run(Object[] objects, ZContext zContext, Socket socket) {
        Socket publisher = zContext.createSocket(ZMQ.PUB);
        publisher.bind("tcp://*:6000");
        Random rand = new Random(System.currentTimeMillis());

        while (!Thread.currentThread().isInterrupted()) {
            String string = String.format("%c-%05d", 'A' + rand.nextInt(10), rand.nextInt(100000));
            if (!publisher.send(string))
                break;              //  Interrupted
            try {
                Thread.sleep(100);     //  Wait for 1/10th second
            } catch (InterruptedException e) {
            }
        }
        zContext.destroySocket(publisher);
    }
}
