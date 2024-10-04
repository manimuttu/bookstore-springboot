-- Inserting sample data into AppUser table
INSERT INTO app_user (username, password) VALUES ('john_doe', 'password1');
INSERT INTO app_user (username, password) VALUES ('jane_doe', 'password2');
INSERT INTO app_user (username, password) VALUES ('alice', 'password3');

-- Inserting sample data into Book table
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0134685991', 'Effective Java', 'Joshua Bloch', 45.00, 10);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0134757599', 'Clean Code', 'Robert C. Martin', 40.00, 5);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0135166307', 'Spring in Action', 'Craig Walls', 39.99, 8);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0321573513', 'Java Concurrency in Practice', 'Brian Goetz', 50.00, 6);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0596009205', 'Head First Design Patterns', 'Eric Freeman', 35.00, 4);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0132763909', 'Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma', 60.00, 3);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0132853957', 'Java: The Complete Reference', 'Herbert Schildt', 55.00, 7);
INSERT INTO book (isbn, title, author, price, stock) VALUES ('978-0132350884', 'Introduction to the Theory of Computation', 'Michael Sipser', 70.00, 2);
