package com.peteydalite.PetPals.model;

import java.util.UUID;

public class Pal {
    private UUID pal_id;
    private UUID from_user;
    private UUID to_user;
    private Long status_id;

    public Pal(){}

    public Pal(UUID from_user, UUID to_user, Long status_id){
        this.from_user = from_user;
        this.to_user = to_user;
        this.status_id = status_id;
    }

    public Pal(UUID pal_id, UUID from_user, UUID to_user, Long status_id) {
        this.pal_id = pal_id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.status_id = status_id;
    }

    public UUID getPal_id() {
        return pal_id;
    }

    public void setPal_id(UUID pal_id) {
        this.pal_id = pal_id;
    }

    public UUID getFrom_user() {
        return from_user;
    }

    public void setFrom_user(UUID from_user) {
        this.from_user = from_user;
    }

    public UUID getTo_user() {
        return to_user;
    }

    public void setTo_user(UUID to_user) {
        this.to_user = to_user;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }
}
