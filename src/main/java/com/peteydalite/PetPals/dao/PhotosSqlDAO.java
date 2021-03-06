package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PhotosSqlDAO implements PhotosDAO{

    private JdbcTemplate jdbc;

    @Autowired
    public PhotosSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private Photo mapRowToPhoto(SqlRowSet rs){
        Photo temp = new Photo();
        temp.setPhoto_id((java.util.UUID) rs.getObject("photo_id"));
        temp.setUserID((java.util.UUID) rs.getObject("user_id"));
        temp.setProfile_picture(rs.getBoolean("profile_picture"));
        temp.setSrc(rs.getString("src"));

        return temp;
    }

    @Override
    public List<Photo> getAllPhotos() {
        List<Photo> photoList = new ArrayList<>();
        String sql = "Select * from photos ";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql);

            while (result.next()) {
                Photo photo = mapRowToPhoto(result);
                photoList.add(photo);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return photoList;
    }

    @Override
    public List<Photo> getAllPhotosForUser(UUID userID) {
        List<Photo> photoList = new ArrayList<>();
        String sql = "Select * from photos where user_id = ? ";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql, userID);

            while (result.next()) {
                Photo photo = mapRowToPhoto(result);
                photoList.add(photo);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return photoList;
    }

    @Override
    public Photo getPhotoByID(UUID photoID) {
        Photo photo = new Photo();
        String sql = "Select * from photos where photo_id = ? ";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql, photoID);

            if (result.next()) {
                photo = mapRowToPhoto(result);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return photo;
    }

    @Override
    public Photo getProfilePic(UUID userID) {
        Photo photo = new Photo();
        String sql = "Select * from photos where user_id = ? and profile_picture = 'true' ";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql, userID);

            if (result.next()) {
                photo = mapRowToPhoto(result);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return photo;
    }

    @Override
    public boolean addPhoto(Photo newPhoto) {
        boolean added = false;
        String sqlInsert = "Insert into photos (user_id, profile_picture, src) values (?,?,?) ";
        try {
            added = jdbc.update(sqlInsert, newPhoto.getUserID(), newPhoto.isProfile_picture(), newPhoto.getSrc()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return added;
    }

    @Override
    public boolean deletePhoto(Photo photo) {
        boolean deleted = false;
        String sqlDelete = "Delete from photos where photo_id = ? ";

        try {
            deleted = jdbc.update(sqlDelete, photo.getPhoto_id()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return deleted;
    }
}
