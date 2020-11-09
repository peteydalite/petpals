package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RatingSqlDAO implements RatingDAO{
    private JdbcTemplate jdbc;

    @Autowired
    public RatingSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private Rating mapRowToRating(SqlRowSet rs){
        Rating temp = new Rating();
        temp.setRating_id((UUID) rs.getObject("rating_id"));
        temp.setPlaydate_id((UUID) rs.getObject("playdate_id"));
        temp.setFor_user((UUID) rs.getObject("for_user"));
        temp.setFrom_user((UUID) rs.getObject("from_user"));
        temp.setRating(rs.getDouble("rating"));

        return temp;

    }
    @Override
    public List<Rating> getAllRatings() {
        List<Rating> ratingList = new ArrayList<>();
        String select = "Select * from ratings ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select);
            while(result.next()){
                ratingList.add(mapRowToRating(result));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return ratingList;
    }

    @Override
    public List<Rating> getRatingsByPlaydate(UUID playdate_id) {
        List<Rating> ratingList = new ArrayList<>();
        String select = "Select * from ratings where playdate_id = ? ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select, playdate_id);
            while(result.next()){
                ratingList.add(mapRowToRating(result));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return ratingList;
    }

    @Override
    public List<Rating> getRatingsForUser(UUID forUser_id) {
        List<Rating> ratingList = new ArrayList<>();
        String select = "Select * from ratings where for_user = ? ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select, forUser_id);
            while(result.next()){
                ratingList.add(mapRowToRating(result));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return ratingList;
    }

    @Override
    public List<Rating> getRatingsFromUser(UUID fromUser_id) {
        List<Rating> ratingList = new ArrayList<>();
        String select = "Select * from ratings where from_user = ? ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select, fromUser_id);
            while(result.next()){
                ratingList.add(mapRowToRating(result));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return ratingList;
    }

    @Override
    public Rating getByRatingID(UUID ratingID) {
        Rating rating = new Rating();
        String select = "Select * from ratings where rating_id = ? ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select,ratingID);
            if(result.next()){
                rating = mapRowToRating(result);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return rating;
    }

    @Override
    public boolean addRating(Rating rate) {
        boolean added = false;
        String insert = "Insert into ratings (playdate_id, for_user, from_user, rating) " +
                        "values (?,?,?,?) ";
        try{
            added = jdbc.update(con->{
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setObject(1, rate.getPlaydate_id());
                ps.setObject(2, rate.getFor_user());
                ps.setObject(3, rate.getFrom_user());
                ps.setObject(4, rate.getRating());
                return ps;
            }) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return added;
    }

    @Override
    public boolean updateRating(Rating rate) {
        boolean updated = false;
        String sqlUpdate = "Update ratings set playdate_id = ?, for_user = ?, from_user = ?, rating = ? " +
                        "where rating_id = ? ";
        try{
            updated = jdbc.update(con ->{
                PreparedStatement ps = con.prepareStatement(sqlUpdate);
                ps.setObject(1, rate.getPlaydate_id());
                ps.setObject(2, rate.getFor_user());
                ps.setObject(3, rate.getFrom_user());
                ps.setObject(4, rate.getRating());
                ps.setObject(5, rate.getRating_id());
                return ps;
            }) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return updated;
    }
}
