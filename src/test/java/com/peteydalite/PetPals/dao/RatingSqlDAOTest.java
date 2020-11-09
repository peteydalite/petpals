package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Playdate;
import com.peteydalite.PetPals.model.Rating;
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

public class RatingSqlDAOTest {
    private JdbcTemplate jdbc;
    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private PlaydateSqlDAO pdDao;
    private RatingSqlDAO ratingDao;
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
        ratingDao = new RatingSqlDAO(jdbc);

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
    public void getAllRatings() {
        List<Rating> before = ratingDao.getAllRatings();
        User user = userDao.findByUsername("pthong14");
        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l, test.getId(), user.getId(), "2021-01-02", new PGpoint(1.0,1.0)) ;
        pdDao.addPlaydate(pd);
        pd = pdDao.getPlaydatesForUser(test).get(0);

        Rating rate = new Rating(pd.getPlaydate_id(), pd.getFrom_user(), pd.getTo_user(), 5.0);
        ratingDao.addRating(rate);

        List<Rating> res = ratingDao.getAllRatings();

        assertEquals(before.size() + 1, res.size());
    }

    @Test
    public void getRatingsByPlaydate() {
        User user = userDao.findByUsername("pthong14");
        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l, test.getId(), user.getId(), "2021-01-02", new PGpoint(1.0,1.0)) ;
        pdDao.addPlaydate(pd);
        pd = pdDao.getPlaydatesForUser(test).get(0);

        Rating rate = new Rating(pd.getPlaydate_id(), pd.getFrom_user(), pd.getTo_user(), 5.0);
        ratingDao.addRating(rate);

        List<Rating> res = ratingDao.getRatingsByPlaydate(pd.getPlaydate_id());

        assertEquals(1, res.size());


    }

    @Test
    public void getRatingsForUser() {
        User user = userDao.findByUsername("pthong14");
        List<Rating> before = ratingDao.getRatingsForUser(user.getId());

        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l, test.getId(), user.getId(), "2021-01-02", new PGpoint(1.0,1.0)) ;
        pdDao.addPlaydate(pd);
        pd = pdDao.getPlaydatesForUser(test).get(0);

        Rating rate = new Rating(pd.getPlaydate_id(), pd.getTo_user(), pd.getFrom_user(),5.0);
        ratingDao.addRating(rate);

        List<Rating> res = ratingDao.getRatingsForUser(user.getId());

        assertEquals(before.size() + 1, res.size());

    }

    @Test
    public void getRatingsFromUser() {
        User user = userDao.findByUsername("pthong14");
        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l, test.getId(), user.getId(), "2021-01-02", new PGpoint(1.0,1.0)) ;
        pdDao.addPlaydate(pd);
        pd = pdDao.getPlaydatesForUser(test).get(0);

        Rating rate = new Rating(pd.getPlaydate_id(), pd.getTo_user(), pd.getFrom_user(), 5.0);
        ratingDao.addRating(rate);

        List<Rating> res = ratingDao.getRatingsFromUser(test.getId());

        assertEquals(1, res.size());

    }

    @Test
    public void getByRatingID() {
        Rating rate = ratingDao.getAllRatings().get(0);
        Rating res = ratingDao.getByRatingID(rate.getRating_id());

        assertEquals(rate.getRating_id(), res.getRating_id());
    }

    @Test
    public void addRating() {
        User user = userDao.findByUsername("pthong14");
        User test = userDao.findByUsername("TEST");
        Playdate pd = new Playdate(1l,1l, test.getId(), user.getId(), "2021-01-02", new PGpoint(1.0,1.0)) ;
        pdDao.addPlaydate(pd);
        pd = pdDao.getPlaydatesForUser(test).get(0);

        Rating rate = new Rating(pd.getPlaydate_id(), pd.getFrom_user(), pd.getTo_user(), 5.0);
        boolean res = ratingDao.addRating(rate);

        assertEquals(true, res);
    }

    @Test
    public void updateRating() {
    }
}