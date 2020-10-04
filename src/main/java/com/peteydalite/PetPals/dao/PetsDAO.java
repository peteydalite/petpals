package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pet;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PetsDAO {
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    List<Pet> getPetByName(String petName);
    boolean createNewPet(String petName, Optional<Double> height, Optional<Double> weight, Optional<String> color);
}
