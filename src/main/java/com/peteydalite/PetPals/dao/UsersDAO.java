package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.User;

import java.util.List;
import java.util.UUID;

public interface UsersDAO {

    List<User> findAll();
    User getUserById(UUID userId);
    User findByUsername(String username);
    UUID findIdByUsername(String username);
    boolean createNewUser(User user);
    boolean updateUser(User user);


}
