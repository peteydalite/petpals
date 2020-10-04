package com.peteydalite.PetPals.model;

public class Pet {
    private Long pet_id;
    private String petName;
    private double height;
    private double weight;
    private String color;

    public Pet(){

    }

    public Pet(Long pet_id, String petName) {
        this.pet_id = pet_id;
        this.petName = petName;
    }

    public Pet(Long pet_id, String petName, double height, double weight) {
        this.pet_id = pet_id;
        this.petName = petName;
        this.height = height;
        this.weight = weight;
    }

    public Pet(Long pet_id, String petName, double height, double weight, String color) {
        this.pet_id = pet_id;
        this.petName = petName;
        this.height = height;
        this.weight = weight;
        this.color = color;
    }

    public Long getPet_id() {
        return pet_id;
    }

    public void setPet_id(Long pet_id) {
        this.pet_id = pet_id;
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
