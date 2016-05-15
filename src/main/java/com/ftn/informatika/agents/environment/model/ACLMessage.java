package com.ftn.informatika.agents.environment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
public class ACLMessage implements Serializable {
    private Performative performative;
    private AID sender;
    private List<AID> receivers;
    private AID replyTo;
    private String content;
    private Object contentObj;
    private Map<String, Object> userArgs;
    private String language;
    private String encoding;
    private String ontology;
    private String protocol;
    private String conversationID;
    private String replyWith;
    private String inReplyTo;
    private Long replyBy;

    public ACLMessage() {
        receivers = new ArrayList<>();
        userArgs = new HashMap<>();
    }

    public Performative getPerformative() {
        return performative;
    }

    public void setPerformative(Performative performative) {
        this.performative = performative;
    }

    public AID getSender() {
        return sender;
    }

    public void setSender(AID sender) {
        this.sender = sender;
    }

    public List<AID> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<AID> receivers) {
        this.receivers = receivers;
    }

    public AID getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(AID replyTo) {
        this.replyTo = replyTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getContentObj() {
        return contentObj;
    }

    public void setContentObj(Object contentObj) {
        this.contentObj = contentObj;
    }

    public Map<String, Object> getUserArgs() {
        return userArgs;
    }

    public void setUserArgs(Map<String, Object> userArgs) {
        this.userArgs = userArgs;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getOntology() {
        return ontology;
    }

    public void setOntology(String ontology) {
        this.ontology = ontology;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getReplyWith() {
        return replyWith;
    }

    public void setReplyWith(String replyWith) {
        this.replyWith = replyWith;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public Long getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(Long replyBy) {
        this.replyBy = replyBy;
    }

    public enum Performative {
        ACCEPT_PROPOSAL,
        AGREE,
        CANCEL,
        CFP,
        CONFIRM,
        DISCONFIRM,
        FAILURE,
        INFORM,
        INFORM_IF,
        INFORM_REF,
        NOT_UNDERSTOOD,
        PROPOSE,
        QUERY_IF,
        QUERY_REF,
        REFUSE,
        REJECT_PROPOSAL,
        REQUEST,
        REQUEST_WHEN,
        REQUEST_WHENEVER,
        SUBSCRIBE,
        PROXY,
        PROPAGATE,
        UNKNOWN,
    }

}
