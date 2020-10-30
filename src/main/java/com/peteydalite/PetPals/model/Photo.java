package com.peteydalite.PetPals.model;

import java.util.UUID;

public class Photo {
    private UUID photo_id;
    private UUID userID;
    private boolean profile_picture = false;
    private String src;

    public Photo(){}

    public Photo(UUID photo_id, UUID userID, boolean profile_picture, String src) {
        this.photo_id = photo_id;
        this.userID = userID;
        this.profile_picture = profile_picture;
        this.src = src;
    }

    public UUID getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(UUID photo_id) {
        this.photo_id = photo_id;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public boolean isProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(boolean profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
