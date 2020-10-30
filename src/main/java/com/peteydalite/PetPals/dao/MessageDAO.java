package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageDAO {
    List<Message> getAllMessages();
    List<Message> getMessagesByUserId(UUID userId);
    List<Message> getMessagesByUserReply(UUID replayToUserId);
    List<Message> getMessageByReplyToMessage(UUID replyToMessageId);
    Message getMessageById(UUID messageId);
    boolean createMessgage(UUID user_id, String message, Optional<UUID> inReplyToUser, Optional<UUID> inReplyToMessage);
    boolean deleteMessage(UUID messageId);
    boolean updateMessage(Message msg);


}
