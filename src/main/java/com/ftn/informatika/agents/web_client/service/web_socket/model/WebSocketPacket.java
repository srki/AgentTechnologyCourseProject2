package com.ftn.informatika.agents.web_client.service.web_socket.model;

import com.google.gson.Gson;

/**
 * @author - Srđan Milaković
 */
public class WebSocketPacket {
    private Type type;
    private String data;
    private Boolean success;

    public WebSocketPacket() {
    }

    public WebSocketPacket(Type type, String data, Boolean success) {
        this.type = type;
        this.data = data;
        this.success = success;
    }

    public WebSocketPacket(Type type, Object data, Boolean success) {
        this(type, new Gson().toJson(data), success);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "WebSocketPacket{" +
                "type=" + type +
                ", data='" + data + '\'' +
                ", success=" + success +
                '}';
    }

    public enum Type {
        GET_CLASSES,
        GET_RUNNING,
        RUN_AGENT,
        STOP_AGENT,
        SEND_MESSAGE,
        GET_PERFORMATIVES
    }
}
