package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDAO {
    List<Message> getMessagesByUserId(Long userId);
    List<Message> getMessagesByUserReply(Long replayToUserId);
    Message getMessageById(Long messageId);
    boolean createMessgage(Long user_id, String message, Optional<Long> inReplyToUser, Optional<Long> inReplayToMessage);
    boolean deleteMessage(Long messageId);
    boolean updateMessage(Message msg);


}
