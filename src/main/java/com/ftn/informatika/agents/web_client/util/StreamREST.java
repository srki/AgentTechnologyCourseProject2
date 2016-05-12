package com.ftn.informatika.agents.web_client.util;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class StreamREST implements StreamEndpointREST {
    @Override
    public List<String> getMessages(Long last) {
        System.out.println(last);
        return new ArrayList<>();
    }
}
