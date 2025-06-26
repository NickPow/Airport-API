package com.example.airportapi.controller;

import com.example.airportapi.model.Aircraft;
import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.AircraftRepository;
import com.example.airportapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsForAircraft(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> ResponseEntity.ok(aircraft.getAirports()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft updatedAircraft) {
        Optional<Aircraft> optionalAircraft = aircraftRepository.findById(id);

        if (optionalAircraft.isPresent()) {
            Aircraft existingAircraft = optionalAircraft.get();

            existingAircraft.setType(updatedAircraft.getType());
            existingAircraft.setAirlineName(updatedAircraft.getAirlineName());
            existingAircraft.setNumberOfPassengers(updatedAircraft.getNumberOfPassengers());

            List<Airport> linkedAirports = updatedAircraft.getAirports().stream()
                    .map(a -> airportRepository.findById(a.getId())
                            .orElseThrow(() -> new RuntimeException("Airport not found with id: " + a.getId())))
                    .toList();

            existingAircraft.getAirports().clear();
            existingAircraft.getAirports().addAll(linkedAirports);

            // No save() needed, handled by @Transactional

            return ResponseEntity.ok(existingAircraft);
        } else {
            return ResponseEntity.notFound().build();
        }
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
