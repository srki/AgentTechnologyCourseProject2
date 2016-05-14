package com.ftn.informatika.agents.web_client.util;

import java.util.Date;

/**
 * @author - Srđan Milaković
 */
public class StreamMessage {
    private MessageType type;
    private Object content;
    private long date;
    public StreamMessage() {
        this.date = new Date().getTime();
    }

    public StreamMessage(MessageType type, Object content) {
        this();
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public enum MessageType {
        LOG,
        CLASSES,
        AGENTS
    }
}
