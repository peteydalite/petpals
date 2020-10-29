package com.peteydalite.PetPals.model;

import java.text.DateFormat;
import java.util.UUID;

public class Message {
    private UUID message_id;
    private UUID user_id;
    private String description;
    private UUID inReplyToUserId;
    private UUID inReplyToMessageId;
    private DateFormat datetime;

    public Message(){}

    public Message(UUID message_id, UUID user_id, String description, UUID inReplyToUserId, UUID inReplyToMessageId, DateFormat datetime) {
        this.message_id = message_id;
        this.user_id = user_id;
        this.description = description;
        this.inReplyToUserId = inReplyToUserId;
        this.inReplyToMessageId = inReplyToMessageId;
        this.datetime = datetime;
    }

    public UUID getMessage_id() {
        return message_id;
    }

    public void setMessage_id(UUID message_id) {
        this.message_id = message_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(UUID inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public UUID getInReplyToMessageId() {
        return inReplyToMessageId;
    }

    public void setInReplyToMessageId(UUID inReplyToMessageId) {
        this.inReplyToMessageId = inReplyToMessageId;
    }

    public DateFormat getDatetime() {
        return datetime;
    }

    public void setDatetime(DateFormat datetime) {
        this.datetime = datetime;
    }
}


