package com.example.airportapi;

import com.example.airportapi.model.*;
import com.example.airportapi.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class PassengerQuantityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    private FlightSchedule savedFlight;
    private Aircraft savedAircraft;

    @BeforeEach
    public void setup() {
        flightScheduleRepository.deleteAll();
        aircraftRepository.deleteAll();
        airlineRepository.deleteAll();
        gateRepository.deleteAll();
        airportRepository.deleteAll();
        cityRepository.deleteAll();

        // Create test data
        City city = cityRepository.save(new City("Test City", "Test Province", 100000));
        Airport origin = airportRepository.save(new Airport("Origin Airport", "ORG", city));
        Airport destination = airportRepository.save(new Airport("Destination Airport", "DST", city));
        Gate gate = gateRepository.save(new Gate("A1", origin));
        Airline airline = airlineRepository.save(new Airline("Test Airline", "TA"));
        savedAircraft = aircraftRepository.save(new Aircraft("Boeing 737", "Test Airline", 150)); // 150 passenger capacity

        savedFlight = flightScheduleRepository.save(new FlightSchedule(
                "TA123",
                com.example.airportapi.model.enums.FlightType.DEPARTURE,
                com.example.airportapi.model.enums.FlightStatus.ON_TIME,
                LocalDateTime.now().plusHours(2),
                airline,
                savedAircraft,
                gate,
                origin,
                destination
        ));
    }

    @Test
    public void testGetPassengerInfo() throws Exception {
        mockMvc.perform(get("/admin/flights/" + savedFlight.getId() + "/passenger-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPassengerCount", is(0)))
                .andExpect(jsonPath("$.aircraftCapacity", is(150)))
                .andExpect(jsonPath("$.availableSeats", is(150)))
                .andExpect(jsonPath("$.hasAvailableSeats", is(true)));
    }

    @Test
    public void testBookPassengers() throws Exception {
        mockMvc.perform(post("/admin/flights/" + savedFlight.getId() + "/book-passengers")
                .param("passengerCount", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPassengerCount", is(50)))
                .andExpect(jsonPath("$.availableSeats", is(100)));
    }

    @Test
    public void testBookPassengers_ExceedsCapacity() throws Exception {
        mockMvc.perform(post("/admin/flights/" + savedFlight.getId() + "/book-passengers")
                .param("passengerCount", "200"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCancelPassengers() throws Exception {
        // First book some passengers
        mockMvc.perform(post("/admin/flights/" + savedFlight.getId() + "/book-passengers")
                .param("passengerCount", "50"))
                .andExpect(status().isOk());

        // Then cancel some
        mockMvc.perform(post("/admin/flights/" + savedFlight.getId() + "/cancel-passengers")
                .param("passengerCount", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPassengerCount", is(30)))
                .andExpect(jsonPath("$.availableSeats", is(120)));
    }

    @Test
    public void testCancelPassengers_ExceedsBooked() throws Exception {
        mockMvc.perform(post("/admin/flights/" + savedFlight.getId() + "/cancel-passengers")
                .param("passengerCount", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBookPassengers_InvalidFlightId() throws Exception {
        mockMvc.perform(post("/admin/flights/999/book-passengers")
                .param("passengerCount", "10"))
                .andExpect(status().isNotFound());
    }
}
