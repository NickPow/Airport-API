package com.example.airportapi.dto;

import jakarta.validation.constraints.NotBlank;

public class AirportCreateDTO {
    
    @NotBlank(message = "Airport code is required")
    private String code;
    
    @NotBlank(message = "Airport name is required")
    private String name;
    
    private Long cityId;

    public AirportCreateDTO() {
    }

    public AirportCreateDTO(String code, String name, Long cityId) {
        this.code = code;
        this.name = name;
        this.cityId = cityId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
