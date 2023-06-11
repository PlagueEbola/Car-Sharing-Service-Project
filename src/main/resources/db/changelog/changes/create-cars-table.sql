--liquibase formatted sql
--changeset andrew:create-cars-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS cars (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    model VARCHAR(255),
    brand VARCHAR(255),
    type VARCHAR(255),
    inventory INTEGER,
    daily_fee DECIMAL(38, 2)
) ENGINE=InnoDB;

--rollback DROP TABLE cars;
