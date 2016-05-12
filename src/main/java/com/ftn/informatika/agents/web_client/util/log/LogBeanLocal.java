package com.ftn.informatika.agents.web_client.util.log;

import javax.ejb.Singleton;

@Singleton
public class LogBeanLocal implements LogLocal {
    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void error(String message) {
        System.err.println(message);
    }
}