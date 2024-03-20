package com.example.Dinter.models;

public class Message {
    private String _id;
    private String conversationId;
    private String senderId;
    private String text;


    public Message( String conversationId, String senderId, String text) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
}
