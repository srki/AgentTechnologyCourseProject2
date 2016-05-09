package com.ftn.informatika.agents.web_client.util;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class LogsREST implements LogsEndpointREST {
    @Override
    public List<String> getLogs(Long last) {
        return null;
    }
}
