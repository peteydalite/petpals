package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pal;
import com.peteydalite.PetPals.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PalSqlDAO implements PalDAO {
    private JdbcTemplate jdbc;

    @Autowired
    public PalSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private Pal mapRowToPal(SqlRowSet rs){
        Pal temp = new Pal();
        temp.setPal_id((UUID) rs.getObject("pal_id"));
        temp.setFrom_user((UUID) rs.getObject("from_user"));
        temp.setTo_user((UUID) rs.getObject("to_user"));
        temp.setStatus_id(rs.getLong("status_id"));
        return temp;
    }
    @Override
    public List<Pal> getAllPals() {
        List<Pal> palList = new ArrayList<>();
        String select = "Select * from pals ";
        try {
            SqlRowSet result = jdbc.queryForRowSet(select);

            while (result.next()) {
                Pal pal = mapRowToPal(result);
                palList.add(pal);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return palList;
    }

    @Override
    public List<Pal> getUserPals(UUID userID) {
        List<Pal> palList = new ArrayList<>();
        String select = "Select * from pals where from_user = ? or to_user = ? " +
                        "and status_id = 1 ";
        try {


            SqlRowSet result = jdbc.queryForRowSet(select, userID, userID);

            while (result.next()) {
                Pal pal = mapRowToPal(result);
                palList.add(pal);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return palList;
    }

    @Override
    public List<Pal> getByStatus(User user, Long status_code) {
        List<Pal> palList = new ArrayList<>();
        String select = "Select * from pals where from_user = ? or to_user = ? " +
                        " and status_id = ? ";
        try {
            SqlRowSet result = jdbc.queryForRowSet(select, user.getId(), user.getId(), status_code);

            while (result.next()) {
                Pal pal = mapRowToPal(result);
                palList.add(pal);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return palList;
    }

    @Override
    public boolean addPal(Pal newPal) {
        boolean added = false;

        String add = "Insert into pals (from_user, to_user, status_id) values (?,?,1) ";
        try {
            added = jdbc.update(add, newPal.getFrom_user(), newPal.getTo_user()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return added;
    }

    @Override
    public boolean updatePal(Pal updatePal) {
        boolean updated = false;

        String update = "Update pals set from_user = ?, to_user = ?, status_id = ? " +
                        "where pal_id = ? ";
        try {
            updated = jdbc.update(update, updatePal.getFrom_user(), updatePal.getTo_user(), updatePal.getStatus_id(), updatePal.getStatus_id()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return updated;
    }
}
