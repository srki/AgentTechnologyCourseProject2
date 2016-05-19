package com.ftn.informatika.agents.clustering.zeromq;

import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZThread.IAttachedRunnable;

import javax.ejb.Singleton;

/**
 * @author Dragan Vidakovic
 */

@Singleton
public class ListenerBean implements ListenerLocal, IAttachedRunnable {
    @Override
    public void run(Object[] objects, ZContext zContext, ZMQ.Socket socket) {
        while (true) {
            ZFrame frame = ZFrame.recvFrame(socket);
            if (frame == null)
                break; //Interrupted
            frame.print(null);
            frame.destroy();
        }
    }
}
