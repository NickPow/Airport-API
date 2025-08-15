package com.example.airportapi.controller;

import com.example.airportapi.dto.FlightScheduleCreateDTO;
import com.example.airportapi.dto.FlightScheduleDTO;
import com.example.airportapi.dto.FlightScheduleResponseDTO;
import com.example.airportapi.dto.PassengerInfoDTO;
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
    public ResponseEntity<List<FlightScheduleResponseDTO>> getArrivals(@PathVariable("id") Long id) {
        List<FlightScheduleResponseDTO> arrivals = flightService.getArrivalsByAirport(id)
                .stream()
                .map(FlightScheduleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(arrivals);
    }

    @GetMapping("/airports/{id}/flights/departures")
    public ResponseEntity<List<FlightScheduleResponseDTO>> getDepartures(@PathVariable("id") Long id) {
        List<FlightScheduleResponseDTO> departures = flightService.getDeparturesByAirport(id)
                .stream()
                .map(FlightScheduleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departures);
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

    @PostMapping("/admin/flights/{id}/book-passengers")
    public ResponseEntity<FlightScheduleResponseDTO> bookPassengers(@PathVariable Long id, @RequestParam int passengerCount) {
        try {
            FlightSchedule updated = flightService.bookPassengers(id, passengerCount);
            return ResponseEntity.ok(FlightScheduleMapper.toDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/admin/flights/{id}/cancel-passengers")
    public ResponseEntity<FlightScheduleResponseDTO> cancelPassengers(@PathVariable Long id, @RequestParam int passengerCount) {
        try {
            FlightSchedule updated = flightService.cancelPassengers(id, passengerCount);
            return ResponseEntity.ok(FlightScheduleMapper.toDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/admin/flights/{id}/passenger-info")
    public ResponseEntity<PassengerInfoDTO> getPassengerInfo(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(flight -> {
                    int aircraftCapacity = flight.getAircraft() != null ? flight.getAircraft().getNumberOfPassengers() : 0;
                    PassengerInfoDTO info = new PassengerInfoDTO(
                        flight.getCurrentPassengerCount(),
                        aircraftCapacity,
                        flight.getAvailableSeats(),
                        flight.hasAvailableSeats()
                    );
                    return ResponseEntity.ok(info);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
