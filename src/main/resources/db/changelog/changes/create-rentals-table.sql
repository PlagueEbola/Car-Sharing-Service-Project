--liquibase formatted sql
--changeset andrew:create-rental-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS rentals (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    rental_date DATE,
    return_date DATE,
    actual_return_date DATE,
    car_id BIGINT REFERENCES `cars` (`id`),
    user_id BIGINT REFERENCES `users` (`id`)
    ) ENGINE=InnoDB;

INSERT INTO rentals VALUES(1, current_date, current_date, current_date, 1, 1);