package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Playdate;
import com.peteydalite.PetPals.model.User;

import java.util.List;
import java.util.UUID;

public interface PlaydateDAO {
    List<Playdate> getAllPlaydates();
    List<Playdate> getPlaydatesForUser(User user);
    List<Playdate> getPlaydatesForUserByStatus(User user, long status_id);
    List<Playdate> getPlaydatesForUserByConfirmation(User user, long confirm_id);
    Playdate getPlaydateByID(UUID playdateID);
    boolean addPlaydate(Playdate newPlaydate);
    boolean updatePlaydate(Playdate updatedPlaydate);

}
