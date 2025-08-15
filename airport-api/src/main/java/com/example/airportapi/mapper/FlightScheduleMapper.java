package com.example.airportapi.mapper;

import com.example.airportapi.dto.FlightScheduleDTO;
import com.example.airportapi.dto.FlightScheduleResponseDTO;
import com.example.airportapi.model.*;

public class FlightScheduleMapper {

    public static FlightScheduleResponseDTO toDto(FlightSchedule flight) {
        int aircraftCapacity = flight.getAircraft() != null ? flight.getAircraft().getNumberOfPassengers() : 0;
        return new FlightScheduleResponseDTO(
            flight.getId(),
            flight.getFlightNumber(),
            flight.getFlightType(),
            flight.getStatus(),
            flight.getScheduledTime(),
            flight.getAirline().getName(),
            flight.getOrigin().getCode(),
            flight.getDestination().getCode(),
            flight.getCurrentPassengerCount(),
            aircraftCapacity,
            flight.getAvailableSeats(),
            flight.getAircraftType(),
            flight.getGateNumber(),
            flight.getTerminalNumber(),
            flight.getPassengerCapacity()
        );
    }

    public static FlightSchedule toEntity(FlightScheduleDTO dto,
                                          Airline airline,
                                          Aircraft aircraft,
                                          Gate gate,
                                          Airport origin,
                                          Airport destination) {
        return new FlightSchedule(
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
    }
}
