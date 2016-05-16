package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * ZeroMQ Request-Reply service.
 * Connects REP socket to tcp://*:5560
 * Expects "Hello" from client, replies with "World"
 *
 * @author Dragan Vidakovic
 */
public class RRServer {

    public static void main(String[] args)
    {
        Context context = ZMQ.context(1);

        // socket to talk to clients
        Socket responder = context.socket(ZMQ.REP);
        responder.connect("tcp://localhost:5560");

        System.out.println("launch and connect server");

        while (!Thread.currentThread().isInterrupted())
        {
            // wait for next request from client
            byte[] request = responder.recv(0);
            String string = new String(request);
            System.out.println("Received request: [" + string + "].");

            
            // do some 'work'
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }


            // send reply back to client
            responder.send("World".getBytes(), 0);

        }

        // clean up
        responder.close();
        context.term();

    }

}
