package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PetsSqlDAO implements PetsDAO{

    private JdbcTemplate jdbc = new JdbcTemplate();

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
    public Pet getPetById(Long id) {
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
    public boolean createNewPet(String petName, Optional<Double> height, Optional<Double> weight, Optional<String> color) {
        boolean createdPet = false;

        String sqlInsert = "Insert into pets (petname, height, weight, color) values (?,?,?,?) ";
        createdPet = jdbc.update(sqlInsert, petName, height, weight, color) == 1;

        return createdPet;
    }

    private Pet mapRowToPet(SqlRowSet result){
        Pet temp = new Pet();
        temp.setPet_id(result.getLong("pet_id"));
        temp.setPetName(result.getString("petname"));
        temp.setHeight(result.getDouble("height"));
        temp.setWeight(result.getDouble("weight"));
        temp.setColor(result.getString("color"));

        return temp;
    }
}
