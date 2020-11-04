package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;
import com.peteydalite.PetPals.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MessageSqlDAOTest {
    private MessageSqlDAO msgDao;
    private UsersSqlDAO userDao;
    private JdbcTemplate jdbc;
    private static SingleConnectionDataSource dataSource = new SingleConnectionDataSource();


    @Before
    public void setUp() throws Exception {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pet_pals");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
        jdbc = new JdbcTemplate(dataSource);
        msgDao = new MessageSqlDAO(jdbc);

        userDao = new UsersSqlDAO(jdbc);

        User user = new User();
        user.setUsername("TEST");
        user.setPassword("tst");
        user.setFirstName("tst");
        user.setLastName("tst");
        user.setRole("TEST");
        user.setRole("Test");
        user.setEmail("test@email.com");
        userDao.createNewUser(user);

        User user2 = userDao.findByUsername(user.getUsername());
        Message testMsg = new Message();
        testMsg.setUser_id(user2.getId());
        testMsg.setDescription("This is a test!");
        msgDao.createMessage(testMsg);


    }

    @AfterAll
    public void tearDown() throws Exception {
        try {

            dataSource.getConnection().rollback();
            System.out.println("Rollback");
        }catch(SQLException e){
            System.out.println("Could not rollback. " + e);
        }
        dataSource.destroy();
        System.out.println("Destroy");
    }

    @Test
    public void getMessagesByUserId() {
        User user = userDao.findByUsername("TEST");
        List<Message> messageList = msgDao.getMessagesByUserId(user.getId());

        assertEquals(user.getId(), messageList.get(0).getUser_id());

    }

    @Test
    public void getMessagesByUserReply() {
        List<Message> msglist = msgDao.getAllMessages();
        User user = userDao.findByUsername("TEST");

        Message newMsg = new Message();
        newMsg.setUser_id(user.getId());
        newMsg.setDescription("Reply test!");
        newMsg.setInReplyToUserId(msglist.get(0).getUser_id());
        newMsg.setInReplyToMessageId(msglist.get(0).getInReplyToMessageId());
        msgDao.createMessage(newMsg);

        List<Message> msgListAfter = msgDao.getAllMessages();
        List<Message> userReplyList = msgDao.getMessagesByUserReply(msglist.get(0).getUser_id());

        assertEquals(msglist.size() + 1, msgListAfter.size());
        assertEquals(msglist.get(0).getUser_id(), userReplyList.get(0).getUser_id());

    }

    @Test
    public void getMessageByReplyToMessage() {
        Message msg = msgDao.getAllMessages().get(0);
        User user = userDao.findByUsername("TEST");

        Message newMsg = new Message();
        newMsg.setUser_id(user.getId());
        newMsg.setDescription("Does this work?!");
        newMsg.setInReplyToUserId(msg.getUser_id());
        newMsg.setInReplyToMessageId(msg.getMessage_id());

        boolean result = msgDao.createMessage(newMsg);

        assertEquals(true, result );
        System.out.println(newMsg.getInReplyToMessageId());

        List<Message> listAfter = msgDao.getAllMessages();
        List<Message> replyToMsgList = msgDao.getMessagesByReplyToMessage(newMsg.getInReplyToMessageId());

        assertEquals(newMsg.getInReplyToMessageId(), replyToMsgList.get(0).getInReplyToMessageId());
    }

    @Test
    public void getMessageById() {
        Message msg = msgDao.getAllMessages().get(0);
        Message result  = msgDao.getMessageById(msg.getMessage_id());

        assertEquals(msg.getMessage_id(), result.getMessage_id());
    }

    @Test
    public void createMessgage() {
        List<Message> listBefore = msgDao.getAllMessages();
        Message newMsg = listBefore.get(0);
        newMsg.setMessage_id(null);
        newMsg.setDescription("I'm a new message!");

        boolean result = msgDao.createMessage(newMsg);
        List<Message> listAfter = msgDao.getAllMessages();

        assertEquals(true, result);
        assertEquals(listBefore.size() + 1, listAfter.size());
    }

    @Test
    public void deleteMessage() {
        Message newMsg = msgDao.getAllMessages().get(0);
        newMsg.setMessage_id(null);
        newMsg.setDescription("I'm a new message!");
        msgDao.createMessage(newMsg);

        List<Message> listBefore = msgDao.getAllMessages();
        msgDao.deleteMessage(listBefore.get(0).getMessage_id());
        List<Message> listAfter = msgDao.getAllMessages();

        assertEquals(listBefore.size() - 1, listAfter.size());

    }

    @Test
    public void updateMessage() {
        Message msg = msgDao.getAllMessages().get(0);

        msg.setDescription("CHANGED!");
        msgDao.updateMessage(msg);

        Message result = msgDao.getMessageById(msg.getMessage_id());

        assertEquals(msg.getDescription().equals("CHANGED!"), result.getDescription().equals("CHANGED!"));


    }
}