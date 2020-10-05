BEGIN TRANSACTION;

DROP TABLE IF EXISTS pets;
DROP SEQUENCE IF EXISTS seq_pet_id;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP TABLE IF EXISTS photos;
DROP SEQUENCE IF EXISTS seq_photo_id;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS confirmation;
DROP TABLE IF EXISTS messages;
DROP SEQUENCE IF EXISTS seq_message_id;
DROP TABLE IF EXISTS PlayDates;
DROP SEQUENCE IF EXISTS seq_playdate_id;
DROP TABLE IF EXISTS ratings;
DROP SEQUENCE IF EXISTS seq_rating_id;
DROP TABLE IF EXISTS pals;
DROP SEQUENCE IF EXISTS seq_pal_id;

CREATE SEQUENCE seq_pet_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;

CREATE SEQUENCE seq_user_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;

CREATE SEQUENCE seq_photo_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;

CREATE SEQUENCE seq_message_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;

CREATE SEQUENCE seq_playdate_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;

CREATE SEQUENCE seq_rating_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;

CREATE SEQUENCE seq_pal_id
        INCREMENT BY 1
        NO MAXVALUE
        NO MINVALUE
        CACHE 1;


CREATE TABLE users (
        user_id int DEFAULT nextval('seq_user_id') NOT NULL,
        username varchar(70) NOT NULL,
        password_hash varchar(200) NOT NULL,
        role varchar(60) NOT NULL,
        firstname char(70) NOT NULL,
        lastname char(70) NOT NULL,
        email varchar(80) NOT NULL,
        CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE pets (
        pet_id int DEFAULT nextval('seq_pet_id') NOT NULL,
        user_id int NOT NULL,
        petname varchar(70) NOT NULL,
        height int,
        weight int,
        color char(70),
        CONSTRAINT PK_pet PRIMARY KEY (pet_id),
        CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE photos (
        photo_id int DEFAULT nextval('seq_photo_id') NOT NULL,
        user_id int NOT NULL,
        profile_picture BOOLEAN NOT NULL DEFAULT FALSE,
        src varchar (1000) NOT NULL,
        CONSTRAINT PK_photo PRIMARY KEY (photo_id),
        CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE status (
        status_id int NOT NULL,
        description varchar(70),
        CONSTRAINT PK_status PRIMARY KEY (status_id)
);

CREATE TABLE confirmation (
        confirmation_id int NOT NULL,
        description varchar(70),
        CONSTRAINT PK_confirmation PRIMARY KEY (confirmation_id)
);

INSERT INTO status (status_id, description) values (1, 'Pending');
INSERT INTO status (status_id, description) values (2, 'Accepted');
INSERT INTO status (status_id, description) values (3, 'Rejected');
INSERT INTO confirmation (confirmation_id, description) values (1, 'Pending');
INSERT INTO confirmation (confirmation_id, description) values (2, 'Completed');
INSERT INTO confirmation (confirmation_id, description) values (3, 'Cancelled');


CREATE TABLE messages (
        message_id int DEFAULT nextval('seq_message_id') NOT NULL,
        user_id int NOT NULL,
        message_description varchar(1000000) NOT NULL,
        In_Reply_To_UserId int,
        In_Reply_To_MessageId int,
        datetime timestamp NOT NULL,
        CONSTRAINT PK_message PRIMARY KEY (message_id),
        CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE PlayDate (
        playdate_id int DEFAULT nextval('seq_playdate_id') NOT NULL,
        status_id int NOT NULL,
        confirmation_id int NOT NULL,
        from_user int NOT NULL,
        to_user int,
        set_date DATE NOT NULL,
        loc point,
        CONSTRAINT PK_playdate PRIMARY KEY (playdate_id),
        CONSTRAINT FK_status FOREIGN KEY (status_id) REFERENCES status(status_id),
        CONSTRAINT FK_confirmation FOREIGN KEY (confirmation_id) REFERENCES confirmation(confirmation_id),
        CONSTRAINT FK_fromuser FOREIGN KEY (from_user) REFERENCES users(user_id),
        CONSTRAINT FK_touser FOREIGN KEY (to_user) REFERENCES users(user_id)
);

CREATE TABLE ratings (
        rating_id int DEFAULT nextval('seq_rating_id') NOT NULL,
        playdate_id int NOT NULL,
        for_user int NOT NULL,
        from_user int NOT NULL,
        rating real NOT NULL,
        CONSTRAINT PK_rating PRIMARY KEY (rating_id),
        CONSTRAINT FK_playdate FOREIGN KEY (playdate_id) REFERENCES Playdate(playdate_id),
        CONSTRAINT FK_foruser FOREIGN KEY (for_user) REFERENCES users(user_id),
        CONSTRAINT FK_fromuser FOREIGN KEY (from_user) REFERENCES users(user_id)
);

CREATE TABLE pals (
        pal_id int DEFAULT nextval('seq_pal_id') NOT NULL,
        from_user int NOT NULL,
        to_user int NOT NULL,
        status_id int NOT NULL,
        CONSTRAINT PK_pal PRIMARY KEY (pal_id),
        CONSTRAINT FK_fromuser FOREIGN KEY (from_user) REFERENCES users(user_id),
        CONSTRAINT FK_touser FOREIGN KEY (to_user) REFERENCES users(user_id),
        CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES status(status_id)
);

--ROLLBACK;
COMMIT TRANSACTION;

END TRANSACTION;

--Just incase I need to redo the schema, run the below statements

--DROP SCHEMA public CASCADE;
--CREATE SCHEMA public;