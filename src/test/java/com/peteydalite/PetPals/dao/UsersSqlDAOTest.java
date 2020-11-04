package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.User;


import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsersSqlDAOTest {

    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private UsersSqlDAO userDao;
    private JdbcTemplate jdbc;

    @BeforeEach
    void setUp() {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pet_pals");
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
        List<User> before = userDao.findAll();
        User user = new User();
        user.setUsername("NewUser");
        user.setPassword("tst");
        user.setFirstName("tst");
        user.setLastName("tst");
        user.setRole("TEST");
        user.setRole("Test");
        user.setEmail("test@email");

        boolean newUserCreated = userDao.createNewUser(user);
        List<User> after = userDao.findAll();

        assertEquals(before.size() + 1, after.size());

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
        UUID actualID = userDao.findIdByUsername(usrList.get(0).getUsername());

        assertEquals(usrList.get(0).getId(), actualID);
    }

    @Test
    void createNewUser() {
        List<User> userListBefore = userDao.findAll();

        User user = new User();
        user.setUsername("NewUser2");
        user.setPassword("tst");
        user.setFirstName("tst");
        user.setLastName("tst");
        user.setRole("TEST");
        user.setRole("Test");
        user.setEmail("test@email.com");
        boolean newUserCreated = userDao.createNewUser(user);
        User actual = userDao.findByUsername(user.getUsername());

        assertEquals(true, newUserCreated);
        assertEquals("NewUser2", actual.getUsername());

        List<User> userListAfter = userDao.findAll();

        assertEquals(userListBefore.size() + 1, userListAfter.size());

    }
}