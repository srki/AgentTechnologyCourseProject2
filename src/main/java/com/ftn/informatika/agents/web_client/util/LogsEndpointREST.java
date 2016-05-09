package com.ftn.informatika.agents.web_client.util;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/logs")
public interface LogsEndpointREST {
    @GET
    List<String> getLogs(@QueryParam("last") Long last);
}
