package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;
import com.peteydalite.PetPals.model.Photo;
import com.peteydalite.PetPals.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.core.parameters.P;

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
        User user = userDao.findByUsername("pthong14");
        List<Photo> photoList = photoDao.getAllPhotosForUser(user.getId());
        boolean result = photoList.size() >= 1;

        assertEquals(true, result);

    }

    @Test
    public void getPhotoByID() {
        User user = userDao.findByUsername("pthong14");
        List<Photo> photoList = photoDao.getAllPhotosForUser(user.getId());
        Photo result = photoDao.getPhotoByID(photoList.get(0).getPhoto_id());

        assertEquals(photoList.get(0).getPhoto_id(), result.getPhoto_id());
    }

    @Test
    public void getProfilePic() {
        Photo profpic = new Photo();
        User usr = userDao.findByUsername("pthong14");
        profpic.setSrc("my profile pic");
        profpic.setUserID(usr.getId());
        profpic.setProfile_picture(true);
        photoDao.addPhoto(profpic);

        List<Photo> usrPhotos = photoDao.getAllPhotosForUser(usr.getId());
        for(Photo ph : usrPhotos){
            if(ph.isProfile_picture()){
                profpic = ph;
            }
        }

        Photo result = photoDao.getProfilePic(usr.getId());

        assertEquals(profpic.getPhoto_id(), result.getPhoto_id());
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
        Photo toDelete = photoDao.getAllPhotos().get(0);

        boolean result = photoDao.deletePhoto(toDelete);
        assertEquals(true, result);

    }
}