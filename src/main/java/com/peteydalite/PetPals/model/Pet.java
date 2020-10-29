package com.peteydalite.PetPals.model;

import java.util.UUID;

public class Pet {
    private UUID pet_id;
    private UUID user_id;
    private String petName;
    private double height;
    private double weight;
    private String color;

    public Pet(){

    }

    public Pet(UUID pet_id, UUID user_id, String petName) {
        this.pet_id = pet_id;
        this.user_id = user_id;
        this.petName = petName;
    }

    public Pet(UUID pet_id, UUID user_id, String petName, double height, double weight) {
        this.pet_id = pet_id;
        this.user_id = user_id;
        this.petName = petName;
        this.height = height;
        this.weight = weight;
    }

    public Pet(UUID pet_id, UUID user_id, String petName, double height, double weight, String color) {
        this.pet_id = pet_id;
        this.user_id = user_id;
        this.petName = petName;
        this.height = height;
        this.weight = weight;
        this.color = color;
    }

    public UUID getPet_id() {
        return pet_id;
    }

    public void setPet_id(UUID pet_id) {
        this.pet_id = pet_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
