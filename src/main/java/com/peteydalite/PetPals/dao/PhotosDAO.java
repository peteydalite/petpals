package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Photo;

import java.util.List;
import java.util.UUID;

public interface PhotosDAO {
    List<Photo> getAllPhotos();
    List<Photo> getAllPhotosForUser(UUID userID);
    Photo getPhotoByID(UUID photoID);
    Photo getProfilePic(UUID userID);
    boolean addPhoto(Photo newPhoto);
    boolean deletePhoto(Photo photo);
}
