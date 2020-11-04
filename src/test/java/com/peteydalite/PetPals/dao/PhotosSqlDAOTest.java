package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;
import com.peteydalite.PetPals.model.Photo;
import com.peteydalite.PetPals.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class PhotosSqlDAOTest {

    private JdbcTemplate jdbc;
    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    private PhotosSqlDAO photoDao;
    private UsersSqlDAO userDao;

    @Before
    public void setUp() throws Exception {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pet_pals");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
        jdbc = new JdbcTemplate(dataSource);
        photoDao = new PhotosSqlDAO(jdbc);
        userDao = new UsersSqlDAO(jdbc);

    }

    @AfterAll
    public void tearDown() throws Exception {
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
    public void getAllPhotos() {
        List<Photo> photoList = photoDao.getAllPhotos();
        this.addPhoto();
        List<Photo> photosListAfter = photoDao.getAllPhotos();

        assertEquals(photoList.size() + 1, photosListAfter.size());

    }

    @Test
    public void getAllPhotosForUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("tst");
        user.setFirstName("tst");
        user.setLastName("tst");
        user.setRole("TEST");
        user.setRole("Test");
        user.setEmail("test@emial.com");
        userDao.createNewUser(user);

    }

    @Test
    public void getPhotoByID() {
    }

    @Test
    public void getProfilePic() {
    }

    @Test
    public void addPhoto() {
        Photo newPhoto = new Photo();
        User usr = userDao.findAll().get(0);
        newPhoto.setUserID(usr.getId());
        newPhoto.setProfile_picture(true);
        newPhoto.setSrc("sauce");

        boolean result = photoDao.addPhoto(newPhoto);
        assertEquals(true, result);
    }

    @Test
    public void deletePhoto() {
        this.addPhoto();
        Photo toDelete = photoDao.getAllPhotos().get(0);

        boolean result = photoDao.deletePhoto(toDelete);
        assertEquals(true, result);

    }
}