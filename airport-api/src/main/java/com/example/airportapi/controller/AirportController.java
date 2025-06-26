package com.example.airportapi.controller;

import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @GetMapping("/byCity/{cityId}")
    public List<Airport> getAirportsByCity(@PathVariable Long cityId) {
        return airportRepository.findByCityId(cityId);
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport) {
        return airportRepository.save(airport);
    }

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
