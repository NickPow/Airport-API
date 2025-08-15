package com.example.airportapi.repository;

import com.example.airportapi.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    
    // Find aircraft by type (exact match)
    List<Aircraft> findByType(String type);
    
    // Find aircraft by airline name
    List<Aircraft> findByAirlineName(String airlineName);
    
    // Find aircraft by type containing (partial match for searching)
    List<Aircraft> findByTypeContainingIgnoreCase(String typePattern);
    
    // Find aircraft by manufacturer (Boeing, Airbus, etc.)
    @Query("SELECT a FROM Aircraft a WHERE a.type LIKE %:manufacturer%")
    List<Aircraft> findByManufacturer(@Param("manufacturer") String manufacturer);
    
    // Find aircraft by capacity range
    List<Aircraft> findByNumberOfPassengersBetween(int minCapacity, int maxCapacity);
    
    // Get all unique aircraft types
    @Query("SELECT DISTINCT a.type FROM Aircraft a ORDER BY a.type")
    List<String> findAllDistinctTypes();
    
    // Get aircraft count by manufacturer
    @Query("SELECT CASE " +
           "WHEN a.type LIKE 'Boeing%' THEN 'Boeing' " +
           "WHEN a.type LIKE 'Airbus%' THEN 'Airbus' " +
           "ELSE 'Other' END as manufacturer, COUNT(a) " +
           "FROM Aircraft a GROUP BY " +
           "CASE WHEN a.type LIKE 'Boeing%' THEN 'Boeing' " +
           "WHEN a.type LIKE 'Airbus%' THEN 'Airbus' " +
           "ELSE 'Other' END")
    List<Object[]> countByManufacturer();
}
