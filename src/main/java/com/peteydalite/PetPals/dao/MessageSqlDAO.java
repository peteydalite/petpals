package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.peteydalite.PetPals.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageSqlDAO implements MessageDAO {
    private JdbcTemplate jdbc;

    @Autowired
    public MessageSqlDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> msgList = new ArrayList<>();
        String sql = "Select * from messages ";
        try {
            SqlRowSet result = jdbc.queryForRowSet(sql);

            while (result.next()) {
                Message msg = mapRowToMessage(result);
                msgList.add(msg);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return msgList;
    }

    @Override
    public List<Message> getMessagesByUserId(UUID userId) {
        List<Message> messagesByUserId = new ArrayList<>();
        String sql = "Select * from messages where user_id = ? ";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql, userId);

            while (result.next()) {
                Message msg = mapRowToMessage(result);
                messagesByUserId.add(msg);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return messagesByUserId;
    }

    @Override
    public List<Message> getMessagesByUserReply(UUID replayToUserId) {
        List<Message> messagesByReplay = new ArrayList<>();
        String sql = "Select * from messages where in_reply_to_userid = ?";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql, replayToUserId);

            while (result.next()) {
                Message msg = mapRowToMessage(result);
                messagesByReplay.add(msg);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return messagesByReplay;
    }

    @Override
    public List<Message> getMessagesByReplyToMessage(UUID replyToMessageId) {
        List<Message> messageList = new ArrayList<>();
        String sql = "Select * from messages where in_reply_to_messageid = ? ";

        try {

            SqlRowSet result = jdbc.queryForRowSet(sql, replyToMessageId);

            while (result.next()) {
                Message msg = mapRowToMessage(result);
                messageList.add(msg);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return messageList;
    }

    @Override
    public Message getMessageById(UUID messageId) {
        Message msg = new Message();
        String sql = "Select * from messages where message_id = ? ";

        try {
            SqlRowSet result = jdbc.queryForRowSet(sql, messageId);

            if (result.next()) {
                msg = mapRowToMessage(result);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return msg;
    }

    @Override
    public boolean createMessage(Message newMessage) {
        boolean messageCreated = false;
        String sqlInsert = "Insert into messages (user_id, message_description, in_reply_to_userid, in_reply_to_messageid, datetime) " +
                            "values (?, ?, ?, ?, current_timestamp)";
        try {
            messageCreated = jdbc.update(sqlInsert, newMessage.getUser_id(), newMessage.getDescription(), newMessage.getInReplyToUserId(), newMessage.getInReplyToMessageId()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return messageCreated;

    }

    @Override
    public boolean deleteMessage(UUID messageId) {
        boolean messageDeleted = false;

        String sqlDelete = "Delete from messages where message_id = ? ";

        try {
            messageDeleted = jdbc.update(sqlDelete, messageId) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return messageDeleted;
    }

    @Override
    public boolean updateMessage(Message msg) {
        boolean updated = false;
        String sqlUpdate = "Update messages set user_id = ?, message_description = ?, in_reply_to_userid = ?, in_reply_to_messageid = ?, datetime = current_timestamp " +
                            "where message_id = ? ";

        try {
            updated = jdbc.update(sqlUpdate, msg.getUser_id(), msg.getDescription(), msg.getInReplyToUserId(), msg.getInReplyToMessageId(), msg.getMessage_id()) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
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
