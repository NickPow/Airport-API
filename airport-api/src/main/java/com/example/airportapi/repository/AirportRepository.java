package com.example.airportapi.repository;

import com.example.airportapi.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    List<Airport> findByCityId(Long cityId);
}
