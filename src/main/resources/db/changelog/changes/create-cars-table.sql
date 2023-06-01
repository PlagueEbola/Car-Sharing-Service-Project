--liquibase formatted sql
--changeset andrew:create-cars-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS cars (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    model VARCHAR(255),
    brand VARCHAR(255),
    car_type VARCHAR(255),
    inventory BIGINT,
    daily_fee BIGINT
    ) ENGINE=InnoDB;

INSERT INTO cars VALUES(1, 'Leaf', 'Nissan', 'SEDAN', 3, 300);