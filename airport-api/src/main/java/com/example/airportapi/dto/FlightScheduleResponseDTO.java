package com.example.airportapi.dto;

import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;

import java.time.LocalDateTime;

public class FlightScheduleResponseDTO {
    private Long id;
    private String flightNumber;
    private FlightType flightType;
    private FlightStatus status;
    private LocalDateTime scheduledTime;
    private String airlineName;
    private String originCode;
    private String destinationCode;
    private int currentPassengerCount;
    private int aircraftCapacity;
    private int availableSeats;
    
    // New fields for direct flight information
    private String aircraftType;           // e.g. "Boeing 737-800", "Airbus A320"
    private String gateNumber;             // e.g. "A12", "B7", "C3"
    private String terminalNumber;         // e.g. "1", "2", "International"
    private Integer passengerCapacity;     // e.g. 180, 250, 350

    public FlightScheduleResponseDTO() {
    }

    public FlightScheduleResponseDTO(Long id, String flightNumber, FlightType flightType, FlightStatus status,
                                     LocalDateTime scheduledTime, String airlineName,
                                     String originCode, String destinationCode, 
                                     int currentPassengerCount, int aircraftCapacity, int availableSeats,
                                     String aircraftType, String gateNumber, String terminalNumber, Integer passengerCapacity) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.flightType = flightType;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.airlineName = airlineName;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.currentPassengerCount = currentPassengerCount;
        this.aircraftCapacity = aircraftCapacity;
        this.availableSeats = availableSeats;
        this.aircraftType = aircraftType;
        this.gateNumber = gateNumber;
        this.terminalNumber = terminalNumber;
        this.passengerCapacity = passengerCapacity;
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

    public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }

    public void setCurrentPassengerCount(int currentPassengerCount) {
        this.currentPassengerCount = currentPassengerCount;
    }

    public int getAircraftCapacity() {
        return aircraftCapacity;
    }

    public void setAircraftCapacity(int aircraftCapacity) {
        this.aircraftCapacity = aircraftCapacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
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
