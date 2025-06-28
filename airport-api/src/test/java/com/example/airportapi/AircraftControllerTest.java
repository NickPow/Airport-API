package com.example.airportapi;

import com.example.airportapi.model.Aircraft;
import com.example.airportapi.repository.AircraftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AircraftRepository aircraftRepository;

    private Aircraft savedAircraft;

    @BeforeEach
    public void setup() {
        aircraftRepository.deleteAll();
        savedAircraft = aircraftRepository.save(new Aircraft("Jet", "TestAir", 200));
    }

    @Test
    public void testGetAllAircraft() throws Exception {
        mockMvc.perform(get("/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type", is("Jet")));
    }

    @Test
    public void testGetAirportsForAircraft_NoAirports() throws Exception {
        mockMvc.perform(get("/aircraft/" + savedAircraft.getId() + "/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetAirportsForAircraft_InvalidId() throws Exception {
        mockMvc.perform(get("/aircraft/99999/airports"))
                .andExpect(status().isNotFound());
    }
}
