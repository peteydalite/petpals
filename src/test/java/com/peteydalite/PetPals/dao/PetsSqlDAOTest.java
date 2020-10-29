package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.core.parameters.P;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetsSqlDAOTest {

    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private JdbcTemplate jdbc;
    private PetsSqlDAO petDao;


    @BeforeEach
    void setUp() {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pet_pals");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
        jdbc = new JdbcTemplate(dataSource);
        petDao = new PetsSqlDAO(jdbc);

        String sqlInsertTest = "Insert into pets (petname) values (?)";
        jdbc.update(sqlInsertTest,"test");

    }

    @AfterAll
    static void tearDown() {
        try{
            dataSource.getConnection().rollback();
            System.out.println("Rollback");
        }catch(SQLException e){
            System.out.println("Rollback error " + e);
        }

        dataSource.destroy();
        System.out.println("Destroy");
    }

    @Test
    void getAllPets() {
        List<Pet> result = petDao.getAllPets();

        assertEquals(1,result.size());
    }

    @Test
    void getPetById() {
        List<Pet> pets = petDao.getAllPets();
        Pet result = petDao.getPetById(pets.get(0).getPet_id());
        assertEquals(pets.get(0).getPet_id(), result.getPet_id());

    }

    @Test
    void getPetByName() {
        List<Pet> petsList = petDao.getAllPets();
        List<Pet> result = petDao.getPetByName(petsList.get(0).getPetName());

        assertEquals(petsList.size(), result.size());
    }

    @Test
    void createNewPet() {
        List<Pet> listBefore = petDao.getAllPets();
        boolean created = petDao.createNewPet("NewPet",null,null,null);
        
        List<Pet> listAfter = petDao.getAllPets();
        assertEquals(created, true);
        assertEquals(listBefore.size() + 1, listAfter.size());
        
    }
}