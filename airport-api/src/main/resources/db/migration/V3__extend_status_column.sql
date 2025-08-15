-- Extend the status column to accommodate longer enum values
ALTER TABLE flight_schedule MODIFY COLUMN status VARCHAR(20) NOT NULL;
