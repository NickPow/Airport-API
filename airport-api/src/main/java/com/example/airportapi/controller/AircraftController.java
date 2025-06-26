package com.example.airportapi.controller;

import com.example.airportapi.model.Aircraft;
import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsUsedByAircraft(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> ResponseEntity.ok(aircraft.getAirports()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft details) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraft.setType(details.getType());
                    aircraft.setAirlineName(details.getAirlineName());
                    aircraft.setNumberOfPassengers(details.getNumberOfPassengers());
                    return ResponseEntity.ok(aircraftRepository.save(aircraft));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAircraft(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraftRepository.delete(aircraft);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
