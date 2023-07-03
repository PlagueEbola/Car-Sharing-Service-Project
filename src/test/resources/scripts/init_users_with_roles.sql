INSERT INTO users(id, email, firstname, lastname, password) VALUES(1, 'bob@gmail.com', 'Bob', 'Bobov', '$2a$10$GPvzY8rZ9tpWEC223ZZqHO1xL.gXoa865ICIFLItladaswRbwJFz.');
INSERT INTO users(id, email, firstname, lastname, password) VALUES(2, 'alice@gmail.com', 'Alice', 'Aliceva', '$2a$10$BAq6EA07KIgKPkC1ftgWheGUvteB3SqseWkjtkrToSwDXh/RyQrTK');

INSERT INTO user_roles(id, role_name) VALUES(1, "CUSTOMER");
INSERT INTO user_roles(id, role_name) VALUES(2, "MANAGER");

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);
INSERT INTO users_roles(user_id, role_id) VALUES(2, 2);
