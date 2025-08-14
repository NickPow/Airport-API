package com.example.airportapi.config;

import com.example.airportapi.model.*;
import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;
import com.example.airportapi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataSeeder {

    /**
     * This seeder populates the database with sample data for development and demo
     * purposes.
     * It only runs if no airports exist to avoid duplication.
     */
    @Bean
    public CommandLineRunner seedData(
            CityRepository cityRepo,
            AirportRepository airportRepo,
            GateRepository gateRepo,
            AircraftRepository aircraftRepo,
            AirlineRepository airlineRepo,
            FlightScheduleRepository flightRepo) {
        return args -> {

            if (airportRepo.count() > 0) {
                System.out.println("Database already seeded. Skipping.");
                return;
            }

            // Cities
            City toronto = new City("Toronto", "Ontario", 3000000);
            City vancouver = new City("Vancouver", "British Columbia", 2500000);

            cityRepo.saveAll(Arrays.asList(toronto, vancouver));

            // Airports
            Airport yyz = new Airport("Toronto Pearson", "YYZ", toronto);
            Airport yvr = new Airport("Vancouver Intl", "YVR", vancouver);
            airportRepo.saveAll(Arrays.asList(yyz, yvr));

            // Gates
            Gate gateA1 = new Gate("A1", yyz);
            Gate gateB2 = new Gate("B2", yyz);
            Gate gateC1 = new Gate("C1", yvr);
            gateRepo.saveAll(Arrays.asList(gateA1, gateB2, gateC1));

            // Aircraft
            Aircraft boeing737 = new Aircraft("Boeing 737", "Air Canada", 160);
            Aircraft airbus320 = new Aircraft("Airbus A320", "WestJet", 150);
            aircraftRepo.saveAll(Arrays.asList(boeing737, airbus320));

            // Airlines
            Airline airCanada = new Airline("Air Canada", "AC");
            Airline westJet = new Airline("WestJet", "WS");
            airlineRepo.saveAll(Arrays.asList(airCanada, westJet));

            // Flights
            FlightSchedule arrival1 = new FlightSchedule(
                    "AC123", FlightType.ARRIVAL, FlightStatus.ON_TIME,
                    LocalDateTime.now().plusHours(2),
                    airCanada, boeing737, gateA1, yvr, yyz);

            FlightSchedule departure1 = new FlightSchedule(
                    "WS456", FlightType.DEPARTURE, FlightStatus.DELAYED,
                    LocalDateTime.now().plusHours(3),
                    westJet, airbus320, gateB2, yyz, yvr);

            FlightSchedule arrival2 = new FlightSchedule(
                    "AC789", FlightType.ARRIVAL, FlightStatus.CANCELLED,
                    LocalDateTime.now().plusHours(1),
                    airCanada, airbus320, gateC1, yyz, yvr);

            flightRepo.saveAll(Arrays.asList(arrival1, departure1, arrival2));

            System.out.println("Seed data loaded.");
        };
    }
}
