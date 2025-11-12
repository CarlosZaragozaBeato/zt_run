-- Contraseñas bcrypt (ejemplo de hash para 'admin123' y 'user123')
INSERT INTO users (username, email, password, enabled) VALUES
('admin', 'admin@example.com', '$2a$10$E1VQ91vU57rIuZ0lL4hv6u8eI1BwQsx7F6ZGmG6rRltA.dW9lE4Oa', true),
('user1', 'user1@example.com', '$2a$10$7N3gX0aC0lE4f9u1XGQ8tO5Q2A7vX3LzXJd/zy8d5S0bPhtT/A4kW', true);
