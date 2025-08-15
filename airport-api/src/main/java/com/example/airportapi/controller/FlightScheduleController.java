package com.example.airportapi.controller;

import com.example.airportapi.dto.FlightScheduleCreateDTO;
import com.example.airportapi.dto.FlightScheduleDTO;
import com.example.airportapi.dto.FlightScheduleResponseDTO;
import com.example.airportapi.mapper.FlightScheduleMapper;
import com.example.airportapi.model.FlightSchedule;
import com.example.airportapi.service.FlightScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/admin/flights")
    public ResponseEntity<List<FlightScheduleResponseDTO>> getAllFlights() {
        List<FlightScheduleResponseDTO> flights = flightService.getAllFlights()
                .stream()
                .map(FlightScheduleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(flights);
    }

    @PostMapping("/admin/flights")
    public ResponseEntity<FlightScheduleResponseDTO> createFlight(@RequestBody @Valid FlightScheduleDTO dto) {
        FlightSchedule created = flightService.createFlight(dto);
        return ResponseEntity.ok(FlightScheduleMapper.toDto(created));
    }

    @PostMapping("/admin/flights/simple")
    public ResponseEntity<FlightScheduleResponseDTO> createFlightSimple(@RequestBody @Valid FlightScheduleCreateDTO dto) {
        FlightSchedule created = flightService.createFlightSimple(dto);
        return ResponseEntity.ok(FlightScheduleMapper.toDto(created));
    }

    @PutMapping("/admin/flights/{id}")
    public ResponseEntity<FlightScheduleResponseDTO> updateFlight(@PathVariable Long id, @RequestBody @Valid FlightScheduleDTO dto) {
        FlightSchedule updated = flightService.updateFlight(id, dto);
        return ResponseEntity.ok(FlightScheduleMapper.toDto(updated));
    }

    @DeleteMapping("/admin/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
