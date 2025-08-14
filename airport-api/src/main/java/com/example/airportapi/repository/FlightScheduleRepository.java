package com.example.airportapi.repository;

import com.example.airportapi.model.FlightSchedule;
import com.example.airportapi.model.enums.FlightType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    List<FlightSchedule> findByDestinationIdAndFlightType(Long airportId, FlightType flightType);
    List<FlightSchedule> findByOriginIdAndFlightType(Long airportId, FlightType flightType);
}
