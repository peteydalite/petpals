package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetsSqlDAO implements PetsDAO{

    private JdbcTemplate jdbc;

    @Autowired
    public PetsSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> allPets = new ArrayList<>();
        String sql = "Select * from pets";
        SqlRowSet result = jdbc.queryForRowSet(sql);

        while(result.next()){
            Pet pet = mapRowToPet(result);
            allPets.add(pet);
        }
        return allPets;
    }

    @Override
    public Pet getPetById(UUID id) {
        Pet pet = new Pet();

        String sql = "Select * from pets where pet_id = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, id);

        if(result.next()){
            pet = mapRowToPet(result);
        }
        return pet;
    }

    @Override
    public List<Pet> getPetByName(String petName) {
        List<Pet> petsByName = new ArrayList<>();
        String sql = "Select * from pets where petname = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, petName);

        while(result.next()){
            Pet pet = mapRowToPet(result);
            petsByName.add(pet);
        }
        return petsByName;
    }

    @Override
    public boolean createNewPet(UUID user_id, String petName, Optional<Double> height, Optional<Double> weight, Optional<String> color) {
        boolean createdPet = false;

        String sqlInsert = "Insert into pets (user_id, petname, height, weight, color) values (?,?,?,?,?) ";
        createdPet = jdbc.update(sqlInsert, user_id, petName, height, weight, color) == 1;

        return createdPet;
    }

    @Override
    public List<Pet> getPetsByUserId(UUID userId) {
        List<Pet> petList = new ArrayList<>();
        String sql = "Select * from pets where user_id = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, userId);

        while(result.next()){
            Pet pet = mapRowToPet(result);
            petList.add(pet);
        }
        return petList;
    }

    @Override
    public boolean updatePet(Pet pet) {
        boolean updated = false;
        String sqlUpdate = "Update pets set petname = ? , height = ?, weight = ?, color = ? where pet_id = ? ";
        updated = jdbc.update(sqlUpdate, pet.getPetName(), pet.getHeight(), pet.getWeight(), pet.getColor(), pet.getPet_id()) == 1;

        return updated;
    }

    private Pet mapRowToPet(SqlRowSet result){
        Pet temp = new Pet();
        temp.setPet_id((java.util.UUID)result.getObject("pet_id"));
        temp.setUser_id((java.util.UUID)result.getObject("user_id"));
        temp.setPetName(result.getString("petname"));
        temp.setHeight(result.getDouble("height"));
        temp.setWeight(result.getDouble("weight"));
        temp.setColor(result.getString("color"));

        return temp;
    }
}
