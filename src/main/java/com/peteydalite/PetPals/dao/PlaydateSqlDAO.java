package com.peteydalite.PetPals.dao;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.peteydalite.PetPals.model.Playdate;
import com.peteydalite.PetPals.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlaydateSqlDAO implements PlaydateDAO {
    private JdbcTemplate jdbc;

    @Autowired
    public PlaydateSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private Playdate mapRowToPlaydate(SqlRowSet rs){
        Playdate temp = new Playdate();
        temp.setPlaydate_id((UUID)rs.getObject("playdate_id"));
        temp.setStatus_id(rs.getLong("status_id"));
        temp.setConfirmation_id(rs.getLong("confirmation_id"));
        temp.setFrom_user((UUID) rs.getObject("from_user"));
        temp.setTo_user((UUID) rs.getObject("to_user"));
        temp.setSet_date(rs.getString("set_date").trim());
        temp.setLoc(rs.getObject("loc"));

        return  temp;
    }

    @Override
    public List<Playdate> getAllPlaydates() {
        List<Playdate> playdateList = new ArrayList<>();
        String select = "Select * from playdate ";
        try{
            SqlRowSet result  = jdbc.queryForRowSet(select);
            while(result.next()){
                Playdate pd = mapRowToPlaydate(result);
                playdateList.add(pd);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return playdateList;
    }

    @Override
    public List<Playdate> getPlaydatesForUser(User user) {
        List<Playdate> playdateList = new ArrayList<>();
        String select = "Select * from playdate where from_user = ? or to_user = ? ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select, user.getId(), user.getId());
            while(result.next()){
                Playdate pd = mapRowToPlaydate(result);
                playdateList.add(pd);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return playdateList;
    }

    @Override
    public List<Playdate> getPlaydatesForUserByStatus(User user, long status_id) {
        List<Playdate> playdateList = new ArrayList<>();
        String select = "Select * from playdate where from_user = ? or to_user = ? and " +
                        "status_id = ? ";

        try{
            SqlRowSet result = jdbc.queryForRowSet(select, user.getId(), user.getId(), status_id);
            while(result.next()){
                Playdate pd = mapRowToPlaydate(result);
                playdateList.add(pd);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return playdateList;
    }

    @Override
    public List<Playdate> getPlaydatesForUserByConfirmation(User user, long confirm_id) {
        List<Playdate> playdateList = new ArrayList<>();
        String select = "Select * from playdate where from_user = ? or to_user = ? and " +
                "confirmation_id = ? ";

        try{
            SqlRowSet result = jdbc.queryForRowSet(select, user.getId(), user.getId(), confirm_id);
            while(result.next()){
                Playdate pd = mapRowToPlaydate(result);
                playdateList.add(pd);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return playdateList;
    }

    @Override
    public Playdate getPlaydateByID(UUID playdateID) {
        Playdate pd = new Playdate();
        String select = "select * from playdate where playdate_id = ? ";
        try{
            SqlRowSet result = jdbc.queryForRowSet(select, playdateID);
            if(result.next()){
                pd = mapRowToPlaydate(result);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return pd;
    }

    @Override
    public boolean addPlaydate(Playdate newPlaydate) {
        boolean added = false;
        String insert = "insert into playdate (status_id, confrimation_id, from_user, to_user, set_date, loc) "+
                        "values (1, 1, ?, ?, ?, ?) ";
        try{
            added = jdbc.update(insert, newPlaydate.getFrom_user(), newPlaydate.getTo_user(), newPlaydate.getSet_date(), newPlaydate.getLoc()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return added;
    }

    @Override
    public boolean updatePlaydate(Playdate updatedPlaydate) {
        boolean updated = false;
        String update = "update playdate set status_id = ?, confirmation_id = ?, from_user = ?, to_user = ?, set_date = ?, loc = ? " +
                        "where playdate_id = ? ";
        try{
            updated = jdbc.update(update, updatedPlaydate.getStatus_id(), updatedPlaydate.getConfirmation_id(), updatedPlaydate.getFrom_user(), updatedPlaydate.getTo_user(),
                updatedPlaydate.getSet_date(), updatedPlaydate.getLoc(), updatedPlaydate.getPlaydate_id()) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return updated;
    }
}
