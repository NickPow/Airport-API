package com.example.airportapi;

import com.example.airportapi.model.*;
import com.example.airportapi.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    private Passenger savedPassenger;

    @BeforeEach
    public void setup() {
        passengerRepository.deleteAll();
        cityRepository.deleteAll();

        City city = cityRepository.save(new City("TestCity", "TestProvince", 50000));
        savedPassenger = passengerRepository.save(new Passenger("John", "Doe", "1234567890", city));
    }

    @Test
    public void testGetAllPassengers() throws Exception {
        mockMvc.perform(get("/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    public void testGetAircraftForPassenger_NoAircraft() throws Exception {
        mockMvc.perform(get("/passengers/" + savedPassenger.getId() + "/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetAircraftForPassenger_InvalidId() throws Exception {
        mockMvc.perform(get("/passengers/99999/aircraft"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAirportsUsedByPassenger_NoAirports() throws Exception {
        mockMvc.perform(get("/passengers/" + savedPassenger.getId() + "/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
