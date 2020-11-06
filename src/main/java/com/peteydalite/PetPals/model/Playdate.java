package com.peteydalite.PetPals.model;

import java.time.LocalDate;
import java.util.UUID;

public class Playdate {
    private UUID playdate_id;
    private long status_id;
    private long confirmation_id;
    private UUID from_user;
    private UUID to_user;
    private String set_date;
    private Object loc;

    public Playdate(){}

    public Playdate(long status_id, long confirmation_id, UUID from_user, UUID to_user, String set_date, Object loc) {
        this.status_id = status_id;
        this.confirmation_id = confirmation_id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.set_date = set_date;
        this.loc = loc;
    }

    public Playdate(UUID playdate_id, long status_id, long confirmation_id, UUID from_user, UUID to_user, String set_date, Object loc) {
        this.playdate_id = playdate_id;
        this.status_id = status_id;
        this.confirmation_id = confirmation_id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.set_date = set_date;
        this.loc = loc;
    }

    public UUID getPlaydate_id() {
        return playdate_id;
    }

    public void setPlaydate_id(UUID playdate_id) {
        this.playdate_id = playdate_id;
    }

    public long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(long status_id) {
        this.status_id = status_id;
    }

    public long getConfirmation_id() {
        return confirmation_id;
    }

    public void setConfirmation_id(long confirmation_id) {
        this.confirmation_id = confirmation_id;
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

    public String getSet_date() {
        return set_date;
    }

    public void setSet_date(String set_date) {
        this.set_date = set_date;
    }

    public Object getLoc() {
        return loc;
    }

    public void setLoc(Object loc) {
        this.loc = loc;
    }
}
