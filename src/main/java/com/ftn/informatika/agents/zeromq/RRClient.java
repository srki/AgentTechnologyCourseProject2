package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * ZeroMQ Request-Reply client.
 *
 * Connects REQ socket to tcp://localhost:5559.
 * Sends "Hello" to server, expects "World" back.
 *
 * @author Dragan Vidakovic
 */

public class RRClient
{
    public static void main(String[] args)
    {
        Context context = ZMQ.context(1);

        // socket talk to server
        Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:5559");

        System.out.println("launch and connect client.");

        for (int request_nbr = 0; request_nbr < 10; request_nbr++)
        {
            requester.send("Hello", 0);
            String reply = requester.recvStr(0);
            System.out.println("Received reply " + request_nbr + " [" + reply + "]");
        }

        // clean up
        requester.close();
        context.term();

    }

}
