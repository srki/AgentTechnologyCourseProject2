package com.ftn.informatika.agents.clustering.zeromq;

import javax.ejb.Local;

/**
 * @author Dragan Vidakovic
 */

@Local
public interface PublisherLocal {

     void sendMessage(String message);

}
