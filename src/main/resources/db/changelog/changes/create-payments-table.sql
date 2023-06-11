--liquibase formatted sql
--changeset andrew:create-payments-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    status VARCHAR(255),
    type VARCHAR(255),
    rental_id BIGINT REFERENCES rentals (id),
    stripe_payment_url VARCHAR(1000),
    stripe_price VARCHAR(255),
    price DECIMAL(38, 2)
) ENGINE=InnoDB;

--rollback DROP TABLE payments;
