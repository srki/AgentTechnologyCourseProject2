package com.ftn.informatika.agents.web_client.util.stream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/stream")
public interface StreamEndpointREST {
    @GET
    List<StreamMessage> getMessages(@QueryParam("last") Long last);

}
