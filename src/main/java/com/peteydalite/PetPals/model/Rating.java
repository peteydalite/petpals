package com.peteydalite.PetPals.model;

import java.util.UUID;

public class Rating {
    private UUID rating_id;
    private UUID playdate_id;
    private UUID for_user;
    private UUID from_user;
    private double rating;

    public Rating(){};

    public Rating(UUID playdate_id, UUID for_user, UUID from_user, double rating) {
        this.playdate_id = playdate_id;
        this.for_user = for_user;
        this.from_user = from_user;
        this.rating = rating;
    }

    public Rating(UUID rating_id, UUID playdate_id, UUID for_user, UUID from_user, double rating) {
        this.rating_id = rating_id;
        this.playdate_id = playdate_id;
        this.for_user = for_user;
        this.from_user = from_user;
        this.rating = rating;
    }

    public UUID getRating_id() {
        return rating_id;
    }

    public void setRating_id(UUID rating_id) {
        this.rating_id = rating_id;
    }

    public UUID getPlaydate_id() {
        return playdate_id;
    }

    public void setPlaydate_id(UUID playdate_id) {
        this.playdate_id = playdate_id;
    }

    public UUID getFor_user() {
        return for_user;
    }

    public void setFor_user(UUID for_user) {
        this.for_user = for_user;
    }

    public UUID getFrom_user() {
        return from_user;
    }

    public void setFrom_user(UUID from_user) {
        this.from_user = from_user;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
