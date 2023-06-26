--liquibase formatted sql
--changeset denmg:create-users-table splitStatements:true endDelimiter:;

INSERT INTO user_roles(id, role_name) VALUES(1, "CUSTOMER");
INSERT INTO user_roles(id, role_name) VALUES(2, "MANAGER");

--crypted password for bob@gmail.com - 123, alice@gmail.com - 12345
INSERT INTO users(id, email, firstname, lastname, password) VALUES(1, 'bob@gmail.com', 'Bob', 'Bobov', '$2a$10$GPvzY8rZ9tpWEC223ZZqHO1xL.gXoa865ICIFLItladaswRbwJFz.');
INSERT INTO users(id, email, firstname, lastname, password) VALUES(2, 'alice@gmail.com', 'Alice', 'Aliceva', '$2a$10$BAq6EA07KIgKPkC1ftgWheGUvteB3SqseWkjtkrToSwDXh/RyQrTK');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);
INSERT INTO users_roles(user_id, role_id) VALUES(2, 2);

INSERT INTO cars(id, model, brand, type, inventory, daily_fee) VALUES(1, 'C5', 'Citroen', 'UNIVERSAL', 4, 29.9);

INSERT INTO rentals(id, rental_date, return_date, actual_return_date, car_id, user_id) VALUES(1, '2023-06-06', '2023-06-07', '2023-06-07', 1, 1);

INSERT INTO payments(id, status, type, rental_id, stripe_price, price) VALUES(1, 'PENDING', 'PAYMENT', 1, "100", 100);
