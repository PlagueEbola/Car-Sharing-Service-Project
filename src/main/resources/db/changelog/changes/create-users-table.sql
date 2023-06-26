--liquibase formatted sql
--changeset andrew:create-users-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    password VARCHAR(255),
    telegram_chat_id BIGINT
) ENGINE=InnoDB;

--rollback DROP TABLE users;
