package com.example.airportapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "City name is required")
    private String name;

    private String state;

    @Min(value = 0, message = "Population cannot be negative")
    private int population;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Airport> airports;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    // Constructors
    public City() {
    }

    public City(String name, String state, int population) {
        this.name = name;
        this.state = state;
        this.population = population;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
