package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MessageSqlDAO implements MessageDAO {
    private JdbcTemplate jdbc;

    public MessageSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> msgList = new ArrayList<>();
        String sql = "Select * from messages ";
        SqlRowSet result = jdbc.queryForRowSet(sql);

        while(result.next()){
            Message msg = mapRowToMessage(result);
            msgList.add(msg);
        }
        return msgList;
    }

    @Override
    public List<Message> getMessagesByUserId(UUID userId) {
        List<Message> messagesByUserId = new ArrayList<>();
        String sql = "Select * from messages where user_id = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, userId);

        while(result.next()){
            Message msg = mapRowToMessage(result);
            messagesByUserId.add(msg);
        }
        return messagesByUserId;
    }

    @Override
    public List<Message> getMessagesByUserReply(UUID replayToUserId) {
        List<Message> messagesByReplay = new ArrayList<>();
        String sql = "Select * from messages where in_reply_to_userid = ?";
        SqlRowSet result = jdbc.queryForRowSet(sql, replayToUserId);

        while(result.next()) {
            Message msg = mapRowToMessage(result);
            messagesByReplay.add(msg);
        }
        return messagesByReplay;
    }

    @Override
    public List<Message> getMessageByReplyToMessage(UUID replyToMessageId) {
        List<Message> messageList = new ArrayList<>();
        String sql = "Select * from messages where in_reply_to_messageid = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, replyToMessageId);

        while(result.next()){
            Message msg = mapRowToMessage(result);
            messageList.add(msg);
        }

        return messageList;
    }

    @Override
    public Message getMessageById(UUID messageId) {
        Message msg = new Message();
        String sql = "Select * from messages where message_id = ? ";
        SqlRowSet result = jdbc.queryForRowSet(sql, messageId);

        if(result.next()){
            msg = mapRowToMessage(result);
        }
        return msg;
    }

    @Override
    public boolean createMessgage(UUID user_id, String message, Optional<UUID> inReplyToUser, Optional<UUID> inReplyToMessage) {
        boolean messageCreated = false;
        String sqlInsert = "Insert into messages (user_id, message_description, in_reply_to_userid, in_reply_to_messageid, datetime) " +
                            "values (?, ?, ?, ?, current_timestamp)";
        messageCreated = jdbc.update(sqlInsert,user_id, message, inReplyToUser, inReplyToMessage) == 1;

        return messageCreated;

    }

    @Override
    public boolean deleteMessage(UUID messageId) {
        boolean messageDeleted = false;

        String sqlDelete = "Delete from messages where message_id = ? ";
        messageDeleted = jdbc.update(sqlDelete, messageId) == 1;
        return messageDeleted;
    }

    @Override
    public boolean updateMessage(Message msg) {
        boolean updated = false;
        String sqlUpdate = "Update messages set user_id = ?, message_description = ?, in_reply_to_userid = ?, in_replu_to_messageid = ?, datetime = current_timestamp " +
                            "where message_id = ? ";
        updated = jdbc.update(sqlUpdate, msg.getUser_id(), msg.getDescription(), msg.getInReplyToUserId(), msg.getInReplyToMessageId(), msg.getMessage_id()) == 1;
        return updated;
    }

    private Message mapRowToMessage(SqlRowSet result){
        Message temp = new Message();
        temp.setMessage_id((java.util.UUID)result.getObject("message_id"));;
        temp.setUser_id((java.util.UUID)result.getObject("user_id"));
        temp.setDescription(result.getString("message_description"));
        temp.setInReplyToUserId((java.util.UUID)result.getObject("in_reply_to_userid"));
        temp.setInReplyToMessageId((java.util.UUID)result.getObject("in_reply_to_messageid"));

        return temp;
    }
}
