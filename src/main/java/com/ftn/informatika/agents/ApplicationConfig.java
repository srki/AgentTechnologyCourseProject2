package com.ftn.informatika.agents;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author - Srđan Milaković
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    public static final String APPLICATION_URL = "http://%s/api";
}

