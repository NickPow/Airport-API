package com.example.airportapi.controller;

import com.example.airportapi.model.Gate;
import com.example.airportapi.repository.GateRepository;
import com.example.airportapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gates")
public class GateController {

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Gate> getAllGates() {
        return gateRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gate> getGateById(@PathVariable Long id) {
        return gateRepository.findById(id)
                .map(gate -> ResponseEntity.ok().body(gate))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/airport/{airportId}")
    public ResponseEntity<List<Gate>> getGatesByAirport(@PathVariable Long airportId) {
        List<Gate> gates = gateRepository.findByAirportId(airportId);
        return ResponseEntity.ok(gates);
    }

    @PostMapping
    public ResponseEntity<Gate> createGate(@RequestBody Gate gate) {
        // Validate that the airport exists
        if (gate.getAirport() != null && gate.getAirport().getId() != null) {
            return airportRepository.findById(gate.getAirport().getId())
                    .map(airport -> {
                        gate.setAirport(airport);
                        Gate savedGate = gateRepository.save(gate);
                        return ResponseEntity.ok(savedGate);
                    })
                    .orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gate> updateGate(@PathVariable Long id, @RequestBody Gate gateDetails) {
        return gateRepository.findById(id)
                .map(gate -> {
                    gate.setGateNumber(gateDetails.getGateNumber());
                    
                    // Update airport if provided
                    if (gateDetails.getAirport() != null && gateDetails.getAirport().getId() != null) {
                        return airportRepository.findById(gateDetails.getAirport().getId())
                                .map(airport -> {
                                    gate.setAirport(airport);
                                    return ResponseEntity.ok(gateRepository.save(gate));
                                })
                                .orElse(ResponseEntity.badRequest().build());
                    }
                    
                    return ResponseEntity.ok(gateRepository.save(gate));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGate(@PathVariable Long id) {
        return gateRepository.findById(id)
                .map(gate -> {
                    gateRepository.delete(gate);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
