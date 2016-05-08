package com.ftn.informatika.agents.model;

import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
public class ACLMessage {
    private Performative performative;
    private AID sender;
    private List<AID> receivers;
    private AID replayTo;
    private String content;
    private Object contentObj;
    private Map<String, Object> userArgs;
    private String language;
    private String encoding;
    private String ontology;
    private String protocol;
    private String conversationID;
    private String replayWith;
    private String inReplyTo;
    private Long ReplyBy;

    public ACLMessage() {
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

    public AID getReplayTo() {
        return replayTo;
    }

    public void setReplayTo(AID replayTo) {
        this.replayTo = replayTo;
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

    public String getReplayWith() {
        return replayWith;
    }

    public void setReplayWith(String replayWith) {
        this.replayWith = replayWith;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public Long getReplyBy() {
        return ReplyBy;
    }

    public void setReplyBy(Long replyBy) {
        ReplyBy = replyBy;
    }

    enum Performative {
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
