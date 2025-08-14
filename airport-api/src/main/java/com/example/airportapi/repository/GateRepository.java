package com.example.airportapi.repository;

import com.example.airportapi.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<Gate, Long> {
}
