package com.example.airportapi.dto;

import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class FlightScheduleDTO {

    @NotBlank
    private String flightNumber;

    @NotNull
    private FlightType flightType;

    @NotNull
    private FlightStatus status;

    @NotNull
    private LocalDateTime scheduledTime;

    @NotNull
    private Long airlineId;

    @NotNull
    private Long aircraftId;

    @NotNull
    private Long gateId;

    @NotNull
    private Long originAirportId;

    @NotNull
    private Long destinationAirportId;

    public FlightScheduleDTO() {
    }

    // Getters and Setters

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

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public Long getOriginAirportId() {
        return originAirportId;
    }

    public void setOriginAirportId(Long originAirportId) {
        this.originAirportId = originAirportId;
    }

    public Long getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }
}
