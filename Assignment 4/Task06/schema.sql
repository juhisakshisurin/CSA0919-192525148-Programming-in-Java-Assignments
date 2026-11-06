CREATE DATABASE IF NOT EXISTS campus_events
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_events;

CREATE TABLE IF NOT EXISTS registrations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    student_id VARCHAR(30) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_student_event UNIQUE (student_id, event_name),
    INDEX idx_event_registered (event_name, registered_at, id)
);
