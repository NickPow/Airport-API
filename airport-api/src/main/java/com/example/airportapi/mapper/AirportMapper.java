package com.example.airportapi.mapper;

import com.example.airportapi.dto.AirportResponseDTO;
import com.example.airportapi.model.Airport;

public class AirportMapper {

    public static AirportResponseDTO toDto(Airport airport) {
        return new AirportResponseDTO(
            airport.getId(),
            airport.getName(),
            airport.getCode(),
            airport.getCity().getName(),
            airport.getCity().getProvince(),
            airport.getCity().getPopulation()
        );
    }
}
