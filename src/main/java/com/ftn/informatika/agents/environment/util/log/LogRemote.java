package com.ftn.informatika.agents.environment.util.log;

import javax.ejb.Remote;

/**
 * @author - Srđan Milaković
 */
@Remote
public interface LogRemote {
    void info(String message);

    void error(String message);
}
