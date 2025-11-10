-- Admin
INSERT INTO users (username, email, password, enabled)
VALUES ('admin', 'admin@example.com', '$2a$10$E1VQ91vU57rIuZ0lL4hv6u8eI1BwQsx7F6ZGmG6rRltA.dW9lE4Oa', true);

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username='admin'),
        (SELECT id FROM roles WHERE name='ROLE_ADMIN'));

-- Usuario de prueba
INSERT INTO users (username, email, password, enabled)
VALUES ('user1', 'user1@example.com', '$2a$10$7N3gX0aC0lE4f9u1XGQ8tO5Q2A7vX3LzXJd/zy8d5S0bPhtT/A4kW', true);

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username='user1'),
        (SELECT id FROM roles WHERE name='ROLE_USER'));
