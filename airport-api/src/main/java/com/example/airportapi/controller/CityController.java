package com.example.airportapi.controller;

import com.example.airportapi.model.Airport;
import com.example.airportapi.model.City;
import com.example.airportapi.repository.AirportRepository;
import com.example.airportapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsForCity(@PathVariable Long id) {
        Optional<City> optionalCity = cityRepository.findById(id);

        if (optionalCity.isPresent()) {
            List<Airport> airports = airportRepository.findByCityId(id);
            return ResponseEntity.ok(airports);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City updatedCity) {
        Optional<City> optionalCity = cityRepository.findById(id);

        if (optionalCity.isPresent()) {
            City existingCity = optionalCity.get();
            existingCity.setName(updatedCity.getName());
            existingCity.setProvince(updatedCity.getProvince());
            existingCity.setPopulation(updatedCity.getPopulation());

            return ResponseEntity.ok(cityRepository.save(existingCity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        return cityRepository.findById(id)
                .map(city -> {
                    cityRepository.delete(city);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
