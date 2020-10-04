package com.peteydalite.PetPals.model;

public class Message {
    private Long message_id;
    private Long user_id;
    private String description;
    private Long inReplayTo;

    public Message(){}

    public Message(Long message_id, Long user_id, String description, Long inReplayTo) {
        this.message_id = message_id;
        this.user_id = user_id;
        this.description = description;
        this.inReplayTo = inReplayTo;
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

    public Long getInReplayTo() {
        return inReplayTo;
    }

    public void setInReplayTo(Long inReplayTo) {
        this.inReplayTo = inReplayTo;
    }
}


