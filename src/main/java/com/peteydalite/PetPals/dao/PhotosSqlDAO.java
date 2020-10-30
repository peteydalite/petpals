package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Photo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PhotosSqlDAO implements PhotosDAO{

    private JdbcTemplate jdbc;

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
        SqlRowSet result = jdbc.queryForRowSet(sql);

        while(result.next()){
            Photo photo = mapRowToPhoto(result);
            photoList.add(photo);
        }

        return photoList;
    }

    @Override
    public List<Photo> getAllPhotosForUser(UUID userID) {
        List<Photo> photoList = new ArrayList<>();
        String sql = "Select * from photos where user_id = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, userID);

        while(result.next()){
            Photo photo = mapRowToPhoto(result);
            photoList.add(photo);
        }

        return photoList;
    }

    @Override
    public Photo getPhotoByID(UUID photoID) {
        Photo photo = new Photo();
        String sql = "Select * from photos where photo_id = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, photoID);

        if(result.next()){
            photo = mapRowToPhoto(result);
        }

        return photo;
    }

    @Override
    public Photo getProfilePic(UUID userID) {
        Photo photo = new Photo();
        String sql = "Select * from photos where user_id = ? and profile_picture = 'true' ";
        SqlRowSet result = jdbc.queryForRowSet(sql, userID);

        if(result.next()){
            photo = mapRowToPhoto(result);
        }

        return photo;
    }

    @Override
    public boolean addPhoto(Photo newPhoto) {
        boolean added = false;
        String sqlInsert = "Insert into photos (user_id, profile_picture, src) values (?,?,?) ";
        added = jdbc.update(sqlInsert, newPhoto.getUserID(), newPhoto.isProfile_picture(), newPhoto.getSrc()) == 1;

        return added;
    }

    @Override
    public boolean deletePhoto(Photo photo) {
        boolean deleted = false;
        String sqlDelete = "Delete from photos where photo_id = ? ";
        deleted = jdbc.update(sqlDelete, photo.getPhoto_id()) == 1;

        return deleted;
    }
}
