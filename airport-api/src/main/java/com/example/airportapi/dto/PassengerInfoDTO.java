package com.example.airportapi.dto;

public class PassengerInfoDTO {
    private int currentPassengerCount;
    private int aircraftCapacity;
    private int availableSeats;
    private boolean hasAvailableSeats;

    public PassengerInfoDTO() {}

    public PassengerInfoDTO(int currentPassengerCount, int aircraftCapacity, int availableSeats, boolean hasAvailableSeats) {
        this.currentPassengerCount = currentPassengerCount;
        this.aircraftCapacity = aircraftCapacity;
        this.availableSeats = availableSeats;
        this.hasAvailableSeats = hasAvailableSeats;
    }

    public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }

    public void setCurrentPassengerCount(int currentPassengerCount) {
        this.currentPassengerCount = currentPassengerCount;
    }

    public int getAircraftCapacity() {
        return aircraftCapacity;
    }

    public void setAircraftCapacity(int aircraftCapacity) {
        this.aircraftCapacity = aircraftCapacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isHasAvailableSeats() {
        return hasAvailableSeats;
    }

    public void setHasAvailableSeats(boolean hasAvailableSeats) {
        this.hasAvailableSeats = hasAvailableSeats;
    }
}
