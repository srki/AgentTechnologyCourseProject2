package com.ftn.informatika.agents.web_client.util.stream;

/**
 * @author - Srđan Milaković
 */
public class StreamMessage {
    private String type;
    private Object content;

    public StreamMessage() {
    }

    public StreamMessage(String type, Object content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
