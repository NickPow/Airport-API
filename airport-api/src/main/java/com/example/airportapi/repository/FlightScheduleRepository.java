package com.example.airportapi.repository;

import com.example.airportapi.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    // Get arrivals to a specific airport (flights where destination = airportId)
    List<FlightSchedule> findByDestination_Id(Long airportId);
    
    // Get departures from a specific airport (flights where origin = airportId)  
    List<FlightSchedule> findByOrigin_Id(Long airportId);
}
