package com.example.airportapi.controller;

import com.example.airportapi.model.City;
import com.example.airportapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setName(cityDetails.getName());
                    city.setState(cityDetails.getState());
                    city.setPopulation(cityDetails.getPopulation());
                    return ResponseEntity.ok(cityRepository.save(city));
                })
                .orElse(ResponseEntity.notFound().build());
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
