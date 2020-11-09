package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingDAO {
    List<Rating> getAllRatings();
    List<Rating> getRatingsByPlaydate(UUID playdate_id );
    List<Rating> getRatingsForUser(UUID forUser_id);
    List<Rating> getRatingsFromUser(UUID fromUser_id);
    Rating getByRatingID(UUID ratingID);
    boolean addRating(Rating rate);
    boolean updateRating(Rating rate);
}
