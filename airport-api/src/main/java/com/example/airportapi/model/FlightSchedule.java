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
}
