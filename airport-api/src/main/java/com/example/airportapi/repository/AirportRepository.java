package com.example.airportapi.repository;

import com.example.airportapi.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    List<Airport> findByCityId(Long cityId);
    Optional<Airport> findByCode(String code);
}
