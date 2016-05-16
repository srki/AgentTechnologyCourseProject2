package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

/**
 * ZeroMQ Request-Reply broker.
 *
 * @author Dragan Vidakovic
 */
public class RRBroker {

    public static void main(String[] args)
    {
        Context context = ZMQ.context(1);

        Socket fronted = context.socket(ZMQ.ROUTER);
        Socket backend = context.socket(ZMQ.DEALER);
        fronted.bind("tcp://*:5559");
        backend.bind("tcp://*:5560");

        System.out.println("launch and connect broker.");

        // initialize poll set
        Poller items = new Poller(2);
        items.register(fronted, Poller.POLLIN);
        items.register(backend, Poller.POLLIN);

        boolean more = false;
        byte[] message;

        // switch messages between sockets
        while (!Thread.currentThread().isInterrupted())
        {
            items.poll();

            if (items.pollin(0))
            {
                while (true)
                {
                    //receive message
                    message = fronted.recv(0);
                    more = fronted.hasReceiveMore();

                    // broker it
                    backend.send(message, more ? ZMQ.SNDMORE : 0);
                    if (!more)
                    {
                        break;
                    }
                }
            }
        }
        // clean up
        fronted.close();
        backend.close();
        context.term();
    }

}
