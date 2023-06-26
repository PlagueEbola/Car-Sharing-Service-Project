--liquibase formatted sql
--changeset andrew:create-user-roles-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    role_name VARCHAR(255)
) ENGINE=InnoDB;

--rollback DROP TABLE user_roles;