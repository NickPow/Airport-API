package com.example.airportapi.controller;

import com.example.airportapi.model.Passenger;
import com.example.airportapi.model.Aircraft;
import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<Aircraft>> getAircraftFlownByPassenger(@PathVariable Long id) {
        return passengerRepository.findById(id)
                .map(passenger -> ResponseEntity.ok(passenger.getAircraftFlown()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsUsedByPassenger(@PathVariable Long id) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    List<Airport> airports = passenger.getAircraftFlown().stream()
                            .flatMap(aircraft -> aircraft.getAirports().stream())
                            .distinct()
                            .toList();
                    return ResponseEntity.ok(airports);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger details) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstName(details.getFirstName());
                    passenger.setLastName(details.getLastName());
                    passenger.setPhoneNumber(details.getPhoneNumber());
                    return ResponseEntity.ok(passengerRepository.save(passenger));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePassenger(@PathVariable Long id) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passengerRepository.delete(passenger);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
