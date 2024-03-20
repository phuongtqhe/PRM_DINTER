package com.example.Dinter.models;

public class MessageSocketIO {
    private String conversationId;
    private String senderId;
    private String text;
    private String recipientId;

    public MessageSocketIO(String conversationId, String senderId, String text, String recipientId) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
        this.recipientId = recipientId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
