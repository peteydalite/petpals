package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.User;

import org.junit.AfterClass;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersSqlDAOTest {

    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private UsersSqlDAO userDao;
    private JdbcTemplate jdbc;

    @BeforeEach
    void setUp() {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/petpals");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
        jdbc = new JdbcTemplate(dataSource);
        userDao = new UsersSqlDAO(jdbc);

        String sqlInsertTest = "Insert into Users (username, password_hash, role, firstname, lastname, email) values (?,?,?,?,?,?) ";
        jdbc.update(sqlInsertTest, "TEST", "TEST", "USER", "TESTF", "TESTL", "test@test.com");

        System.out.println("Before Test Setup");

    }

    @AfterAll
    static void tearDown() {
        try {

            dataSource.getConnection().rollback();
            System.out.println("Rollback");
        }catch(SQLException e){
            System.out.println("Could not rollback. " + e);
        }
        dataSource.destroy();
        System.out.println("Destroy");
    }

    @Test
    void findAll() {
        List<User> actual = new ArrayList<>();
        actual = userDao.findAll();

        assertEquals(1, actual.size());

    }

    @Test
    void getUserById() {
        List<User> usrlist = new ArrayList<>();
        usrlist = userDao.findAll();

        User actual = new User();
        actual = userDao.getUserById(usrlist.get(0).getId());

        assertEquals(usrlist.get(0).getId(), actual.getId());
    }

    @Test
    void findByUsername() {
        List<User> usrList = userDao.findAll();
        User actual = userDao.findByUsername(usrList.get(0).getUsername());

        assertEquals(usrList.get(0).getUsername(), actual.getUsername());
    }

    @Test
    void findIdByUsername() {
        List<User> usrList = userDao.findAll();
        Long actualID = userDao.findIdByUsername(usrList.get(0).getUsername());

        assertEquals(usrList.get(0).getId(), actualID);
    }

    @Test
    void createNewUser() {
        List<User> userListBefore = userDao.findAll();

        boolean newUserCreated = userDao.createNewUser("NewUser", "test", "User", "test", "test", "test@test.com");
        User actual = userDao.findByUsername("NewUser");

        assertEquals(true, newUserCreated);
        assertEquals("NewUser", actual.getUsername());

        List<User> userListAfter = userDao.findAll();

        assertEquals(userListBefore.size() + 1, userListAfter.size());

    }
}