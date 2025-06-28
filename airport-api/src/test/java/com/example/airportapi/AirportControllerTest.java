package com.example.airportapi;

import com.example.airportapi.model.Airport;
import com.example.airportapi.model.City;
import com.example.airportapi.repository.AirportRepository;
import com.example.airportapi.repository.CityRepository;
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
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    private City savedCity;

    @BeforeEach
    public void setup() {
        airportRepository.deleteAll();
        cityRepository.deleteAll();

        savedCity = cityRepository.save(new City("TestCity", "TestProvince", 50000));
    }

    @Test
    public void testGetAllAirports() throws Exception {
        airportRepository.save(new Airport("TestAirport", "TST", savedCity));

        mockMvc.perform(get("/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("TestAirport")));
    }

    @Test
    public void testGetAirportsByCity_NoAirports() throws Exception {
        mockMvc.perform(get("/airports/byCity/" + savedCity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetAirportsByCity_InvalidCityId() throws Exception {
        mockMvc.perform(get("/airports/byCity/99999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
