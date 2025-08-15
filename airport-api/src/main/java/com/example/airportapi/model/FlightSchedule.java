package com.example.airportapi.model;

import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String flightNumber;

    @Enumerated(EnumType.STRING)
    private FlightType flightType;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @NotNull
    private LocalDateTime scheduledTime;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;

    @ManyToOne
    @JoinColumn(name = "origin_airport_id")
    private Airport origin;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id")
    private Airport destination;

    @Column(nullable = false)
    private int currentPassengerCount = 0;

    // New fields for direct flight information
    private String aircraftType;           // e.g. "Boeing 737-800", "Airbus A320"
    private String gateNumber;             // e.g. "A12", "B7", "C3"
    private String terminalNumber;         // e.g. "1", "2", "International"
    private Integer passengerCapacity;     // e.g. 180, 250, 350

    public FlightSchedule() {
    }

    public FlightSchedule(String flightNumber, FlightType flightType, FlightStatus status,
                          LocalDateTime scheduledTime, Airline airline, Aircraft aircraft,
                          Gate gate, Airport origin, Airport destination) {
        this.flightNumber = flightNumber;
        this.flightType = flightType;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.airline = airline;
        this.aircraft = aircraft;
        this.gate = gate;
        this.origin = origin;
        this.destination = destination;
        this.currentPassengerCount = 0;
    }

    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }

    public void setCurrentPassengerCount(int currentPassengerCount) {
        this.currentPassengerCount = currentPassengerCount;
    }

    public int getAvailableSeats() {
        if (aircraft != null) {
            return aircraft.getNumberOfPassengers() - currentPassengerCount;
        }
        return 0;
    }

    public boolean hasAvailableSeats() {
        return getAvailableSeats() > 0;
    }

    // New getters and setters for direct flight information
    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}
