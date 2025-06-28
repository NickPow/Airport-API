package com.example.airportapi;

import com.example.airportapi.model.City;
import com.example.airportapi.model.Airport;
import com.example.airportapi.repository.CityRepository;
import com.example.airportapi.repository.AirportRepository;
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
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirportRepository airportRepository;

    private City savedCity;

    @BeforeEach
    public void setup() {
        cityRepository.deleteAll();
        City city = new City("TestCity", "TestProvince", 100000);
        savedCity = cityRepository.save(city);
    }

    @Test
    public void testGetAllCities() throws Exception {
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("TestCity")));
    }

    @Test
    public void testGetAirportsForCity_NoAirports() throws Exception {
        mockMvc.perform(get("/cities/" + savedCity.getId() + "/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetAirportsForCity_InvalidId() throws Exception {
        mockMvc.perform(get("/cities/99999/airports"))
                .andExpect(status().isNotFound());
    }
}
