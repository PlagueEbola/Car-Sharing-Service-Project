--liquibase formatted sql
--changeset andrew:create-car-types-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS car_types (
     id BIGINT AUTO_INCREMENT primary key NOT NULL,
     car_type_name VARCHAR(255)
    ) ENGINE=InnoDB;

INSERT INTO car_types VALUES(1, 'SEDAN');
INSERT INTO car_types VALUES(2, 'SUV');
INSERT INTO car_types VALUES(3, 'HATCHBACK');
INSERT INTO car_types VALUES(4, 'UNIVERSAL');