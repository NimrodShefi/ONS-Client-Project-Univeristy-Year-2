INSERT INTO user(first_name, last_name, email, password) VALUES("Nimrod", "Shefi", "nimrod@test.com", "$2a$10$Hfi.30gNk2MQFht5FseEzOzMXp3p9WLDFam8NM5gQWSrHoUvrIEYa");
INSERT INTO user(first_name, last_name, email, password) VALUES("Hannah", "Bonnett", "hannah@test.com", "$2a$10$OMucrxeSJ.7dRqFy6u.TXe2RjE5fEnKPCrlYexWP/bTgTuFPi4x.K");
INSERT INTO user(first_name, last_name, email, password) VALUES("Mahruk", "Zulfiqar", "mahruk@test.com", "$2a$10$ZL9Jwym3ALO5uYVhh7MtouquWswsuWAJHHXIQu1h1BYqKGyleuabm");

INSERT INTO role(name) VALUES("USER");
INSERT INTO role(name) VALUES("AUTHOR");
INSERT INTO role(name) VALUES("ADMIN");

INSERT INTO user_role(user_id, role_id) VALUES(1, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 3);
INSERT INTO user_role(user_id, role_id) VALUES(3, 1);
INSERT INTO user_role(user_id, role_id) VALUES(3, 2);