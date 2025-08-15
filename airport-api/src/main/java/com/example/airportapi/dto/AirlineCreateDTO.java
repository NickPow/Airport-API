package com.example.airportapi.dto;

import jakarta.validation.constraints.NotBlank;

public class AirlineCreateDTO {
    
    @NotBlank(message = "Airline name is required")
    private String name;
    
    private String code;

    public AirlineCreateDTO() {
    }

    public AirlineCreateDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
