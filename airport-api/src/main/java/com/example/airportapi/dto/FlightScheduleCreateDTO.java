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
}
