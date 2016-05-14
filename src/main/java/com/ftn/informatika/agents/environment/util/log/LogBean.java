package com.ftn.informatika.agents.environment.util.log;

import javax.ejb.Singleton;

@Singleton
public class LogBean implements LogLocal {
    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void error(String message) {
        System.err.println(message);
    }
}