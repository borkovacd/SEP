INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_GUEST');

INSERT INTO users(deleted, email, first_name, last_name, password, username)
    VALUES (0, 'devon.smith996@gmail.com', 'Devon', 'Smith', '$2a$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom','devonS');


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

