package com.example.airportapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String gateNumber;

    @ManyToOne
    @JoinColumn(name = "airport_id")
    private Airport airport;

    public Gate() {
    }

    public Gate(String gateNumber, Airport airport) {
        this.gateNumber = gateNumber;
        this.airport = airport;
    }

    public Long getId() {
        return id;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
