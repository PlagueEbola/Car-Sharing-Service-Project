INSERT INTO users (id) VALUES (1);
INSERT INTO users (id) VALUES (2);

INSERT INTO rentals (id, user_id) VALUES(1, 1);
INSERT INTO rentals (id, user_id) VALUES(2, 1);
INSERT INTO rentals (id, user_id, actual_return_date) VALUES(3, 1, '2023-06-01');
INSERT INTO rentals (id, user_id, actual_return_date) VALUES(4, 2, '2023-06-01');
INSERT INTO rentals (id, user_id, actual_return_date) VALUES(5, 2, '2023-06-02');
INSERT INTO rentals (id, user_id, actual_return_date) VALUES(6, 2, '2023-06-03');
