package com.example.airportapi.service;

import com.example.airportapi.dto.FlightScheduleCreateDTO;
import com.example.airportapi.dto.FlightScheduleDTO;
import com.example.airportapi.model.*;
import com.example.airportapi.model.enums.FlightType;
import com.example.airportapi.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightScheduleService {

    private final FlightScheduleRepository flightRepo;
    private final AirlineRepository airlineRepo;
    private final AircraftRepository aircraftRepo;
    private final GateRepository gateRepo;
    private final AirportRepository airportRepo;

    public FlightScheduleService(FlightScheduleRepository flightRepo,
                                 AirlineRepository airlineRepo,
                                 AircraftRepository aircraftRepo,
                                 GateRepository gateRepo,
                                 AirportRepository airportRepo) {
        this.flightRepo = flightRepo;
        this.airlineRepo = airlineRepo;
        this.aircraftRepo = aircraftRepo;
        this.gateRepo = gateRepo;
        this.airportRepo = airportRepo;
    }

    public List<FlightSchedule> getAllFlights() {
        return flightRepo.findAll();
    }

    public FlightSchedule createFlight(FlightScheduleDTO dto) {
        Airline airline = airlineRepo.findById(dto.getAirlineId())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));

        Aircraft aircraft = aircraftRepo.findById(dto.getAircraftId())
                .orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));

        Gate gate = gateRepo.findById(dto.getGateId())
                .orElseThrow(() -> new EntityNotFoundException("Gate not found"));

        Airport origin = airportRepo.findById(dto.getOriginAirportId())
                .orElseThrow(() -> new EntityNotFoundException("Origin airport not found"));

        Airport destination = airportRepo.findById(dto.getDestinationAirportId())
                .orElseThrow(() -> new EntityNotFoundException("Destination airport not found"));

        FlightSchedule flight = new FlightSchedule(
                dto.getFlightNumber(),
                dto.getFlightType(),
                dto.getStatus(),
                dto.getScheduledTime(),
                airline,
                aircraft,
                gate,
                origin,
                destination
        );
        
        // Set the new direct flight information fields
        flight.setAircraftType(dto.getAircraftType());
        flight.setGateNumber(dto.getGateNumber());
        flight.setTerminalNumber(dto.getTerminalNumber());
        flight.setPassengerCapacity(dto.getPassengerCapacity());

        return flightRepo.save(flight);
    }

    public FlightSchedule createFlightSimple(FlightScheduleCreateDTO dto) {
        // Find airline by name
        Airline airline = airlineRepo.findByName(dto.getAirlineName())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found with name: " + dto.getAirlineName()));

        // Find airports by code
        Airport origin = airportRepo.findByCode(dto.getOriginCode())
                .orElseThrow(() -> new EntityNotFoundException("Origin airport not found with code: " + dto.getOriginCode()));

        Airport destination = airportRepo.findByCode(dto.getDestinationCode())
                .orElseThrow(() -> new EntityNotFoundException("Destination airport not found with code: " + dto.getDestinationCode()));

        // Find or create a default aircraft for this airline
        Aircraft aircraft = aircraftRepo.findAll().stream()
                .filter(a -> a.getAirlineName().equals(dto.getAirlineName()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No aircraft found for airline: " + dto.getAirlineName()));

        // Find or assign a default gate at the origin airport
        Gate gate = gateRepo.findAll().stream()
                .filter(g -> g.getAirport().getId().equals(origin.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No gates available at origin airport: " + dto.getOriginCode()));

        FlightSchedule flight = new FlightSchedule(
                dto.getFlightNumber(),
                dto.getFlightType(),
                dto.getStatus(),
                dto.getScheduledTime(),
                airline,
                aircraft,
                gate,
                origin,
                destination
        );
        
        // Set the new direct flight information fields
        flight.setAircraftType(dto.getAircraftType());
        flight.setGateNumber(dto.getGateNumber());
        flight.setTerminalNumber(dto.getTerminalNumber());
        flight.setPassengerCapacity(dto.getPassengerCapacity());

        return flightRepo.save(flight);
    }

    public FlightSchedule updateFlight(Long id, FlightScheduleDTO dto) {
        FlightSchedule existing = flightRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        existing.setFlightNumber(dto.getFlightNumber());
        existing.setFlightType(dto.getFlightType());
        existing.setStatus(dto.getStatus());
        existing.setScheduledTime(dto.getScheduledTime());
        existing.setAirline(airlineRepo.findById(dto.getAirlineId())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found")));
        existing.setAircraft(aircraftRepo.findById(dto.getAircraftId())
                .orElseThrow(() -> new EntityNotFoundException("Aircraft not found")));
        existing.setGate(gateRepo.findById(dto.getGateId())
                .orElseThrow(() -> new EntityNotFoundException("Gate not found")));
        existing.setOrigin(airportRepo.findById(dto.getOriginAirportId())
                .orElseThrow(() -> new EntityNotFoundException("Origin airport not found")));
        existing.setDestination(airportRepo.findById(dto.getDestinationAirportId())
                .orElseThrow(() -> new EntityNotFoundException("Destination airport not found")));
                
        // Update the new direct flight information fields
        existing.setAircraftType(dto.getAircraftType());
        existing.setGateNumber(dto.getGateNumber());
        existing.setTerminalNumber(dto.getTerminalNumber());
        existing.setPassengerCapacity(dto.getPassengerCapacity());

        return flightRepo.save(existing);
    }

    public void deleteFlight(Long id) {
        if (!flightRepo.existsById(id)) {
            throw new EntityNotFoundException("Flight not found");
        }
        flightRepo.deleteById(id);
    }

    public List<FlightSchedule> getArrivalsByAirport(Long airportId) {
        return flightRepo.findByDestinationIdAndFlightType(airportId, FlightType.ARRIVAL);
    }

    public List<FlightSchedule> getDeparturesByAirport(Long airportId) {
        return flightRepo.findByOriginIdAndFlightType(airportId, FlightType.DEPARTURE);
    }

    public Optional<FlightSchedule> getFlightById(Long id) {
        return flightRepo.findById(id);
    }

    public FlightSchedule bookPassengers(Long flightId, int passengerCount) {
        FlightSchedule flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive");
        }

        int newTotal = flight.getCurrentPassengerCount() + passengerCount;
        int aircraftCapacity = flight.getAircraft() != null ? flight.getAircraft().getNumberOfPassengers() : 0;

        if (newTotal > aircraftCapacity) {
            throw new IllegalArgumentException("Not enough available seats. Available: " + 
                (aircraftCapacity - flight.getCurrentPassengerCount()) + ", Requested: " + passengerCount);
        }

        flight.setCurrentPassengerCount(newTotal);
        return flightRepo.save(flight);
    }

    public FlightSchedule cancelPassengers(Long flightId, int passengerCount) {
        FlightSchedule flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive");
        }

        int newTotal = flight.getCurrentPassengerCount() - passengerCount;

        if (newTotal < 0) {
            throw new IllegalArgumentException("Cannot cancel more passengers than currently booked. Current: " + 
                flight.getCurrentPassengerCount() + ", Requested to cancel: " + passengerCount);
        }

        flight.setCurrentPassengerCount(newTotal);
        return flightRepo.save(flight);
    }
}
