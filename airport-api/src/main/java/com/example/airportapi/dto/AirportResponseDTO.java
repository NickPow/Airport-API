package com.example.airportapi.dto;

public class AirportResponseDTO {
    private Long id;
    private String name;
    private String code;
    private String cityName;
    private String province;
    private int population;

    public AirportResponseDTO() {
    }

    public AirportResponseDTO(Long id, String name, String code, String cityName, String province, int population) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cityName = cityName;
        this.province = province;
        this.population = population;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCityName() {
        return cityName;
    }

    public String getProvince() {
        return province;
    }

    public int getPopulation() {
        return population;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
