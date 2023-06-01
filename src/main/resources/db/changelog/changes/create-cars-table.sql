--liquibase formatted sql
--changeset andrew:create-cars-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS cars (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    model VARCHAR(255),
    brand VARCHAR(255),
    car_type_id BIGINT REFERENCES `car_types` (`id`),
    inventory BIGINT,
    daily_fee BIGINT
    ) ENGINE=InnoDB;

INSERT INTO cars VALUES(1, 'Leaf', 'Nissan', 2, 3, 300);