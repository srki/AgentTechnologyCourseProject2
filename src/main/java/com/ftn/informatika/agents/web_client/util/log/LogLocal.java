package com.ftn.informatika.agents.web_client.util.log;

import javax.ejb.Local;

/**
 * @author - Srđan Milaković
 */
@Local
public interface LogLocal {
    void info(String message);

    void error(String message);
}
