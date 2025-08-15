package com.example.airportapi.controller;

import com.example.airportapi.dto.AircraftTypeCreateDTO;
import com.example.airportapi.model.Aircraft;
import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.AircraftRepository;
import com.example.airportapi.repository.AirportRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsForAircraft(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> ResponseEntity.ok(aircraft.getAirports()))
                .orElse(ResponseEntity.notFound().build());
    }

    // NEW: Get aircraft by type
    @GetMapping("/type/{type}")
    public List<Aircraft> getAircraftByType(@PathVariable String type) {
        return aircraftRepository.findByType(type);
    }

    // NEW: Search aircraft by type pattern
    @GetMapping("/search")
    public List<Aircraft> searchAircraftByType(@RequestParam String type) {
        return aircraftRepository.findByTypeContainingIgnoreCase(type);
    }

    // NEW: Get aircraft by manufacturer
    @GetMapping("/manufacturer/{manufacturer}")
    public List<Aircraft> getAircraftByManufacturer(@PathVariable String manufacturer) {
        return aircraftRepository.findByManufacturer(manufacturer);
    }

    // NEW: Get aircraft by airline
    @GetMapping("/airline/{airlineName}")
    public List<Aircraft> getAircraftByAirline(@PathVariable String airlineName) {
        return aircraftRepository.findByAirlineName(airlineName);
    }

    // NEW: Get aircraft by capacity range
    @GetMapping("/capacity")
    public List<Aircraft> getAircraftByCapacity(
            @RequestParam(defaultValue = "0") int minCapacity,
            @RequestParam(defaultValue = "1000") int maxCapacity) {
        return aircraftRepository.findByNumberOfPassengersBetween(minCapacity, maxCapacity);
    }

    // NEW: Get all aircraft types
    @GetMapping("/types")
    public List<String> getAllAircraftTypes() {
        return aircraftRepository.findAllDistinctTypes();
    }

    // NEW: Get aircraft statistics by manufacturer
    @GetMapping("/stats/manufacturer")
    public Map<String, Long> getAircraftStatsByManufacturer() {
        List<Object[]> results = aircraftRepository.countByManufacturer();
        Map<String, Long> stats = new HashMap<>();
        for (Object[] result : results) {
            stats.put((String) result[0], (Long) result[1]);
        }
        return stats;
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    // POST endpoint for aircraft types - creates a basic aircraft with the specified type
    @PostMapping("/types")
    public ResponseEntity<Aircraft> createAircraftType(@Valid @RequestBody AircraftTypeCreateDTO dto) {
        // Create a basic aircraft entry with the new type
        Aircraft aircraft = new Aircraft();
        aircraft.setType(dto.getType());
        aircraft.setAirlineName("Generic"); // Default airline name
        aircraft.setNumberOfPassengers(150); // Default capacity
        
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return ResponseEntity.ok(savedAircraft);
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
