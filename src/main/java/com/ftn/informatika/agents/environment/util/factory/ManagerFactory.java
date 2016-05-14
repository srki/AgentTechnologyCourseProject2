package com.ftn.informatika.agents.environment.util.factory;

import com.ftn.informatika.agents.environment.AgentsBean;
import com.ftn.informatika.agents.environment.AgentsRemote;
import com.ftn.informatika.agents.environment.MessagesBean;
import com.ftn.informatika.agents.environment.MessagesRemote;
import com.ftn.informatika.agents.environment.util.log.LogBean;
import com.ftn.informatika.agents.environment.util.log.LogRemote;

import javax.naming.NamingException;

/**
 * @author - Srđan Milaković
 */
public class ManagerFactory {
    private static final String PREFIX = "ejb:/agent_center/";
    private static final String LOG_MANAGER_NAME = PREFIX + LogBean.class.getSimpleName() + "!" + LogRemote.class.getName();
    private static final String AGENT_MANAGER_NAME = PREFIX + AgentsBean.class.getSimpleName() + "!" + AgentsRemote.class.getName();
    private static final String MESSAGES_MANAGER_NAME = PREFIX + MessagesBean.class.getSimpleName() + "!" + MessagesRemote.class.getName();

    public static LogRemote getLogRemote() {
        return contextLookup(LOG_MANAGER_NAME, LogRemote.class);
    }

    public static AgentsRemote getAgentManager() {
        return contextLookup(AGENT_MANAGER_NAME, AgentsRemote.class);
    }

    public static MessagesRemote getMessagesManager() {
        return contextLookup(MESSAGES_MANAGER_NAME, MessagesRemote.class);
    }

    @SuppressWarnings("unchecked")
    private static <T> T contextLookup(String name, Class<T> c) {
        try {
            return (T) ContextSingleton.getContext().lookup(name);
        } catch (NamingException ex) {
            throw new IllegalStateException("Failed to lookup " + name, ex);
        }
    }
}
