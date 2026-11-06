CREATE DATABASE IF NOT EXISTS assignment5_b3;
USE assignment5_b3;

DROP TABLE IF EXISTS bus_pass;
CREATE TABLE bus_pass (
    pass_id VARCHAR(24) PRIMARY KEY,
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(80) NOT NULL,
    route_no INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;
