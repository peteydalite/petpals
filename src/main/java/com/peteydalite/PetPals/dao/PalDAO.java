package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Pal;
import com.peteydalite.PetPals.model.User;

import java.util.List;
import java.util.UUID;

public interface PalDAO {
    List<Pal> getAllPals();
    List<Pal> getUserPals(UUID userID);
    List<Pal> getByStatus(User user, Long status_code);
    Pal getPalbyID(UUID palID);
    boolean addPal(Pal newPal);
    boolean updatePal(Pal updatePal);
}
