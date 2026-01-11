CREATE DATABASE IF NOT EXISTS library_db;

CREATE USER IF NOT EXISTS 'library_user'@'localhost' IDENTIFIED BY 'library_password';
GRANT ALL PRIVILEGES ON library_db.* TO 'library_user'@'localhost';
FLUSH PRIVILEGES;

USE library_db;

CREATE TABLE Books(
    id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    CONSTRAINT PK_Books PRIMARY KEY (id)
);

INSERT INTO Books (title, author, year) VALUES
('To Kill a Mockingbird', 'Harper Lee', 1960),
('1984', 'George Orwell', 1949),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925),
('The Catcher in the Rye', 'J.D. Salinger', 1951),
('Moby DICK', 'Herman Melville', 1851);