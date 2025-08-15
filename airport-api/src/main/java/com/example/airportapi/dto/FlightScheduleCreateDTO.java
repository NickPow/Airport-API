package com.example.airportapi.dto;

import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class FlightScheduleCreateDTO {

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotBlank(message = "Airline name is required")
    private String airlineName;

    @NotBlank(message = "Origin airport code is required")
    private String originCode;

    @NotBlank(message = "Destination airport code is required")
    private String destinationCode;

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledTime;

    @NotNull(message = "Flight type is required")
    private FlightType flightType = FlightType.DEPARTURE;

    @NotNull(message = "Flight status is required")
    private FlightStatus status = FlightStatus.ON_TIME;
    
    // New fields for direct flight information
    private String aircraftType;           // e.g. "Boeing 737-800", "Airbus A320"
    private String gateNumber;             // e.g. "A12", "B7", "C3"
    private String terminalNumber;         // e.g. "1", "2", "International"
    private Integer passengerCapacity;     // e.g. 180, 250, 350

    public FlightScheduleCreateDTO() {
    }

    public FlightScheduleCreateDTO(String flightNumber, String airlineName, String originCode, 
                                   String destinationCode, LocalDateTime scheduledTime, 
                                   FlightType flightType, FlightStatus status) {
        this.flightNumber = flightNumber;
        this.airlineName = airlineName;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.scheduledTime = scheduledTime;
        this.flightType = flightType != null ? flightType : FlightType.DEPARTURE;
        this.status = status != null ? status : FlightStatus.ON_TIME;
    }

    // Getters and Setters

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType != null ? flightType : FlightType.DEPARTURE;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status != null ? status : FlightStatus.ON_TIME;
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
