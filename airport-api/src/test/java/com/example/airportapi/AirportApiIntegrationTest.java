package com.example.airportapi;

import com.example.airportapi.dto.FlightScheduleCreateDTO;
import com.example.airportapi.model.*;
import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;
import com.example.airportapi.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class AirportApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private FlightScheduleRepository flightRepository;

    private ObjectMapper objectMapper;

    private City testCity;
    private Airport testAirport;
    private Airport testAirport2;
    private Airline testAirline;
    private Aircraft testAircraft;
    private Gate testGate;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Setup test data
        testCity = cityRepository.save(new City("Test City", "Test Province", 100000));
        
        testAirport = airportRepository.save(new Airport("Test Airport", "TST", testCity));
        testAirport2 = airportRepository.save(new Airport("Test Airport 2", "TS2", testCity));
        
        testAirline = airlineRepository.save(new Airline("Test Airlines", "TA"));
        
        testAircraft = aircraftRepository.save(new Aircraft("Test Aircraft", "Test Airlines", 150));
        
        testGate = gateRepository.save(new Gate("A1", testAirport));
    }

    // ===== AIRPORT CONTROLLER TESTS =====

    @Test
    public void testGetAllAirports() throws Exception {
        mockMvc.perform(get("/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test Airport"))
                .andExpect(jsonPath("$[0].code").value("TST"));
    }

    @Test
    public void testGetAirportById() throws Exception {
        mockMvc.perform(get("/airports/" + testAirport.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Airport"))
                .andExpect(jsonPath("$.code").value("TST"))
                .andExpect(jsonPath("$.cityName").value("Test City"));
    }

    @Test
    public void testGetAirportById_NotFound() throws Exception {
        mockMvc.perform(get("/airports/99999"))
                .andExpect(status().isNotFound());
    }

    // ===== FLIGHT SCHEDULE CONTROLLER TESTS =====

    @Test
    public void testCreateFlightSimple_Success() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("TA100");
        dto.setAirlineName("Test Airlines");
        dto.setOriginCode("TST");
        dto.setDestinationCode("TS2");
        dto.setScheduledTime(LocalDateTime.now().plusHours(2));
        dto.setFlightType(FlightType.DEPARTURE);
        dto.setStatus(FlightStatus.ON_TIME);

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("TA100"))
                .andExpect(jsonPath("$.airlineName").value("Test Airlines"))
                .andExpect(jsonPath("$.originCode").value("TST"))
                .andExpect(jsonPath("$.destinationCode").value("TS2"));
    }

    @Test
    public void testCreateFlightSimple_InvalidAirline() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("XX100");
        dto.setAirlineName("Non Existent Airline");
        dto.setOriginCode("TST");
        dto.setDestinationCode("TS2");
        dto.setScheduledTime(LocalDateTime.now().plusHours(2));

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFlightSimple_InvalidAirportCode() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("TA400");
        dto.setAirlineName("Test Airlines");
        dto.setOriginCode("XXX");
        dto.setDestinationCode("TS2");
        dto.setScheduledTime(LocalDateTime.now().plusHours(2));

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFlightArrivals() throws Exception {
        // Create a test flight first
        FlightSchedule testFlight = new FlightSchedule();
        testFlight.setFlightNumber("TA200");
        testFlight.setFlightType(FlightType.ARRIVAL);
        testFlight.setStatus(FlightStatus.ON_TIME);
        testFlight.setScheduledTime(LocalDateTime.now().plusHours(1));
        testFlight.setAirline(testAirline);
        testFlight.setAircraft(testAircraft);
        testFlight.setGate(testGate);
        testFlight.setOrigin(testAirport2);
        testFlight.setDestination(testAirport);
        flightRepository.save(testFlight);

        mockMvc.perform(get("/airports/" + testAirport.getId() + "/flights/arrivals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].flightNumber").value("TA200"))
                .andExpect(jsonPath("$[0].flightType").value("ARRIVAL"));
    }

    @Test
    public void testGetFlightDepartures() throws Exception {
        // Create a test flight first
        FlightSchedule testFlight = new FlightSchedule();
        testFlight.setFlightNumber("TA300");
        testFlight.setFlightType(FlightType.DEPARTURE);
        testFlight.setStatus(FlightStatus.BOARDING);
        testFlight.setScheduledTime(LocalDateTime.now().plusHours(2));
        testFlight.setAirline(testAirline);
        testFlight.setAircraft(testAircraft);
        testFlight.setGate(testGate);
        testFlight.setOrigin(testAirport);
        testFlight.setDestination(testAirport2);
        flightRepository.save(testFlight);

        mockMvc.perform(get("/airports/" + testAirport.getId() + "/flights/departures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].flightNumber").value("TA300"))
                .andExpect(jsonPath("$[0].flightType").value("DEPARTURE"));
    }

    @Test
    public void testGetAllFlights() throws Exception {
        mockMvc.perform(get("/admin/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ===== CITY CONTROLLER TESTS =====

    @Test
    public void testGetAllCities() throws Exception {
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Test City"));
    }

    @Test
    public void testGetAirportsByCity() throws Exception {
        mockMvc.perform(get("/cities/" + testCity.getId() + "/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ===== VALIDATION TESTS =====

    @Test
    public void testCreateFlightSimple_MissingRequiredFields() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("TA500");
        // Missing required fields

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFlightSimple_DefaultValues() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("TA600");
        dto.setAirlineName("Test Airlines");
        dto.setOriginCode("TST");
        dto.setDestinationCode("TS2");
        dto.setScheduledTime(LocalDateTime.now().plusHours(3));
        // Not setting flightType and status to test defaults

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightType").value("DEPARTURE"))
                .andExpect(jsonPath("$.status").value("ON_TIME"));
    }
}
