package com.ftn.informatika.agents.web_client.service;

/**
 * @author - Srđan Milaković
 */
public class ErrorObject {
    private String message;

    public ErrorObject() {
    }

    public ErrorObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
