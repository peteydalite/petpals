package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pet;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetsDAO {
    List<Pet> getAllPets();
    Pet getPetById(UUID id);
    List<Pet> getPetByName(String petName);
    boolean createNewPet(UUID user_id, String petName, Optional<Double> height, Optional<Double> weight, Optional<String> color);
    List<Pet> getPetsByUserId(UUID userId);
    boolean updatePet(Pet pet);
}
