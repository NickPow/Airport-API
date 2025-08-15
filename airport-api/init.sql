-- Initialize Airport Database
CREATE DATABASE IF NOT EXISTS airportdb;

USE airportdb;

-- Grant privileges
GRANT ALL PRIVILEGES ON airportdb.* TO 'airportuser'@'%';
FLUSH PRIVILEGES;
