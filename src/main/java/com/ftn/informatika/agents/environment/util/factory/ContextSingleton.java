package com.ftn.informatika.agents.environment.util.factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * @author - Srđan Milaković
 */

public abstract class ContextSingleton {
    private static Context context;

    private static Context initContext() {
        try {
            Hashtable<String, Object> jndiProps = new Hashtable<>();
            jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            return new InitialContext(jndiProps);
        } catch (NamingException e) {
            System.err.println("Context initialization error." + e.getMessage());
            return null;
        }
    }

    public static Context getContext() {
        if (context == null) {
            context = initContext();
        }
        return context;
    }
}