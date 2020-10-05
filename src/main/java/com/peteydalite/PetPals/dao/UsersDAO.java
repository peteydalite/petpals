package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.User;

import java.util.List;

public interface UsersDAO {

    List<User> findAll();
    User getUserById(Long userId);
    User findByUsername(String username);
    Long findIdByUsername(String username);
    boolean createNewUser(String username, String password, String role, String firstName, String lastName, String email);
    boolean updateUser(User user);


}
