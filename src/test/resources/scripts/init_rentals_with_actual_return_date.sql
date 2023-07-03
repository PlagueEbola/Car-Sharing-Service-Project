INSERT INTO cars(id, model, brand, type, inventory, daily_fee) VALUES(1, 'C5', 'Citroen', 'UNIVERSAL', 4, 39.9);

INSERT INTO users(id, email, firstname, lastname, password) VALUES(1, 'bob@gmail.com', 'Bob', 'Bobov', '$2a$10$GPvzY8rZ9tpWEC223ZZqHO1xL.gXoa865ICIFLItladaswRbwJFz.');
INSERT INTO users(id, email, firstname, lastname, password) VALUES(2, 'alice@gmail.com', 'Alice', 'Aliceva', '$2a$10$BAq6EA07KIgKPkC1ftgWheGUvteB3SqseWkjtkrToSwDXh/RyQrTK');

INSERT INTO rentals (id, user_id, rental_date, return_date, actual_return_date, car_id) VALUES(3, 1, '2023-06-01', '2023-06-02', '2023-06-02', 1);
INSERT INTO rentals (id, user_id, rental_date, return_date, actual_return_date, car_id) VALUES(4, 2, '2023-06-01', '2023-06-02', '2023-06-03', 1);
INSERT INTO rentals (id, user_id, rental_date, return_date, actual_return_date, car_id) VALUES(5, 2, '2023-06-01', '2023-06-02', '2023-06-04', 1);
INSERT INTO rentals (id, user_id, rental_date, return_date, actual_return_date, car_id) VALUES(6, 2, '2023-06-01', '2023-06-02', '2023-06-05', 1);
