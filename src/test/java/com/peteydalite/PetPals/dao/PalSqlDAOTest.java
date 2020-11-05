package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pal;
import com.peteydalite.PetPals.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class PalSqlDAOTest {
    private UsersSqlDAO userDao;
    private JdbcTemplate jdbc;
    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private PalSqlDAO palDao;

    @Before
    public void setUp() throws Exception {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pet_pals");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
        jdbc = new JdbcTemplate(dataSource);
        palDao = new PalSqlDAO(jdbc);
        userDao = new UsersSqlDAO(jdbc);

        User user = new User();
        user.setUsername("TEST");
        user.setPassword("tst");
        user.setFirstName("tst");
        user.setLastName("tst");
        user.setRole("TEST");
        user.setRole("Test");
        user.setEmail("test@email.com");
        userDao.createNewUser(user);
    }

    @After
    public void rollBack() throws Exception {
        try {
            dataSource.getConnection().rollback();
            System.out.println("Rollback");
        }catch(SQLException e){
            System.out.println("Could not rollback. " + e);
        }

    }

    @AfterAll
    public void tearDown() throws Exception{
        dataSource.destroy();
        System.out.println("Destroy");
    }

    @Test
    public void getAllPals() {
        List<Pal> before = palDao.getAllPals();
        User usr1 = userDao.findByUsername("pthong14");
        User usrTst = userDao.findByUsername("TEST");
        Pal newPal = new Pal(usr1.getId(), usrTst.getId(), 1l);

        palDao.addPal(newPal);

        List<Pal> result = palDao.getAllPals();

        assertEquals(before.size() + 1, result.size());

    }

    @Test
    public void getUserPals() {
        //Adding new pal request and then accepting it to make sure the user has a pal
        User usr1 = userDao.findByUsername("pthong14");
        User usrTst = userDao.findByUsername("TEST");
        Pal newPal = new Pal(usr1.getId(), usrTst.getId(), 1l);

        palDao.addPal(newPal);
        List<Pal> pending = palDao.getByStatus(usr1, 1l);
        Pal test = pending.get(0);
        test.setStatus_id(2l);
        palDao.updatePal(test);

        List<Pal> before = palDao.getUserPals(usr1.getId());

        //Changing the status to decrement the list of pals the user has
        test.setStatus_id(3l);
        palDao.updatePal(test);
        List<Pal> after = palDao.getUserPals(usr1.getId());

        boolean expected = before.size() >= 1;
        boolean result = after.size() < before.size() || after == null ? true : false;

        assertEquals(expected, result);
    }

    @Test
    public void getByStatus() {
        User usr1 = userDao.findByUsername("pthong14");
        List<Pal> before = palDao.getByStatus(usr1, 1l);

        User usrTst = userDao.findByUsername("TEST");
        Pal newPal = new Pal(usr1.getId(), usrTst.getId(), 1l);

        palDao.addPal(newPal);

        List<Pal> result = palDao.getByStatus(usr1, 1l);

        assertEquals(before.size() + 1, result.size());

    }

    @Test
    public void addPal() {
        User usr1 = userDao.findByUsername("pthong14");
        User usrTst = userDao.findByUsername("TEST");
        Pal newPal = new Pal(usr1.getId(), usrTst.getId(), 1l);

        boolean result = palDao.addPal(newPal);

        assertEquals(true, result);
    }

    @Test
    public void updatePal() {
        User user = userDao.findByUsername("pthong14");
        Pal toUpdate = palDao.getByStatus(user, 1l).get(0);
        toUpdate.setStatus_id(2l);

        boolean result = palDao.updatePal(toUpdate);

        assertEquals(true, result);
    }
}