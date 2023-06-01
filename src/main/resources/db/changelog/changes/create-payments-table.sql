--liquibase formatted sql
--changeset andrew:create-payments-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    status VARCHAR(255),
    type VARCHAR(255),
    rental_id BIGINT REFERENCES `rentails` (`id`),
    rental_cost BIGINT
    ) ENGINE=InnoDB;

INSERT INTO payments VALUES(1, 'PENDING', 'PAYMENT', 1, 300);