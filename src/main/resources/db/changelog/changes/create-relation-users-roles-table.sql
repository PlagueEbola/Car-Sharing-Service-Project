--liquibase formatted sql
--changeset denmg:create-users-roles-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS users_roles (
    user_id bigint NOT NULL REFERENCES users (id),
    role_id bigint NOT NULL REFERENCES user_roles (id),
    PRIMARY KEY (user_id, role_id)
)

--rollback DROP TABLE users_roles;
