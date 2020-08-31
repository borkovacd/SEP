INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_GUEST');

INSERT INTO users(address, blocked, date_of_birth, deleted, email, first_name, last_name, password, phone_number, user_type, username)
    VALUES ('777 Brockton Avenue, Abington MA 2351', 0, '1996-05-05', 0, 'devon.smith996@gmail.com', 'Devon', 'Smith', '$2a$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom', '202-555-0194', 'ADMIN', 'devonS');
INSERT INTO users(address, blocked, date_of_birth, deleted, email, first_name, last_name, password, phone_number, user_type, username)
    VALUES ('777 Brockton Avenue, Abington MA 2351', 0, '1986-05-05', 0, 'petrovic.petar@gmail.com', 'Petar', 'Petrovic', '$2a$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom', '202-555-0194', 'USER', 'petarP');
INSERT INTO users(address, blocked, date_of_birth, deleted, email, first_name, last_name, password, phone_number, user_type, username)
    VALUES ('777 Brockton Avenue, Abington MA 2351', 0, '1986-05-05', 0, 'jefferson.meghan@gmail.com', 'Meghan', 'Jefferson', '$2a$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom', '202-555-0194', 'USER', 'meghanJ');
INSERT INTO users(address, blocked, date_of_birth, deleted, email, first_name, last_name, password, phone_number, user_type, username)
    VALUES ('777 Brockton Avenue, Abington MA 2351', 0, '1986-05-05', 0, 'aniston.jennifer@gmail.com', 'Jennifer', 'Aniston', '$2a$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom', '202-555-0194', 'USER', 'jenniferA');


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 1);
