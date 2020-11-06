package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Playdate;
import com.peteydalite.PetPals.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.postgresql.geometric.PGpoint;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class PlaydateSqlDAOTest {
    private JdbcTemplate jdbc;
    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private PlaydateSqlDAO pdDao;
    private UsersSqlDAO userDao;


    @Before
    public void setUp() throws Exception {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pet_pals");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
        jdbc = new JdbcTemplate(dataSource);
        pdDao = new PlaydateSqlDAO(jdbc);
        userDao = new UsersSqlDAO(jdbc);

        User test = new User("TEST", "test", "user", "testf", "testl","test@email.com");
        userDao.createNewUser(test);
    }

    @After
    public void rollback() throws Exception{
        try {
            dataSource.getConnection().rollback();
            System.out.println("Rollback");
        }catch(SQLException e){
            System.out.println("Could not rollback. " + e);
        }
    }

    @AfterAll
    public void tearDown() throws Exception {
        dataSource.destroy();
        System.out.println("Destroy");
    }

    @Test
    public void getAllPlaydates() {
        List<Playdate> before = pdDao.getAllPlaydates();

        User user = userDao.findByUsername("pthong14");
        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l,test.getId(), user.getId(), "2021-01-02", new PGpoint(1.0,1.0)) ;
        pdDao.addPlaydate(pd);

        List<Playdate> res = pdDao.getAllPlaydates();

        assertEquals(before.size() + 1, res.size());

    }

    @Test
    public void getPlaydatesForUser() {
    }

    @Test
    public void getPlaydatesForUserByStatus() {
    }

    @Test
    public void getPlaydatesForUserByConfirmation() {
    }

    @Test
    public void getPlaydateByID() {
        Playdate pd = pdDao.getAllPlaydates().get(0);
        Playdate res = pdDao.getPlaydateByID(pd.getPlaydate_id());

        assertEquals(pd.getPlaydate_id(), res.getPlaydate_id());
    }

    @Test
    public void addPlaydate() {
        User user = userDao.findByUsername("pthong14");
        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l,test.getId(), user.getId(), "2020-02-16", new PGpoint(1.0,1.0)) ;

        boolean res = pdDao.addPlaydate(pd);
        assertEquals(true, res);
    }

    @Test
    public void updatePlaydate() {
        Playdate pd = pdDao.getAllPlaydates().get(0);
        pd.setStatus_id(3l);
        boolean res = pdDao.updatePlaydate(pd);
        System.out.println(pd.toString());
        assertEquals(true, res);
    }
}