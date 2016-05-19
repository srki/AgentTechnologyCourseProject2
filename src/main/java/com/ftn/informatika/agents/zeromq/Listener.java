package com.ftn.informatika.agents.zeromq;

import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZThread;

/**
 * The listener receives all messages flowing through the proxy, on its pipe.
 * The pipe is a pair of ZMQ_PAIR sockets that connect attached child threads.
 *
 * @author Dragan Vidakovic
 */
public class Listener implements ZThread.IAttachedRunnable {

    @Override
    public void run(Object[] objects, ZContext zContext, Socket socket) {

        // print everything that arrives on pipe
        while (true) {
            ZFrame frame = ZFrame.recvFrame(socket);
            if (frame == null)
                break; //Interrupted
            frame.print(null);
            frame.destroy();
        }

    }
}
