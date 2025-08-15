package com.example.airportapi.dto;

import jakarta.validation.constraints.NotBlank;

public class AircraftTypeCreateDTO {
    
    @NotBlank(message = "Aircraft type is required")
    private String type;

    public AircraftTypeCreateDTO() {
    }

    public AircraftTypeCreateDTO(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
