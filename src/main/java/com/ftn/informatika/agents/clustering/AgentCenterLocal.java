package com.ftn.informatika.agents.clustering;

import com.ftn.informatika.agents.exception.AliasNotExistsException;
import com.ftn.informatika.agents.exception.NodeExistsException;

import javax.ejb.Local;

/**
 * @author Dragan Vidakovic
 */
@Local
public interface AgentCenterLocal {

    void registerNode() throws NodeExistsException;

    void removeNode(String alias) throws AliasNotExistsException;


}
