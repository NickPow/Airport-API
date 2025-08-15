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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class FlightScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private GateRepository gateRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        // Create test data
        createTestData();
    }

    private void createTestData() {
        // Create cities
        City toronto = new City("Toronto", "Ontario", 2930000);
        City vancouver = new City("Vancouver", "British Columbia", 631486);
        cityRepository.save(toronto);
        cityRepository.save(vancouver);

        // Create airports
        Airport yyz = new Airport("Toronto Pearson International Airport", "YYZ", toronto);
        Airport yvr = new Airport("Vancouver International Airport", "YVR", vancouver);
        airportRepository.save(yyz);
        airportRepository.save(yvr);

        // Create airline
        Airline airCanada = new Airline("Air Canada", "AC");
        airlineRepository.save(airCanada);

        // Create aircraft
        Aircraft aircraft = new Aircraft("Boeing 737", "Air Canada", 189);
        aircraftRepository.save(aircraft);

        // Create gates
        Gate gateA1 = new Gate("A1", yyz);
        Gate gateB2 = new Gate("B2", yvr);
        gateRepository.save(gateA1);
        gateRepository.save(gateB2);
    }

    @Test
    public void testCreateFlightSimple_Success() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("AC100");
        dto.setAirlineName("Air Canada");
        dto.setOriginCode("YYZ");
        dto.setDestinationCode("YVR");
        dto.setScheduledTime(LocalDateTime.now().plusHours(2));
        dto.setFlightType(FlightType.DEPARTURE);
        dto.setStatus(FlightStatus.ON_TIME);

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AC100"))
                .andExpect(jsonPath("$.airlineName").value("Air Canada"))
                .andExpect(jsonPath("$.originCode").value("YYZ"))
                .andExpect(jsonPath("$.destinationCode").value("YVR"));
    }

    @Test
    public void testCreateFlightSimple_DefaultValues() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("AC200");
        dto.setAirlineName("Air Canada");
        dto.setOriginCode("YYZ");
        dto.setDestinationCode("YVR");
        dto.setScheduledTime(LocalDateTime.now().plusHours(3));
        // Note: Not setting flightType and status to test defaults

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AC200"))
                .andExpect(jsonPath("$.flightType").value("DEPARTURE"))
                .andExpect(jsonPath("$.status").value("ON_TIME"));
    }

    @Test
    public void testCreateFlightSimple_MissingFields() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("AC300");
        // Missing required fields

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFlightSimple_InvalidAirline() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("XX100");
        dto.setAirlineName("Non Existent Airline");
        dto.setOriginCode("YYZ");
        dto.setDestinationCode("YVR");
        dto.setScheduledTime(LocalDateTime.now().plusHours(2));

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFlightSimple_InvalidAirportCode() throws Exception {
        FlightScheduleCreateDTO dto = new FlightScheduleCreateDTO();
        dto.setFlightNumber("AC400");
        dto.setAirlineName("Air Canada");
        dto.setOriginCode("XXX");
        dto.setDestinationCode("YVR");
        dto.setScheduledTime(LocalDateTime.now().plusHours(2));

        mockMvc.perform(post("/admin/flights/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
