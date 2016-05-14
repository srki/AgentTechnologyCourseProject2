package com.ftn.informatika.agents.environment.util.log;

import com.ftn.informatika.agents.web_client.util.StreamLocal;
import com.ftn.informatika.agents.web_client.util.StreamMessage;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class LogBean implements LogLocal {

    @EJB
    private StreamLocal streamBean;

    @Override
    public void info(String message) {
        System.out.println(message);
        streamBean.sendMessage(new StreamMessage(StreamMessage.MessageType.LOG, message));
    }

    @Override
    public void error(String message) {
        System.err.println(message);
        streamBean.sendMessage(new StreamMessage(StreamMessage.MessageType.LOG, message));
    }
}