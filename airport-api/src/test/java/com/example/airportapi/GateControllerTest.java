package com.example.airportapi;

import com.example.airportapi.model.Airport;
import com.example.airportapi.model.City;
import com.example.airportapi.model.Gate;
import com.example.airportapi.repository.AirportRepository;
import com.example.airportapi.repository.CityRepository;
import com.example.airportapi.repository.GateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class GateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Airport savedAirport;
    private Gate savedGate;

    @BeforeEach
    public void setup() {
        gateRepository.deleteAll();
        airportRepository.deleteAll();
        cityRepository.deleteAll();

        City city = cityRepository.save(new City("Test City", "Test Province", 100000));
        savedAirport = airportRepository.save(new Airport("Test Airport", "TST", city));
        savedGate = gateRepository.save(new Gate("A1", savedAirport));
    }

    @Test
    public void testGetAllGates() throws Exception {
        mockMvc.perform(get("/gates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gateNumber", is("A1")));
    }

    @Test
    public void testGetGateById() throws Exception {
        mockMvc.perform(get("/gates/" + savedGate.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateNumber", is("A1")))
                .andExpect(jsonPath("$.airport.name", is("Test Airport")));
    }

    @Test
    public void testGetGatesByAirport() throws Exception {
        mockMvc.perform(get("/gates/airport/" + savedAirport.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gateNumber", is("A1")));
    }

    @Test
    public void testCreateGate() throws Exception {
        Gate newGate = new Gate("B2", savedAirport);
        
        mockMvc.perform(post("/gates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newGate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateNumber", is("B2")));
    }

    @Test
    public void testUpdateGate() throws Exception {
        Gate updatedGate = new Gate("A2", savedAirport);
        
        mockMvc.perform(put("/gates/" + savedGate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateNumber", is("A2")));
    }

    @Test
    public void testDeleteGate() throws Exception {
        mockMvc.perform(delete("/gates/" + savedGate.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGateById_NotFound() throws Exception {
        mockMvc.perform(get("/gates/999"))
                .andExpect(status().isNotFound());
    }
}
