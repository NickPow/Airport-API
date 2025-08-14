package com.example.airportapi.controller;

import com.example.airportapi.dto.FlightScheduleDTO;
import com.example.airportapi.model.FlightSchedule;
import com.example.airportapi.service.FlightScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class FlightScheduleController {

    private final FlightScheduleService flightService;

    public FlightScheduleController(FlightScheduleService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports/{id}/flights/arrivals")
    public ResponseEntity<List<FlightSchedule>> getArrivals(@PathVariable("id") Long id) {
        return ResponseEntity.ok(flightService.getArrivalsByAirport(id));
    }

    @GetMapping("/airports/{id}/flights/departures")
    public ResponseEntity<List<FlightSchedule>> getDepartures(@PathVariable("id") Long id) {
        return ResponseEntity.ok(flightService.getDeparturesByAirport(id));
    }

    @PostMapping("/admin/flights")
    public ResponseEntity<FlightSchedule> createFlight(@RequestBody @Valid FlightScheduleDTO dto) {
        return ResponseEntity.ok(flightService.createFlight(dto));
    }

    @PutMapping("/admin/flights/{id}")
    public ResponseEntity<FlightSchedule> updateFlight(@PathVariable Long id, @RequestBody @Valid FlightScheduleDTO dto) {
        return ResponseEntity.ok(flightService.updateFlight(id, dto));
    }

    @DeleteMapping("/admin/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
