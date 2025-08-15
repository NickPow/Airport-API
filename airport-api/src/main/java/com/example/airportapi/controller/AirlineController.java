package com.example.airportapi.controller;

import com.example.airportapi.dto.AirlineCreateDTO;
import com.example.airportapi.model.Airline;
import com.example.airportapi.repository.AirlineRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    @Autowired
    private AirlineRepository airlineRepository;

    @GetMapping
    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable Long id) {
        return airlineRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Airline> createAirline(@Valid @RequestBody AirlineCreateDTO dto) {
        Airline airline = new Airline(dto.getName(), dto.getCode());
        Airline savedAirline = airlineRepository.save(airline);
        return ResponseEntity.ok(savedAirline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airline> updateAirline(@PathVariable Long id, @Valid @RequestBody AirlineCreateDTO dto) {
        return airlineRepository.findById(id)
                .map(airline -> {
                    airline.setName(dto.getName());
                    airline.setCode(dto.getCode());
                    return ResponseEntity.ok(airlineRepository.save(airline));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAirline(@PathVariable Long id) {
        return airlineRepository.findById(id)
                .map(airline -> {
                    airlineRepository.delete(airline);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
