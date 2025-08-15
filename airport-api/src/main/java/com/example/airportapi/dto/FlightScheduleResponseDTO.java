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

    public FlightScheduleResponseDTO() {
    }

    public FlightScheduleResponseDTO(Long id, String flightNumber, FlightType flightType, FlightStatus status,
                                     LocalDateTime scheduledTime, String airlineName,
                                     String originCode, String destinationCode) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.flightType = flightType;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.airlineName = airlineName;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
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
}
