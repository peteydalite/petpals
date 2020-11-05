package com.peteydalite.PetPals.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.peteydalite.PetPals.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersSqlDAO implements UsersDAO{

    private JdbcTemplate jdbc;

    @Autowired
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
    public User getUserById(UUID userId) {
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
    public UUID findIdByUsername(String username) {
        UUID uID = null;
        String sql = "Select user_id from Users where username = ? ";
        SqlRowSet results = jdbc.queryForRowSet(sql, username);

        if(results.next()){
            uID = (java.util.UUID)results.getObject("user_id");
        }

        return uID;
    }

    @Override
    public boolean createNewUser(User user) {
        boolean createdUser = false;

        //Only create a new user with a unique username
        UUID existingId = this.findIdByUsername(user.getUsername());
        if(existingId != null){
            return createdUser;
        }
        String sqlInsert = "Insert into users (username, password_hash, role, firstname, lastname, email) values (?,?,?,?,?,?) ";
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());


        createdUser = jdbc.update(sqlInsert,user.getUsername(), password_hash, user.getRole(), user.getFirstName(), user.getLastName(), user.getEmail()) == 1;

        return createdUser;
    }

    @Override
    public boolean updateUser(User user) {
        boolean updated = false;
        String sqlUpdate = "Update users set username = ?, firstname = ?, lastname = , email = ? where user_id = ? ";
        updated = jdbc.update(sqlUpdate, user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getId()) == 1;

        return updated;
    }

    private User mapRowToUser( SqlRowSet result){
        User temp = new User();
        temp.setId((java.util.UUID)result.getObject("user_id"));
        temp.setUsername(result.getString("username"));
        temp.setRole(result.getString("role"));
        temp.setFirstName(result.getString("firstname"));
        temp.setLastName(result.getString("lastname"));
        temp.setEmail(result.getString("email"));

        return temp;


    }
}
