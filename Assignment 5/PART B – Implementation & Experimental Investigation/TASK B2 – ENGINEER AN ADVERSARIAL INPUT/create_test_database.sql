-- H2 creates the local file database automatically.
-- This script documents the assignment-only users table.
CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,
    pwd VARCHAR(100) NOT NULL
);
INSERT INTO users(id, pwd) VALUES ('student01', 'pass123');
