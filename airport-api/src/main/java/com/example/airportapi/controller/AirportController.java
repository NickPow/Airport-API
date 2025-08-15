package com.example.airportapi.controller;

import com.example.airportapi.dto.AirportResponseDTO;
import com.example.airportapi.mapper.AirportMapper;
import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    // GET all airports using DTO
    @GetMapping
    public ResponseEntity<List<AirportResponseDTO>> getAllAirports() {
        List<AirportResponseDTO> airportDTOs = airportRepository.findAll()
                .stream()
                .map(AirportMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(airportDTOs);
    }

    // GET single airport by ID using DTO
    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDTO> getAirportById(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(airport -> ResponseEntity.ok(AirportMapper.toDto(airport)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET airports by city using DTO
    @GetMapping("/byCity/{cityId}")
    public ResponseEntity<List<AirportResponseDTO>> getAirportsByCity(@PathVariable Long cityId) {
        List<AirportResponseDTO> airportDTOs = airportRepository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(airportDTOs);
    }

    // CREATE airport (raw entity)
    @PostMapping
    public Airport createAirport(@RequestBody Airport airport) {
        return airportRepository.save(airport);
    }

    // UPDATE airport (raw entity)
    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport details) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(details.getName());
                    airport.setCode(details.getCode());
                    return ResponseEntity.ok(airportRepository.save(airport));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE airport
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAirport(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airportRepository.delete(airport);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
