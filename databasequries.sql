-- Create and use the Bus_reservation database
CREATE DATABASE IF NOT EXISTS Bus_reservation;
USE Bus_reservation;

-- Create the Account table first (to avoid foreign key issues)
CREATE TABLE `Account` (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    accountType VARCHAR(10) NOT NULL
);

-- Display the Account table
SELECT * FROM `Account`;

-- Create the Bus table
CREATE TABLE `Bus` (
    busNo INT PRIMARY KEY,
    ac TINYINT(1) NOT NULL,  -- Use TINYINT(1) instead of BOOLEAN
    capacity INT NOT NULL,
    fromToLocation VARCHAR(100) NOT NULL
);

-- Display the Bus table
SELECT * FROM `Bus`;

-- Create the Booking table (after Bus and Account)
CREATE TABLE Booking(
    Booking_No INT AUTO_INCREMENT PRIMARY KEY,
    passenger_Name VARCHAR(50) NOT NULL,
    NumOfPassengers INT NOT NULL,
    bus_no INT NOT NULL,
    travel_date DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (bus_no) REFERENCES `Bus`(busNo) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES `Account`(user_id) ON DELETE CASCADE
);
SHOW TABLES;

-- Display the Booking table
SELECT * FROM `Booking`;
