package com.peteydalite.PetPals.dao;

import com.peteydalite.PetPals.model.Message;
import com.peteydalite.PetPals.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
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

       userDao.createNewUser("TEST", "test", "test", "test", "test", "test@email.com");

        User user = userDao.findByUsername("TEST");
        msgDao.createMessgage(user.getId(),"This is a test!", null, null);


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

        User user = userDao.findByUsername("TEST");
    }

    @Test
    public void getMessageByReplyToMessage() {
    }

    @Test
    public void getMessageById() {
    }

    @Test
    public void createMessgage() {
    }

    @Test
    public void deleteMessage() {
    }

    @Test
    public void updateMessage() {
    }
}