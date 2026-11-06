CREATE DATABASE IF NOT EXISTS assignment5_b4;
USE assignment5_b4;

CREATE TABLE IF NOT EXISTS bus_pass (
    pass_id VARCHAR(24) NOT NULL,
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(80) NOT NULL,
    route_no INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    campus_code VARCHAR(20) NOT NULL,
    PRIMARY KEY (campus_code, pass_id),
    INDEX idx_pass_reg (reg_no),
    INDEX idx_pass_route (route_no)
) ENGINE=InnoDB;
