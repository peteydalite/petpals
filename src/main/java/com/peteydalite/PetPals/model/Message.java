package com.peteydalite.PetPals.model;

import java.text.DateFormat;

public class Message {
    private Long message_id;
    private Long user_id;
    private String description;
    private Long inReplyToUserId;
    private Long inReplyToMessageId;
    private DateFormat datetime;

    public Message(){}

    public Message(Long message_id, Long user_id, String description, Long inReplyToUserId, Long inReplyToMessageId, DateFormat datetime) {
        this.message_id = message_id;
        this.user_id = user_id;
        this.description = description;
        this.inReplyToUserId = inReplyToUserId;
        this.inReplyToMessageId = inReplyToMessageId;
        this.datetime = datetime;
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public Long getInReplyToMessageId() {
        return inReplyToMessageId;
    }

    public void setInReplyToMessageId(Long inReplyToMessageId) {
        this.inReplyToMessageId = inReplyToMessageId;
    }

    public DateFormat getDatetime() {
        return datetime;
    }

    public void setDatetime(DateFormat datetime) {
        this.datetime = datetime;
    }
}


