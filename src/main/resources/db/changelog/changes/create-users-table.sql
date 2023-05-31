--liquibase formatted sql
--changeset andrew:create-users-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    password VARCHAR(255),
    role_id BIGINT REFERENCES `user_roles` (`id`)
    ) ENGINE=InnoDB;

INSERT INTO users VALUES(1, 'roma@gmail.com', 'roma', 'sushko', '1234', 1);