package com.peteydalite.PetPals.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import com.peteydalite.PetPals.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersSqlDAO implements UsersDAO{

    private JdbcTemplate jdbc;

    public UsersSqlDAO(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "Select * from Users ";
        List<User> users = new ArrayList<>();
        SqlRowSet results = jdbc.queryForRowSet(sql);
        while(results.next()){
            User tempUser = mapRowToUser(results);
            users.add(tempUser);
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        String sql = "Select * from Users where user_id = ? ";
        SqlRowSet results = jdbc.queryForRowSet(sql, userId);
        User user = new User();
        if(results.next()){
            user = mapRowToUser(results);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = new User();
        String sql = "Select * from Users where username = ? ";
        SqlRowSet results = jdbc.queryForRowSet(sql, username);
        if(results.next()){
            user = mapRowToUser(results);
        }
        return user;
    }

    @Override
    public Long findIdByUsername(String username) {
        Long uID = null;
        String sql = "Select user_id from Users where username = ? ";
        SqlRowSet results = jdbc.queryForRowSet(sql, username);

        if(results.next()){
            uID = results.getLong("user_id");
        }

        return uID;
    }

    @Override
    public boolean createNewUser(String username, String password, String role, String firstName, String lastName, String email) {
        boolean createdUser = false;

        //Only create a new user with a unique username
        Long existingId = this.findIdByUsername(username);
        if(existingId != null && existingId > 0L){
            return createdUser;
        }
        String sqlInsert = "Insert into users (username, password_hash, role, firstname, lastname, email) values (?,?,?,?,?,?) ";
        String password_hash = new BCryptPasswordEncoder().encode(password);


        createdUser = jdbc.update(sqlInsert,username, password_hash, role, firstName, lastName, email) == 1;

        return createdUser;
    }

    private User mapRowToUser( SqlRowSet result){
        User temp = new User();
        temp.setId(result.getLong("user_id"));
        temp.setUsername(result.getString("username"));
        temp.setRole(result.getString("role"));
        temp.setFirstName(result.getString("firstname"));
        temp.setLastName(result.getString("lastname"));
        temp.setEmail(result.getString("email"));

        return temp;


    }
}
