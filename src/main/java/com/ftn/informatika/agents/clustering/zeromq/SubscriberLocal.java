package com.ftn.informatika.agents.clustering.zeromq;

import javax.ejb.Local;

/**
 * @author Dragan Vidakovic
 */

@Local
public interface SubscriberLocal
{
    String readMessage();
}
