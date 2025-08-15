-- Add new direct flight information fields to flight_schedule table
ALTER TABLE flight_schedule 
ADD COLUMN aircraft_type VARCHAR(255),
ADD COLUMN gate_number VARCHAR(20),
ADD COLUMN terminal_number VARCHAR(20),
ADD COLUMN passenger_capacity INTEGER;
