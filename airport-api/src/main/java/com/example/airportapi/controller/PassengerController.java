package com.example.airportapi.controller;

import com.example.airportapi.model.Aircraft;
import com.example.airportapi.model.Airport;
import com.example.airportapi.model.City;
import com.example.airportapi.model.Passenger;
import com.example.airportapi.repository.AircraftRepository;
import com.example.airportapi.repository.CityRepository;
import com.example.airportapi.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private CityRepository cityRepository;

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

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger updatedPassenger) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);

        if (optionalPassenger.isPresent()) {
            Passenger existingPassenger = optionalPassenger.get();

            existingPassenger.setFirstName(updatedPassenger.getFirstName());
            existingPassenger.setLastName(updatedPassenger.getLastName());
            existingPassenger.setPhoneNumber(updatedPassenger.getPhoneNumber());

            City city = cityRepository.findById(updatedPassenger.getCity().getId())
                    .orElseThrow(() -> new RuntimeException("City not found with id: " + updatedPassenger.getCity().getId()));
            existingPassenger.setCity(city);

            List<Aircraft> linkedAircraft = updatedPassenger.getAircraftFlown().stream()
                    .map(a -> aircraftRepository.findById(a.getId())
                            .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + a.getId())))
                    .toList();

            existingPassenger.getAircraftFlown().clear();
            existingPassenger.getAircraftFlown().addAll(linkedAircraft);

            // No save call needed due to @Transactional

            return ResponseEntity.ok(existingPassenger);
        } else {
            return ResponseEntity.notFound().build();
        }
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
